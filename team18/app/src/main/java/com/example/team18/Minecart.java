package com.example.team18;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class Minecart extends Vehicle {
    private final LinearLayout row;

    private final ImageView image;

    private final ImageView tracks;

    private int delay = 0;

    private final int[] i = {0};

    private boolean launched = false;

    /**
     * Minecart Constructor.
     * @param row row minecart rides across
     * @param image image of minecart
     * @param tracks image of train tracks
     */
    public Minecart(LinearLayout row, ImageView image, ImageView tracks) {
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
     * Launches minecart.
     */
    public void launch() {
        image.setVisibility(View.INVISIBLE);
        c.addScheduledEvents(e -> {
            if (c.getTime() < 10) {
                tracks.setY(row.getY());
                tracks.setImageResource(R.drawable.traintracks);
                tracks.setLayoutParams(new FrameLayout.LayoutParams(row.getWidth(),
                        row.getHeight()));
                tracks.setX(0);
                tracks.setVisibility(View.VISIBLE);
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
     * Animates frames of minecart.
     */
    @Override
    public void animateFrames() {
    }

    /**
     * Animates movement of minecart.
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

    public LinearLayout getRow() {
        return row;
    }

    public ImageView getImage() {
        return image;
    }

    public ImageView getTracks() {
        return tracks;
    }
}