package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {
    private Clock gameClock;
    private Game currGame;
    private TextView playerLives;
    private TextView playerPoints;
    private final Random r = new Random();
    private static ImageView playerImage;
    private boolean playState = true;
    private static boolean collidedWithVehicle = false;
    protected String[] rowTypes;
    private int newTranslation;
    private Vehicle testVehicle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Establishes player details
        Sprite player = Sprite.parseString(getPlayerInfo());
        int lives = getDifficulty();
        //Sets player image on screen
        playerImage = findViewById(R.id.player);
        int spriteImageIndex = player.getSpriteIndex();
        playerImage.setImageResource(Sprite.spriteOptions[spriteImageIndex][0]);
        //Sets player name on screen
        TextView playerName = findViewById(R.id.username);
        playerName.setText(player.getName());
        //Sets player lives on screen
        playerLives = findViewById(R.id.playerLives);
        playerLives.setText(String.valueOf(lives));
        //Sets player score
        playerPoints = findViewById(R.id.points);
        playerPoints.setText("0");

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

        currGame = new Game(player, lives);
        rowTypes = new String[Game.getGameBlockArray().length];
        gameClock = new Clock(new Button(this), new CoupledListeners());
        Vehicle.l = gameClock.getListener();

        //calculating block-size
        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int blockSize = rootView.getWidth() / 9;
                    currGame.setBlockSize(blockSize);

                    //Creates background
                    LinearLayout bG = findViewById(R.id.backgroundGrid);
                    newTranslation = (-Game.getGameBlockArray().length + 15) * blockSize;
                    findViewById(R.id.mainFrame).setY(newTranslation);
                    currGame.createGrid(getApplicationContext());
                    createUIGrid(bG, blockSize);
                    int[] rows = currGame.populateGrid();
                    animate(rows);

                    FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                            blockSize, blockSize);
                    playerImage.setLayoutParams(p);
                    updatePlayerScreenData();
                    playerImage.bringToFront();
                    //Animates rows on screen
                    new CountDownTimer(Long.MAX_VALUE, 30) {
                        public void onTick(long millisUntilFinished) {
                            if (collidedWithVehicle) {
                                collidedWithVehicle = false;
                                playState = false;
                                reset();
                                updatePlayerScreenData();
                            }
                            gameClock.dispatch();
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

    /**
     * method for updating the player positioning, score and lives
     */
    public void updatePlayerScreenData() {
        playerPoints.setText(String.valueOf(currGame.getScore()));
        playerLives.setText(String.valueOf(currGame.getLives()));
        playerImage.setX(currGame.getPosition()[0]);
        playerImage.setY(currGame.getPosition()[1]);
    }

    public void flicker() {
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
                if (currGame.getLives() == 0) {
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
            newTranslation += currGame.getBlockSize();
            int yCord = currGame.getPosition()[1] / currGame.getBlockSize();
            int vehiclePointAdd = 0;

            if ("fireball".equals(rowTypes[yCord])) {
                vehiclePointAdd += 2;
            } else if ("dragon".equals(rowTypes[yCord])) {
                vehiclePointAdd += 1;
            } else if ("mineCart".equals(rowTypes[yCord])) {
                vehiclePointAdd += 3;
            } else if (currGame.getCurrBlock().blockType == GameBlockTypes.GOAL) {
                Intent winScreen = new Intent(this, GameWinScreenActivity.class);
                winScreen.putExtra("finalScore", currGame.getScore());
                startActivity(winScreen);
            }


            currGame.setScore(currGame.getScore() +
                    currGame.getCurrBlock().blockType.getTravelGain() + vehiclePointAdd);
            updatePlayerScreenData();

            if (currGame.getCurrBlock().blockType == GameBlockTypes.SAFE) {
                reFocus();
                return;
            }
        }
        checkOnRiver();
    }

    /**
     *  A method for creating the functionality moving down with the down button
     */
    public void moveDown() {
        if (currGame.getPosition()[1] <
                (Game.getGameBlockArray().length - 1) * currGame.getBlockSize()) {
            currGame.changePosition(0, 1);
            newTranslation -= currGame.getBlockSize();
            updatePlayerScreenData();
            if (currGame.getCurrBlock().blockType == GameBlockTypes.SAFE) {
                reFocus();
            }
        }
        checkOnRiver();
    }

    public void checkOnRiver() {
        if (currGame.getCurrBlock().blockType == GameBlockTypes.RIVER) {
            playState = false;
            reset();
        }
    }

    public void reFocus() {
        playState = false;
        View mainFrame = findViewById(R.id.mainFrame);
        if (newTranslation < 0) {
            mainFrame.animate()
                    .translationY(newTranslation - currGame.getBlockSize()).
                    setDuration(2000)
                    .setInterpolator(new AccelerateDecelerateInterpolator()).start();
        } else {
            mainFrame.animate()
                    .translationY(0).
                    setDuration(2000)
                    .setInterpolator(new AccelerateDecelerateInterpolator()).start();
        }
//        new CountDownTimer(2000, 2000) {
//            @Override
//            public void onTick(long l) {
//            }
//
//            @Override
//            public void onFinish() {
//                playState = true;
//            }
//        }.start();
        playState = true;
    }


    /**
     * Method for setting up the grid for the Game
     * @param gridContainer The parent View which holds all the GameBlocks created
     * @param blockSize the size of each square GameBlock
     */
    public void createUIGrid(LinearLayout gridContainer, int blockSize) {
        for (int row = 0; row < Game.getGameBlockArray().length; row++) {
            LinearLayout rowBlock = new LinearLayout(this);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    blockSize * 9, blockSize);
            rowBlock.setLayoutParams(params1);

            for (int column = 0; column < 9; column++) {
                ImageView gridBlock = Game.getGameBlockArray()[row][column].gridBlock;
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                        blockSize, blockSize);
                gridBlock.setLayoutParams(params2);
                rowBlock.addView(gridBlock);
            }
            gridContainer.addView(rowBlock);
        }
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
            case 2: rowTypes[roadStart + i] = "mineCart";
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
        int gameBlockSize = currGame.getBlockSize();
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

            testVehicle = vehicleObject;
        }
    }
    /**
     * Method for animating rivers
     * @param rowIndex the position of row in the gameBlockArray
     * @param row the corresponding linear layout holding all GameBlocks in that row
     */
    public void moveRiver(int rowIndex, LinearLayout row) {
        int direction = r.nextInt(2) > 0 ? 8 : 0;
        gameClock.addScheduledEvents(e -> {
            if (gameClock.getTime() % 30 == 0) {
                ImageView oldBlock = (ImageView) row.getChildAt(direction);
                row.removeViewAt(direction);
                row.addView(oldBlock, direction > 0 ? 0 : 8);
                if (playerImage.getY() == row.getY()) {
                    movePlayerWithLog(direction > 0 ? 1 : -1);
                }
                Game.shiftGameRow(rowIndex, direction > 0 ? 1 : -1);
            }
        });
    }

    public void movePlayerWithLog(int direction) {
        if (currGame.getCurrBlock().blockType == GameBlockTypes.LOG) {
            if (direction == -1) {
                if (playerImage.getX() != 0) {
                    currGame.changePosition(-1, 0);
                } else {
                    reset();
                }
            } else {
                if (playerImage.getX() != 8 * currGame.getBlockSize()) {
                    currGame.changePosition(1, 0);
                } else {
                    reset();
                }
            }

            updatePlayerScreenData();
        }
    }

    public void reset() {
        currGame.setScore(currGame.getScore() / 2);
        currGame.setLives(currGame.getLives() - 1);
        currGame.resetPosition();
        newTranslation = (-Game.getGameBlockArray().length + 16) * currGame.getBlockSize();
        reFocus();
        newTranslation -= currGame.getBlockSize();
        flicker();
    }

    /**
     * gets player string sent from login activity
     * @return string representing user player
     */
    private String getPlayerInfo() {
        return "1|KELLEY";
        //return getIntent().getStringExtra("player");
    }

    private int getDifficulty() {
        return getIntent().getIntExtra("lives", 5);
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

    public static void setCollidedWithVehicle(boolean newStatus) {
        collidedWithVehicle = newStatus;
    }
}

