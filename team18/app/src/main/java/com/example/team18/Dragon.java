package com.example.team18;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class Dragon extends Vehicle {
    private final LinearLayout row;

    private final ImageView image;

    private final ImageView playerImage;

    private int delay = 0;

    final int[] i = {0};
    int[] dragonFrames = {
            R.drawable.dragon_0,
            R.drawable.dragon_1,
            R.drawable.dragon_2,
            R.drawable.dragon_3,
            R.drawable.dragon_4,
            R.drawable.dragon_5,
            R.drawable.dragon_6,
            R.drawable.dragon_7,
            R.drawable.dragon_8,
            R.drawable.dragon_9,
            R.drawable.dragon_10,
            R.drawable.dragon_11
    };

    private boolean launched = false;

    /**
     * Dragon Constructor
     * @param row row that the dragon flies across
     * @param image image of the dragon
     * @param playerImage image of the player
     */
    public Dragon(LinearLayout row, ImageView image, ImageView playerImage) {
        super();
        this.row = row;
        this.image = image;
        this.playerImage = playerImage;

        Random rand = new Random();
        delay = rand.nextInt(150) + 1;

        launch();
        animateFrames();
        animateMovement();
    }

    /**
     * Launches dragon at start of game.
     */
    public void launch() {
        image.setVisibility(View.INVISIBLE);
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameScreenActivity.getTime() > delay && !launched) {
                    launched = true;

                    image.setY(row.getY() - 50);
                    image.setLayoutParams(new FrameLayout.LayoutParams(row.getHeight() * 2,
                            row.getHeight() + 50));
                    image.setX(row.getWidth());
                    image.setVisibility(View.VISIBLE);
                }
            }
        };
        l.addListener(v);
    }

    /**
     * Switches the frames of the dragon image.
     */
    @Override
    public void animateFrames() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (launched) {
                    if (GameScreenActivity.getTime() % 2 == 0) {
                        image.setImageResource(dragonFrames[i[0]]);
                        i[0]++;

                        if (i[0] >= 12) {
                            i[0] = 0;
                        }
                    }
                }
            }
        };
        l.addListener(v);
    }

    /**
     * Translates dragon across road.
     */
    @Override
    public void animateMovement() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (GameScreenActivity.getTime() % 3 == 0) {
                    image.setX(image.getX() - 30);
                }

                if (image.getX() < -image.getWidth()) {
                    image.setX(row.getWidth());
                }
            }
        };
        l.addListener(v);
    }

    /**
     * Consistently checks for collisions.
     */
    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getGlobalVisibleRect(rect1);

        Rect rect2 = new Rect();
        playerImage.getGlobalVisibleRect(rect2);

        if (Rect.intersects(rect1, rect2)) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }
}
