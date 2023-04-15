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

    private final int delay;

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
        c.addScheduledEvents(e -> {
            if (c.getTime() > delay && !launched) {
                launched = true;

                image.setY(row.getY());
                image.setLayoutParams(new FrameLayout.LayoutParams(row.getHeight(),
                        row.getHeight()));
                image.setX(row.getWidth());
                image.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Animates frames of dragon.
     */
    @Override
    public void animateFrames() {
        c.addScheduledEvents(e -> {
            if (launched) {
                if (c.getTime() % 2 == 0) {
                    image.setImageResource(dragonFrames[i[0]]);
                    i[0]++;

                    if (i[0] >= 12) {
                        i[0] = 0;
                    }
                }
            }
        });
    }

    /**
     * Animates movement of dragon.
     */
    @Override
    public void animateMovement() {
        c.addScheduledEvents(e -> {
            checkForCollision();
            if (c.getTime() % 3 == 0) {
                image.setX(image.getX() - 15);
            }

            if (image.getX() < -image.getWidth()) {
                image.setX(row.getWidth());
            }
        });
    }

    /**
     * Checks for collision.
     */
    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getHitRect(rect1);

        Rect rect2 = new Rect();
        GameScreenActivity.getPlayerImage().getHitRect(rect2);

        if (Rect.intersects(rect1, rect2) && launched) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }
}
