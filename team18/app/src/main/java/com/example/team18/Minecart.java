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

    private final ImageView playerImage;

    private int delay = 0;

    final int[] i = {0};

    private boolean launched = false;

    /**
     * Dragon Constructor
     * @param row row that the dragon flies across
     * @param image image of the dragon
     * @param playerImage image of the player
     */
    public Minecart(LinearLayout row, ImageView image, ImageView tracks, ImageView playerImage) {
        super();
        this.row = row;
        this.image = image;
        this.tracks = tracks;
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
                if (GameScreenActivity.getTime() < 5) {
                    tracks.setImageResource(R.drawable.traintracks);
                    tracks.setX(row.getX());
                    tracks.setY(row.getY());
                    tracks.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth()),
                            row.getHeight()));
                    tracks.setVisibility(View.INVISIBLE);
                }
                if (GameScreenActivity.getTime() > delay && !launched) {
                    launched = true;

                    image.setY(row.getY());
                    image.setImageResource(R.drawable.minecarts);
                    image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth() * 1.5),
                            row.getHeight() - 20));
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
                if (GameScreenActivity.getTime() % 2 == 0) {
                    image.setX(image.getX() - 100);
                }

                if (image.getX() < -image.getWidth()) {
                    image.setX(2 * row.getWidth());
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

        if (Rect.intersects(rect1, rect2) && launched) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }
}
