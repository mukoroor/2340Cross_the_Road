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

    private int delay = 0;

    private final int[] i = {0};
    private final int[] dragonFrames = {
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
     * @param row row dragon flies across
     * @param image image of dragon
     */
    public Dragon(LinearLayout row, ImageView image) {
        super();
        this.row = row;
        this.image = image;

        Random rand = new Random();
        delay = rand.nextInt(150) + 1;
        image.bringToFront();

        launch();
        animateFrames();
        animateMovement();
    }

    /**
     * Launches dragon.
     */
    public void launch() {
        image.setVisibility(View.INVISIBLE);
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Clock.getTime() > delay && !launched) {
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
     * Animates frames of dragon.
     */
    @Override
    public void animateFrames() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (launched) {
                    if (Clock.getTime() % 2 == 0) {
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
     * Animates movement of dragon.
     */
    @Override
    public void animateMovement() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (Clock.getTime() % 3 == 0) {
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
     * Checks for collision.
     */
    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getGlobalVisibleRect(rect1);

        Rect rect2 = new Rect();
        GameScreenActivity.getPlayerImage().getGlobalVisibleRect(rect2);

        if (Rect.intersects(rect1, rect2) && launched) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }
}
