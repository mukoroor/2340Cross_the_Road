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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GameScreenActivity extends AppCompatActivity {

    private Game currGame;

    private TextView playerLives, playerPoints;

    private ImageView playerImage;



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
        Button leftButton = (Button) findViewById(R.id.leftButton);
        Button rightButton = (Button) findViewById(R.id.rightButton);
        Button upButton = (Button) findViewById(R.id.upButton);
        Button downButton = (Button) findViewById(R.id.downButton);

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

    public void updatePlayerScreenData() {
        playerPoints.setText(String.valueOf(currGame.getScore()));
        playerLives.setText(String.valueOf(currGame.getPlayer().getLives()));
        playerImage.setX(currGame.getPosition()[0]);
        playerImage.setY(currGame.getPosition()[1]);
    }

    /**
     * A method for creating the functionality moving left with the left button
     *
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
            currGame.setScore(currGame.getScore() + 1);
            currGame.changePosition(0, -1);
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
        FrameLayout.LayoutParams fireballDims = new FrameLayout.LayoutParams(
                currGame.getBlockSize(), currGame.getBlockSize());
        fireball.setLayoutParams(fireballDims);
        final int[] image = {0};
        final int[] fireBallFrames = {R.drawable.fireball0, R.drawable.fireball1,
                R.drawable.fireball2, R.drawable.fireball3, R.drawable.fireball4,
                R.drawable.fireball5, R.drawable.fireball6, R.drawable.fireball7};
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

    private String getPlayerInfo() {
        return getIntent().getStringExtra("player");
    }


}