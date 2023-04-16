package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {
    private Button timer;
    private CoupledListeners gameClock = new CoupledListeners();

    private int checkCount = 0;
    private Game currGame;
    private TextView playerLives;
    private TextView playerPoints;
    private static ImageView playerImage;
    private boolean playState = true;

    private static boolean collidedWithVehicle = false;
    protected String[] rowTypes = new String[16];

    private Vehicle testVehicle;

    private static int time = 0;

    private ArrayList<Vehicle> vehicleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Establishes player details
        Sprite player = Sprite.parseString(getPlayerInfo());
        playerImage = findViewById(R.id.player);
        int spriteImageIndex = player.getSpriteIndex();

        currGame = new Game(player);
        initializePlayerScreenData(player, spriteImageIndex);
        initializeButtons();

        //calculating block-size
        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int blockSize = rootView.getWidth() / 9;

                        //Creates background
                        createGrid(findViewById(R.id.backgroundGrid), blockSize);
                        int[] rows = populateGrid();

                        currGame.setBlockSize(blockSize);

                        currGame.setMaxHeight(blockSize * 14);
                        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                                blockSize, blockSize);
                        playerImage.setLayoutParams(p);
                        updatePlayerScreenData();

                        //Animates rows on screen
                        Vehicle.l = gameClock;
                        timer.setOnClickListener(gameClock);
                        animate(rows);
                        new CountDownTimer(Long.MAX_VALUE, 30) {
                            public void onTick(long millisUntilFinished) {
                                if (collidedWithVehicle) {
                                    onCollision();
                                }
                                timer.performClick();
                                time++;
                            }
                            public void onFinish() {
                                start();
                            }
                        }.start();
                        // Remove the listener to avoid multiple calls
                        rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });

    }

    public void onCollision() {
        collidedWithVehicle = false;
        playState = false;
        currGame.reset();
        updatePlayerScreenData();
        int[] color = {ContextCompat.getColor(getApplicationContext(), R.color.tint),
            ContextCompat.getColor(getApplicationContext(), R.color.none)};

        new CountDownTimer(2000, 500) {
            private int i = 0;
            @Override
            public void onTick(long l) {
                playerImage.setColorFilter(color[i], PorterDuff.Mode.SRC_IN);
                i = ++i % 2;
            }

            public void onFinish() {
                checkGameOver();
                playerImage.setColorFilter(null);
                playState = true;
            }
        }.start();
    }

    public void checkGameOver() {
        if (currGame.getPlayer().getLives() == 0) {
            Intent gameOver = new Intent(getApplicationContext(), GameOverScreenActivity.class);
            gameOver.putExtra("finalScore", currGame.getScore());
            startActivity(gameOver);
        }
    }

    public void initializePlayerScreenData(Sprite player, int spriteImageIndex){
        //Sets player image on screen
        playerImage.setImageResource(Sprite.spriteOptions[spriteImageIndex][0]);

        //Sets player name on screen
        TextView playerName = findViewById(R.id.username);
        playerName.setText(player.getName().toUpperCase());


        //Sets player lives on screen
        playerLives = findViewById(R.id.playerLives);
        playerLives.setText(String.valueOf(player.getLives()));

        playerPoints = findViewById(R.id.points);
        playerPoints.setText(String.valueOf(currGame.getScore()));

        timer = new Button(this);
    }

    public void initializeButtons() {
        //navigation buttons
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);
        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);

        //moving sprite based on navigation button input
        leftButton.setOnClickListener(e -> {
            if (playState) {
                moveLeft();
            }
        });
        rightButton.setOnClickListener(e -> {
            if (playState) {
                moveRight();
            }
        });
        upButton.setOnClickListener(e -> {
            if (playState) {
                moveUp();
            }
        });
        downButton.setOnClickListener(e -> {
            if (playState) {
                moveDown();
            }
        });
    }

    /**
     * method for updating the player positioning, score and lives
     */
    public void updatePlayerScreenData() {
        checkReachGoalTile();
        playerPoints.setText(String.valueOf(currGame.getScore()));
        playerLives.setText(String.valueOf(currGame.getPlayer().getLives()));
        playerImage.setX(currGame.getPosition()[0]);
        playerImage.setY(currGame.getPosition()[1]);
    }

    public void checkReachGoalTile() {
        if(currGame.getCurrBlock().blockType == GameBlockTypes.GOAL) {
            int temp = currGame.getScore();
            currGame.setScore(temp + 50);
            Intent gameWin = new Intent(getApplicationContext(),
                    GameWinScreenActivity.class);
            gameWin.putExtra("finalScore", currGame.getScore());
            startActivity(gameWin);
            //should be congratulate screen.
        }
    }

    public void checkOnRiver() {
        if (currGame.getCurrBlock().blockType == GameBlockTypes.RIVER) {
            currGame.reset();
            playState = false;
            int[] color = {ContextCompat.getColor(this, R.color.tint),
                    ContextCompat.getColor(this, R.color.none)};
            new CountDownTimer(2000, 500) {
                private int i = 0;
                @Override
                public void onTick(long l) {
                    playerImage.setColorFilter(color[i], PorterDuff.Mode.SRC_IN);
                    i = ++i % 2;
                }

                public void onFinish() {
                    if (currGame.getPlayer().getLives() == 0) {
                        Intent gameOver = new Intent(getApplicationContext(),
                                GameOverScreenActivity.class);
                        gameOver.putExtra("finalScore", currGame.getScore());
                        startActivity(gameOver);
                    }
                    updatePlayerScreenData();
                    playerImage.setColorFilter(null);
                    playState = true;
                }
            }.start();
        }
    }
    /**
     * A method for creating the functionality moving left with the left button
     */
    public void moveLeft() {
        if (currGame.getPosition()[0] > 0) {
            currGame.changePosition(-1, 0);
            updatePlayerScreenData();
        }
        checkOnRiver();
    }

    /**
     * A method for creating the functionality moving right with the right button
     */
    public void moveRight() {
        if (currGame.getPosition()[0] < 8 * currGame.getBlockSize()) {
            currGame.changePosition(1, 0);
            updatePlayerScreenData();
        }
        checkOnRiver();
    }

    /**
     * A method for creating the functionality moving up with the up button
     */
    public void moveUp() {
        if (currGame.getPosition()[1] > 0) {
            currGame.changePosition(0, -1);
            int yCord = currGame.getPosition()[1] / currGame.getBlockSize();
            int vehiclePointAdd = 0;

            if ("fireball".equals(rowTypes[yCord])) {
                vehiclePointAdd += 2;
            } else if ("dragon".equals(rowTypes[yCord])) {
                vehiclePointAdd += 1;
            } else if ("minecart".equals(rowTypes[yCord])) {
                vehiclePointAdd += 3;
            } else if (currGame.getCurrBlock().blockType == GameBlockTypes.GOAL) {
                Intent winScreen = new Intent(this, GameWinScreenActivity.class);
                winScreen.putExtra("finalScore", currGame.getScore());
                startActivity(winScreen);
            }

            currGame.setScore(currGame.getScore() + currGame.getCurrBlock().blockType.travelGain
                    + vehiclePointAdd);
            updatePlayerScreenData();
        }
        checkOnRiver();
    }

    /**
     *  A method for creating the functionality moving down with the down button
     */
    public void moveDown() {
        if (currGame.getPosition()[1] < 14 * currGame.getBlockSize()) {
            currGame.changePosition(0, 1);
            updatePlayerScreenData();
        }
        checkOnRiver();
    }


    /**
     * Method for setting up the grid for the Game
     * @param gridContainer The parent View which holds all the GameBlocks created
     * @param blockSize the size of each square GameBlock
     */
    public void createGrid(LinearLayout gridContainer, int blockSize) {
        for (int row = 0; row < 16; row++) {
            LinearLayout rowBlock = new LinearLayout(this);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    blockSize * 9, blockSize);
            rowBlock.setLayoutParams(params1);

            for (int column = 0; column < 9; column++) {
                ImageView gridBlock = new ImageView(this, null);
                GameBlock g = new GameBlock(row, column, gridBlock);

                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        blockSize, blockSize);
                gridBlock.setLayoutParams(params2);

                rowBlock.addView(gridBlock);
            }
            gridContainer.addView(rowBlock);
        }
    }

    /**
     * Method for assigning row types to the created grid in the game
     * @return int array mapping integers to row types
     */
    public int[] populateGrid() {
        /*
        Goal tile => 3
        Safe tile => 2
        River tile => 1
        Road tile => 0
         */
        int[] rowTypes = new int[16];

        Random r = new Random();

        rowTypes[0] = 3;
        rowTypes[1] = 3;
        rowTypes[14] = 2;
        rowTypes[15] = 2;
        rowTypes[r.nextInt(3) + 7] = 2;

        int type = r.nextInt(2);
        for (int i = 2; i < 15; i++) {
            if (rowTypes[i] == 0) {
                rowTypes[i] = type;
            } else {
                if (type == 1) {
                    type = 0;
                } else {
                    type = 1;
                }
            }
        }

        int[] imageOptions = GameBlock.imageOptions;
        GameBlockTypes[] gbt = GameBlockTypes.values();
        for (int i = 0; i < rowTypes.length; i++) {
            GameBlock[] row = Game.getGameBlockArray()[i];
            for (GameBlock g:row
            ) {
                g.blockType = gbt[rowTypes[i]];
                g.gridBlock.setImageResource(imageOptions[rowTypes[i]]);
            }
        }

        for (int i = 0; i < rowTypes.length; i++) {
            if (rowTypes[i] == 1) {
                GameBlock[] riverRow = Game.getGameBlockArray()[i];
                int begin = r.nextInt(riverRow.length);
                riverRow[begin].blockType = gbt[4];
                riverRow[begin].gridBlock.setImageResource(imageOptions[4]);
                riverRow[(begin + 1) % riverRow.length].blockType = gbt[4];
                riverRow[(begin + 1) % riverRow.length].gridBlock.setImageResource(imageOptions[4]);
                riverRow[(begin + 2) % riverRow.length].blockType = gbt[4];
                riverRow[(begin + 2) % riverRow.length].gridBlock.setImageResource(imageOptions[4]);
            }
        }

        return rowTypes;

    }

    /**
     * Method for starting/calling animations of the rivers and fireball (on roads)
     * @param rows array representing the types of each row in grid;
     */
    public void animate(int[] rows) {
        //Constructs a list of rivers and roads on screen
        HashMap<Integer, LinearLayout> rivers = new HashMap<>();
        ArrayList<LinearLayout> roads = new ArrayList<>();
        int roadStart = 0;
        for (int i = 0; i < rows.length; i++) {
            LinearLayout grid = findViewById(R.id.backgroundGrid);
            if (rows[i] == 1) {
                rivers.put(i, (LinearLayout) grid.getChildAt(i));
            } else if (rows[i] == 0) {
                roadStart = roadStart == 0 ? i : roadStart;
                roads.add((LinearLayout) grid.getChildAt(i));
            }
        }

        for (int i = 0; i < roads.size(); i++) {
            switch (i % 2) {
            case 0: rowTypes[roadStart + i] = "fireball";
                break;
            case 1: rowTypes[roadStart + i] = "dragon";
                break;
            case 2: rowTypes[roadStart + i] = "minecart";
                break;
            default:
            }
        }


        //Animates rivers on screen
        for (Integer rowIndex: rivers.keySet()
             ) {
            moveRiver(rowIndex, rivers.get(rowIndex));
        }
        int i = 1;
        //Animates and moves fireballs on screen
        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        for (LinearLayout road : roads) {
            ImageView vehicle = new ImageView(this);
            mainFrame.addView(vehicle, 0);
            Vehicle vehicleObject = null;
            
            switch (i) {
            case 1:
                vehicleObject = new Fireball(road, vehicle);
                i++;
                break;
            case 2:
                vehicleObject = new Dragon(road, vehicle);
                i++;
                break;
            case 3:
                ImageView tracks = new ImageView(this);
                mainFrame.addView(tracks, 1);
                vehicleObject = new Minecart(road, vehicle, tracks);
                i = 1;
                break;
            default:
            }

            vehicleList.add(vehicleObject);

            testVehicle = vehicleObject;
        }
    }

    /**
     * Method for animating rivers
     * @param rowIndex the position of row in the gameBlockArray
     * @param row the corresponding linear layout holding all GameBlocks in that row
     */
    public void moveRiver(int rowIndex, LinearLayout row) {

        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time % 25 == 0) {
                    ImageView oldBlock = (ImageView) row.getChildAt(0);
                    row.removeViewAt(0);
                    row.addView(oldBlock);
                    Game.shiftGameRow(rowIndex, -1);
                }
            }
        };
        gameClock.addListener(v);
    }

    public void movePlayer() {
        if (currGame.playerOnLog) {
            if (playerImage.getX() != 0) {
                currGame.changePosition(-1, 0);
                updatePlayerScreenData();
            } else {
                playerImage.setX(-currGame.getBlockSize());
            }

        }
    }

    /**
     * gets player string sent from login activity
     * @return string representing user player
     */

    private String getPlayerInfo() {
        if (getIntent().getStringExtra("player") == null) {
            return "Kelley|1|5";
        }
        return getIntent().getStringExtra("player");
    }

    /**
     * Returns game associated with class
     * @return currGame
     */
    public Game getGame() {
        return currGame;
    }

    public static ImageView getPlayerImage() {
        return playerImage;
    }

    public boolean getPlayState() {
        return playState;
    }

    public Vehicle getTestVehicle() {
        return testVehicle;
    }

    public ArrayList<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public static int getTime() {
        return time;
    }

    public static void setCollidedWithVehicle(boolean newStatus) {
        collidedWithVehicle = newStatus;
    }
}

