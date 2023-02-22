package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {
    private int difficulty;

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
        Integer wrap = (Integer) player.getLives();
        playerLives.setText(wrap.toString());

        //Creates background
        createGrid(findViewById(R.id.backgroundGrid));
        int[] rows = populateGrid();

        //Animates rows on screen
        animate(rows);

        //Big O Code
        //int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int difficulty= getGameDifficulty();
        //Game currentGame = new Game(player, difficulty, displayWidth);
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


        for (int i = 0; i < rowTypes.length; i++) {
            GameBlock[] row = GameBlock.gameBlockArray[i];
            for (GameBlock g:row
            ) {
                g.gridBlock.setImageResource(GameBlock.blockOptions[rowTypes[i]]);
            }
        }

        for (int i = 0; i < rowTypes.length; i++) {
            if (rowTypes[i] == 1) {
                GameBlock[] riverRow = GameBlock.gameBlockArray[i];
                int begin = r.nextInt(riverRow.length);
                riverRow[begin].gridBlock.setImageResource(GameBlock.blockOptions[4]);
                riverRow[(begin + 1) % riverRow.length].gridBlock.setImageResource(GameBlock.blockOptions[4]);
                riverRow[(begin + 2) % riverRow.length].gridBlock.setImageResource(GameBlock.blockOptions[4]);
            }
        }

        return rowTypes;

    }

    public void animate(int[] rows) {
        //Constructs a list of rivers and roads on screen
        ArrayList<LinearLayout> rivers = new ArrayList<>();
        ArrayList<LinearLayout> roads = new ArrayList<>();
        for (int i = 0; i < rows.length; i++) {
            LinearLayout grid = findViewById(R.id.backgroundGrid);
            switch (rows[i]) {
                case 1:
                    rivers.add((LinearLayout) grid.getChildAt(i));
                    break;
                case 0:
                    roads.add((LinearLayout) grid.getChildAt(i));
                    break;
            }
        }

        //Animates rivers on screen
        for (LinearLayout river : rivers) {
            moveRiver(river);
        }

        //Animates and moves fireballs on screen
        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        for (LinearLayout road : roads) {
            ImageView fireball = new ImageView(this);
            mainFrame.addView(fireball,1);
            animateFireball(fireball);
            shootFireBall(fireball, road);
        }
    }

    public void moveRiver(LinearLayout row) {
        new CountDownTimer(10000, 1000){
                public void onTick(long millisUntilFinished) {
                    //Moves the blocks in the river
                    ImageView oldBlock = (ImageView) row.getChildAt(0);
                    row.removeViewAt(0);
                    row.addView(oldBlock);
                }
                public  void onFinish() {
                    moveRiver(row);
                }
        }.start();
    }

    public void animateFireball(ImageView fireball) {
        FrameLayout.LayoutParams fireballDims = new FrameLayout.LayoutParams(160, 160);
        fireball.setLayoutParams(fireballDims);
        final int[] image = {0};
        final int[] fireBallFrames = { R.drawable.fireball0, R.drawable.fireball1, R.drawable.fireball2, R.drawable.fireball3,R.drawable.fireball4,R.drawable.fireball5,R.drawable.fireball6,R.drawable.fireball7};
        new CountDownTimer(800, 100){
            public void onTick(long millisUntilFinished) {
                //Changes fireball images
                fireball.setImageResource((fireBallFrames[image[0]]));
                image[0]++;
            }
            public  void onFinish() {
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
            public  void onFinish() {
                fireballMotion(fireball, row);
            }
        }.start();
    }

    public void fireballMotion(ImageView fireball, LinearLayout row) {
        int rowWidth = row.getWidth();
        int rowY = (int) row.getY();
        System.out.println("Row Width: " + rowWidth);
        System.out.println("Row Y: " + rowY);

        fireball.setY(rowY);
        fireball.setX(rowWidth);

        int translation = rowWidth / 100;
        new CountDownTimer(15000, 100){
            public void onTick(long millisUntilFinished) {
                //Moves fireball across screen
                int currentX = (int) fireball.getX();
                fireball.setX(currentX - translation);

            }
            public  void onFinish() {
                fireballMotion(fireball, row);
            }
        }.start();
    }

    private int getGameDifficulty() {
        return getIntent().getIntExtra("level", 0);
    }

    private String getPlayerInfo() {
        return "k3ll3y|3|1";
        //return getIntent().getStringExtra("player");
    }
}