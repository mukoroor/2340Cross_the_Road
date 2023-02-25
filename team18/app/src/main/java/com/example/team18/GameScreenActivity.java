package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {
    private int difficulty;
    private int finalHeight, finalWidth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        //Establishes player details
        Sprite player = Sprite.parseString(getPlayerInfo());
        ImageView playerImage = findViewById(R.id.player);
        int spriteImageIndex = player.getSpriteIndex();

        //Sets player image on screen
        playerImage.setImageResource(Sprite.spriteOptions[spriteImageIndex][0]);

        //Sets player name on screen
        TextView playerName = findViewById(R.id.username);
        playerName.setText(player.getName());

        //Sets player lives on screen
        TextView playerLives = findViewById(R.id.playerLives);
        playerLives.setText(String.valueOf(getGameLives()));

        //Creates background
        createGrid(findViewById(R.id.backgroundGrid));
        int[] rows = populateGrid();
        final FrameLayout iv = (FrameLayout) findViewById(R.id.mainFrame);
        finalHeight = iv.getMeasuredHeight();
        finalWidth = iv.getMeasuredWidth();
        System.out.println(finalWidth);

        //Animates rows on screen
        animate(rows);

        //Big O Code
        //int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int difficulty= getGameDifficulty();
        //Game currentGame = new Game(player, difficulty, displayWidth);


        //The action events for the leftButton and the placement of the left button
        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setX(50);
        leftButton.setY(50);
        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                moveLeft(playerImage);

            }
        });

        //The action events for the rightButton and the placement of the right button
        Button rightButton = (Button) findViewById(R.id.rightButton);
        rightButton.setX(1050);
        rightButton.setY(50);
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveRight(playerImage);
            }
        });


        //The action events for the upbutton and the placement of the up button
        Button upButton = (Button) findViewById(R.id.upButton);
        upButton.setX(500);
        upButton.setY(50);


        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveUp(playerImage);
            }
        });

        //The action events for the downbutton and the placement of the down button
        Button downButton = (Button) findViewById(R.id.downButton);
        downButton.setX(500);
        downButton.setY(300);


        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveDown(playerImage);
            }
        });


    }

    /**
     * A method for creating the functionality moving left with the left button
     * @param playerImage the image of the player on the screen
     */
    public void moveLeft(ImageView playerImage) {
        final FrameLayout iv = (FrameLayout) findViewById(R.id.mainFrame);
        finalHeight = iv.getMeasuredHeight();
        finalWidth = iv.getMeasuredWidth();

        if (playerImage.getX() > 10) {
            playerImage.setX(playerImage.getX() - 30);
        }

    }

    /**
     * A method for creating the functionality moving right with the right button
     * @param playerImage the image of the player on the screen
     */
    public void moveRight(ImageView playerImage) {
        final FrameLayout iv = (FrameLayout) findViewById(R.id.mainFrame);
        finalHeight = iv.getMeasuredHeight();
        finalWidth = iv.getMeasuredWidth();

        if (playerImage.getX() < finalWidth - 300) {
            playerImage.setX(playerImage.getX() + 30);
        }

    }


    /**
     * A method for creating the functionality moving up with the up button
     * @param playerImage the image of the player on the screen
     */
    public void moveUp(ImageView playerImage) {
        final FrameLayout iv = (FrameLayout) findViewById(R.id.mainFrame);
        finalHeight = iv.getMeasuredHeight();
        finalWidth = iv.getMeasuredWidth();

        if (playerImage.getY() > finalHeight - 2200) {
            playerImage.setY(playerImage.getY() - 30);
            System.out.println("Player height :" + playerImage.getY());
        }

    }

    /**
     *  A method for creating the functionality moving down with the down button
     * @param playerImage the image of the player on the screen
     */
    public void moveDown(ImageView playerImage) {


        if (playerImage.getY() < 2172) {
            playerImage.setY(playerImage.getY() + 30);
            System.out.println("Player height :" + 2200);
            System.out.println("Player height :" + playerImage.getY());
        }

    }


    public void createGrid(LinearLayout gridContainer) {
        gridContainer.removeAllViews();
        int blockSize = 160;
        for (int row = 0; row < 16; row++) {
            LinearLayout rowBlock = new LinearLayout(this);

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(blockSize * 9, blockSize);
            params1.weight = 1.0f;
            rowBlock.setLayoutParams(params1);

            for (int column = 0; column < 9; column++) {
                ImageView gridBlock = new ImageView(this, null);
                GameBlock g = new GameBlock(row, column, gridBlock);

                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(blockSize, blockSize);
                params2.weight = 1.0f;
                gridBlock.setLayoutParams(params2);

                rowBlock.addView(gridBlock);
            }
            gridContainer.addView(rowBlock);
        }
    }

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
        rowTypes[15] = 2;
        rowTypes[r.nextInt(3) + 7] = 2;

        int type = r.nextInt(2);
        for (int i = 1; i < 15; i++) {
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


        int[] blockOptions = GameBlock.blockOptions;
        for (int i = 0; i < rowTypes.length; i++) {
            GameBlock[] row = Game.gameBlockArray[i];
            for (GameBlock g:row
            ) {
                g.gridBlock.setImageResource(blockOptions[rowTypes[i]]);
            }
        }

        for (int i = 0; i < rowTypes.length; i++) {
            if (rowTypes[i] == 1) {
                GameBlock[] riverRow = Game.gameBlockArray[i];
                int begin = r.nextInt(riverRow.length);
                riverRow[begin].gridBlock.setImageResource(blockOptions[4]);
                riverRow[(begin + 1) % riverRow.length].gridBlock.setImageResource(blockOptions[4]);
                riverRow[(begin + 2) % riverRow.length].gridBlock.setImageResource(blockOptions[4]);
            }
        }

        return rowTypes;

    }

    public void animate(int[] rows) {
        //Constructs a list of rivers and roads on screen
//        ArrayList<LinearLayout> rivers = new ArrayList<>();
        HashMap<Integer, LinearLayout> rivers = new HashMap<>();
        ArrayList<LinearLayout> roads = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            LinearLayout grid = findViewById(R.id.backgroundGrid);
            switch (rows[i]) {
                case 1:
                    rivers.put(i, (LinearLayout) grid.getChildAt(i));
                    break;
                case 0:
                    roads.add((LinearLayout) grid.getChildAt(i));
                    break;
            }
        }

        //Animates rivers on screen
        for (Integer rowIndex: rivers.keySet()
             ) {
            moveRiver(rowIndex, rivers.get(rowIndex));
        }

