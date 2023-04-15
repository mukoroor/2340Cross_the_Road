package com.example.team18;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class MineCart extends Vehicle {
    private final LinearLayout row;

    private final ImageView image;

    private final ImageView tracks;

    private final int delay;
    private boolean launched = false;

    /**
     * MineCart Constructor.
     * @param row row mine cart rides across
     * @param image image of mine cart
     * @param tracks image of train tracks
     */
    public MineCart(LinearLayout row, ImageView image, ImageView tracks) {
        super();
        this.row = row;
        this.image = image;
        this.tracks = tracks;

        Random rand = new Random();
        delay = rand.nextInt(150) + 1;
        tracks.bringToFront();
        image.bringToFront();

        launch();
        animateFrames();
        animateMovement();
    }

    /**
     * Launches mine cart.
     */
    public void launch() {
        image.setVisibility(View.INVISIBLE);
        c.addScheduledEvents(e -> {
            if (c.getTime() < 5) {
                tracks.setImageResource(R.drawable.traintracks);
                tracks.setX(row.getX());
                tracks.setY(row.getY());
                tracks.setLayoutParams(new FrameLayout.LayoutParams(row.getWidth(),
                        row.getHeight()));
            }
            if (c.getTime() > delay && !launched) {
                launched = true;

                image.setY(row.getY());
                image.setImageResource(R.drawable.minecarts);
                image.setLayoutParams(new FrameLayout.LayoutParams(row.getWidth(),
                        row.getHeight()));
                image.setX(row.getWidth());
                image.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * Animates frames of mine cart.
     */
    @Override
    public void animateFrames() {
    }

    /**
     * Animates movement of mine cart.
     */
    @Override
    public void animateMovement() {
        c.addScheduledEvents(e -> {
            checkForCollision();
            if (c.getTime() % 2 == 0) {
                image.setX(image.getX() - 50);
            }

            if (image.getX() < -image.getWidth()) {
                image.setX(3 * row.getWidth());
            }
        });
    }

    /**
     * Checks for collisions.
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