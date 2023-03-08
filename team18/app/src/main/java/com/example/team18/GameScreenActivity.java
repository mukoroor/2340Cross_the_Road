package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

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


    private Game currGame;

    private TextView playerLives;
    private TextView playerPoints;

    private ImageView playerImage;
    private int riverSpeed = 1000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Establishes player details
        Sprite player = Sprite.parseString(getPlayerInfo());
        playerImage = findViewById(R.id.player);
        int spriteImageIndex = player.getSpriteIndex();

        currGame = new Game(player);

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

        //navigation buttons
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);
        Button upButton = findViewById(R.id.upButton);
        Button downButton = findViewById(R.id.downButton);

        //moving sprite based on navigation button input
        leftButton.setOnClickListener(e -> moveLeft());
        rightButton.setOnClickListener(e -> moveRight());
        upButton.setOnClickListener(e -> moveUp());
        downButton.setOnClickListener(e -> moveDown());

        //calculating block-size
        View rootView = getWindow().getDecorView().getRootView();
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int blockSize = rootView.getWidth() / 9;
                        currGame.setBlockSize(blockSize);
                        currGame.setMaxHeight(blockSize * 14);
                        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
                                blockSize, blockSize);
                        playerImage.setLayoutParams(p);
                        updatePlayerScreenData();

                        //Creates background
                        createGrid(findViewById(R.id.backgroundGrid), blockSize);
                        int[] rows = populateGrid();

                        //Animates rows on screen
                        animate(rows);

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
        playerLives.setText(String.valueOf(currGame.getPlayer().getLives()));
        playerImage.setX(currGame.getPosition()[0]);
        playerImage.setY(currGame.getPosition()[1]);
    }

    /**
     * A method for creating the functionality moving left with the left button
     */
    public void moveLeft() {
        if (currGame.getPosition()[0] > 0) {
            currGame.changePosition(-1, 0);
            updatePlayerScreenData();
        }
    }

    /**
     * A method for creating the functionality moving right with the right button
     */
    public void moveRight() {
        if (currGame.getPosition()[0] < 8 * currGame.getBlockSize()) {
            currGame.changePosition(1, 0);
            updatePlayerScreenData();
        }
    }

    /**
     * A method for creating the functionality moving up with the up button
     */
    public void moveUp() {
        if (currGame.getPosition()[1] > 0) {
            currGame.changePosition(0, -1);
            currGame.setScore(currGame.getScore() + currGame.getCurrBlock().blockType.travelGain);
            TextView playerName = findViewById(R.id.username);
            playerName.setText(currGame.getCurrBlock().blockType.toString());
            updatePlayerScreenData();
        }
    }

    /**
     *  A method for creating the functionality moving down with the down button
     */
    public void moveDown() {
        if (currGame.getPosition()[1] < 14 * currGame.getBlockSize()) {
            currGame.changePosition(0, 1);
            updatePlayerScreenData();
        }
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
        for (int i = 0; i < rows.length; i++) {
            LinearLayout grid = findViewById(R.id.backgroundGrid);
            if (rows[i] == 1) {
                rivers.put(i, (LinearLayout) grid.getChildAt(i));
            } else if (rows[i] == 0) {
                roads.add((LinearLayout) grid.getChildAt(i));
            }
        }

        //Animates rivers on screen
        for (Integer rowIndex: rivers.keySet()
             ) {
            moveRiver(rowIndex, rivers.get(rowIndex));
        }

        //Animates and moves fireballs on screen
        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        for (LinearLayout road : roads) {
            ImageView fireball = new ImageView(this);
            fireball.setVisibility(View.INVISIBLE);
            mainFrame.addView(fireball, 0);
            animateFireball(fireball);
            shootFireBall(fireball, road);
        }
    }

    /**
     * Method for animating rivers
     * @param rowIndex the position of row in the gameBlockArray
     * @param row the corresponding linear layout holding all GameBlocks in that row
     */
    public void moveRiver(int rowIndex, LinearLayout row) {
        new CountDownTimer(10000, riverSpeed) {
                public void onTick(long millisUntilFinished) {
                    //Moves the blocks in the river
                    ImageView oldBlock = (ImageView) row.getChildAt(0);
                    row.removeViewAt(0);
                    row.addView(oldBlock);

                    Game.shiftGameRow(rowIndex, -1);
                }
                public  void onFinish() {
                    moveRiver(rowIndex, row);
                }
        }.start();
    }

    /**
     * Method for animating fireball
     * @param fireball the image view which is being animated
     */
    public void animateFireball(ImageView fireball) {
        FrameLayout.LayoutParams fireballDims = new FrameLayout.LayoutParams(
                currGame.getBlockSize(), currGame.getBlockSize());
        fireball.setLayoutParams(fireballDims);
        Random r = new Random();
        final int[] fireBallFrames = {
                R.drawable.fball_0,
                R.drawable.fball_1,
                R.drawable.fball_2,
                R.drawable.fball_3,
                R.drawable.fball_4,
                R.drawable.fball_5,
                R.drawable.fball_6,
                R.drawable.fball_7
        };
        final int[] image = {r.nextInt(fireBallFrames.length)};
        new CountDownTimer(fireBallFrames.length*120, 120) {
            public void onTick(long millisUntilFinished) {
                //Changes fireball images
                fireball.setImageResource((fireBallFrames[image[0]]));
                 image[0] = (image[0] + 1) % fireBallFrames.length;
            }

            public void onFinish() {
                animateFireball(fireball);
            }
        }.start();
    }

    /**
     * Method for randomly choosing the start time of fireball when game begins
     * @param fireball fireball that is to be shot
     * @param row road which the fireball will be moving across
     */
    public void shootFireBall(ImageView fireball, LinearLayout row) {
        Random rand = new Random();
        int waitOffset = 1 + rand.nextInt(10);
        int waitTime = waitOffset * 1000;
        new CountDownTimer(waitTime, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                fireballMotion(fireball, row);
            }
        }.start();
    }


    /**
     * Method for moving fireball across the screen
     * @param fireball fireball that is being moved
     * @param row road which the fireball will be moving across
     */
    public void fireballMotion(ImageView fireball, LinearLayout row) {
        int rowWidth = row.getWidth();
        int rowY = (int) row.getY();

        fireball.setY(rowY);
        fireball.setX(rowWidth);
        fireball.setVisibility(View.VISIBLE);

        int translation = rowWidth / 100;
        new CountDownTimer(15000, 100) {
            public void onTick(long millisUntilFinished) {
                //Moves fireball across screen
                int currentX = (int) fireball.getX();
                fireball.setX(currentX - translation);
            }

            public void onFinish() {
                fireballMotion(fireball, row);
            }
        }.start();
    }


    /**
     * gets player string sent from login activity
     * @return string representing user player
     */

    private String getPlayerInfo() {
        return getIntent().getStringExtra("player");
    }

    /**
     * Returns game associated with class
     * @return currGame
     */
    public Game getGame() {
        return currGame;
    }

}