//        for (LinearLayout river : rivers) {
//            moveRiver(river);
//        }

        //Animates and moves fireballs on screen
        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        for (LinearLayout road : roads) {
            ImageView fireball = new ImageView(this);
            mainFrame.addView(fireball, 1);
            animateFireball(fireball);
            shootFireBall(fireball, road);
        }
    }

    public void moveRiver(int rowIndex, LinearLayout row) {
        new CountDownTimer(10000, 1000){
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

    public void animateFireball(ImageView fireball) {
        FrameLayout.LayoutParams fireballDims = new FrameLayout.LayoutParams(160, 160);
        fireball.setLayoutParams(fireballDims);
        final int[] image = {0};
        final int[] fireBallFrames = {R.drawable.fireball0, R.drawable.fireball1, R.drawable.fireball2, R.drawable.fireball3, R.drawable.fireball4, R.drawable.fireball5, R.drawable.fireball6, R.drawable.fireball7};
        new CountDownTimer(800, 100) {
            public void onTick(long millisUntilFinished) {
                //Changes fireball images
                fireball.setImageResource((fireBallFrames[image[0]]));
                image[0]++;
            }

            public void onFinish() {
                animateFireball(fireball);
            }
        }.start();
    }

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

    public void fireballMotion(ImageView fireball, LinearLayout row) {
        int rowWidth = row.getWidth();
        int rowY = (int) row.getY();
//        System.out.println("Row Width: " + rowWidth);
//        System.out.println("Row Y: " + rowY);

        fireball.setY(rowY);
        fireball.setX(rowWidth);

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

    private int getGameLives() {
        return getIntent().getIntExtra("lives", 5);
    }

    private String getPlayerInfo() {
//        return "k3ll3y|3|1";
        return getIntent().getStringExtra("player");
    }


}