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

        //Animates rows on screen
        int[] rows = {2,2,1,1,1,1,1,2,0,0,0,0,0,2,2};
        animate(rows);

        //Big O Code
        //int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        //int difficulty= getGameDifficulty();
        //Game currentGame = new Game(player, difficulty, displayWidth);
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
        for (LinearLayout road : roads) {
            ImageView fireball = new ImageView(this);
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
        final int[] image = {0};
        new CountDownTimer(800, 100){
            public void onTick(long millisUntilFinished) {
                //Changes fireball images
                switch (image[0]) {
                    case 0:
                        fireball.setImageResource(R.drawable.fireball0);
                        break;
                    case 1:
                        fireball.setImageResource(R.drawable.fireball1);
                        break;
                    case 2:
                        fireball.setImageResource(R.drawable.fireball2);
                        break;
                    case 3:
                        fireball.setImageResource(R.drawable.fireball3);
                        break;
                    case 4:
                        fireball.setImageResource(R.drawable.fireball4);
                        break;
                    case 5:
                        fireball.setImageResource(R.drawable.fireball5);
                        break;
                    case 6:
                        fireball.setImageResource(R.drawable.fireball6);
                        break;
                    case 7:
                        fireball.setImageResource(R.drawable.fireball7);
                        break;
                }
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
        new CountDownTimer(10000, 100){
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