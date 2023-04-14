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
        image.bringToFront();
        tracks.bringToFront();

        launch();
        animateFrames();
        animateMovement();
    }

    /**
     * Launches minecart.
     */
    public void launch() {
        image.setVisibility(View.INVISIBLE);
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Clock.getTime() < 5) {
                    tracks.setImageResource(R.drawable.traintracks);
                    tracks.setX(row.getX());
                    tracks.setY(row.getY());
                    tracks.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth()),
                            row.getHeight()));
                }
                if (Clock.getTime() > delay && !launched) {
                    launched = true;

                    image.setY(row.getY());
                    image.setImageResource(R.drawable.minecarts);
                    image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth() * 1.5),
                            row.getHeight() - 30));
                    image.setX(row.getWidth());
                    image.setVisibility(View.VISIBLE);
                }
            }
        };
        l.addListener(v);
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
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (Clock.getTime() % 2 == 0) {
                    image.setX(image.getX() - 100);
                }

                if (image.getX() < -image.getWidth()) {
                    image.setX(4 * row.getWidth());
                }
            }
        };
        l.addListener(v);
    }

    /**
     * Checks for collisions.
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