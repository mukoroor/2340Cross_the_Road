package com.example.team18;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class Fireball extends Vehicle {

    private final LinearLayout row;

    private final ImageView image;

    private final int delay;

    private final int[] i = {0};

    private final int[] fireBallFrames = {
        R.drawable.fball_0,
        R.drawable.fball_1,
        R.drawable.fball_2,
        R.drawable.fball_3,
        R.drawable.fball_4,
        R.drawable.fball_5,
        R.drawable.fball_6,
        R.drawable.fball_7};

    private boolean launched = false;

    /**
     * Fireball Constructor
     * @param row row fireball flies across
     * @param image image of fireball
     */
    public Fireball(LinearLayout row, ImageView image) {
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
     * Launches fireball.
     */
    public void launch() {
        image.setVisibility(View.INVISIBLE);
        c.addScheduledEvents(e -> {
            if (c.getTime() > delay && !launched) {
                launched = true;

                image.setY(row.getY());
                image.setLayoutParams(new FrameLayout.LayoutParams(
                        row.getHeight(), row.getHeight()));
                image.setX(-image.getWidth());
                image.setRotation(180);
                image.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Animates frames of fireballs.
     */
    @Override
    public void animateFrames() {
        c.addScheduledEvents(e -> {
            if (launched) {
                if (c.getTime() % 5 == 0) {
                    image.setImageResource(fireBallFrames[i[0]]);
                    i[0]++;

                    if (i[0] >= 8) {
                        i[0] = 0;
                    }
                }
            }
        });
    }

    /**
     * Animates movement of fireball.
     */
    @Override
    public void animateMovement() {
        c.addScheduledEvents(e -> {
            if (launched) {
                image.setVisibility(View.VISIBLE);
                checkForCollision();
                if (c.getTime() % 2 == 0) {
                    image.setX(image.getX() + 25);
                    System.out.println(image.getX());
                }

                if (image.getX() > row.getWidth()) {
                    image.setX(-image.getWidth());
                }
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
