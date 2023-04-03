package com.example.team18;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class Vehicle {
    private LinearLayout row;

    private ImageView image;

    private ImageView tracks;

    private ImageView playerImage;

    private CoupledListeners movementListener;

    private Button clockButton;

    private Game game;

    private int frame = 0;

    private boolean launch = true;

    private boolean frameClock = true;

    private boolean motionClock = true;

    /**
     * Constructor for Vehicle Class.
     * @param row road that vehicle will cross
     * @param image vehicle display
     * @param tracks track that minecart would ride on
     * @param type type of vehicle
     */
    public Vehicle(LinearLayout row, ImageView image, ImageView tracks, int type, ImageView playerImage, Button clockButton, Game game) {
        this.row = row;
        this.image = image;
        this.tracks = tracks;
        this.playerImage = playerImage;
        this.clockButton = clockButton;
        this.game = game;

        this.image.setVisibility(View.INVISIBLE);
        this.tracks.setVisibility(View.INVISIBLE);
        //type = 1 -> fireball
        //type = 2 -> dragon
        //type = 3 -> minecarts
        image.setId(type);
        switch(type) {
            case 1:
                animateFireball();
                break;
            case 2:
                animateDragon();
                break;
            case 3:
                animateMineCarts();
                break;
        }
    }

    /**
     * Animates Fireball.
     */
    public void animateFireball() {
        image.setY(row.getY());
        image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getHeight() * 1.5), row.getHeight()));
        image.setX(-image.getWidth());
        image.setRotation(180);
        image.setVisibility(View.VISIBLE);

        clockButton.setOnClickListener(e -> {
            fireballFrames(); //switches frames of fireball
            fireballMotion(); //moves fireball
        });
    }

    /**
     * Flips through fireball animation.
     */
    public void fireballFrames() {
        int[] fireBallFrames = {
                R.drawable.fball_0,
                R.drawable.fball_1,
                R.drawable.fball_2,
                R.drawable.fball_3,
                R.drawable.fball_4,
                R.drawable.fball_5,
                R.drawable.fball_6,
                R.drawable.fball_7
        };

        if (GameScreenActivity.getTime() % 3 == 0 && frameClock) {
            image.setImageResource(fireBallFrames[frame]);
            frameClock = false;
            frame++;
        }
        if (GameScreenActivity.getTime() % 3 == 1) {
            frameClock = true;
        }
        if (frame >= 8) {
            frame = 0;
        }
    }

    /**
     * Translates fireball across road.
     */
    public void fireballMotion() {
        checkForCollision();
        if (GameScreenActivity.getTime() % 4 == 0 && motionClock) {
            System.out.println(image.getX());
            image.setX(image.getX() + 30);
            motionClock = false;
        }
        if (GameScreenActivity.getTime() % 4 == 1) {
            motionClock = true;
        }
        if (image.getX() > row.getWidth()) {
            image.setX(- image.getWidth());
        }
    }

    /**
     * Animates Dragon.
     */
    public void animateDragon() {
        image.setY(row.getY() - 50);
        image.setX(row.getWidth());
        image.setLayoutParams(new FrameLayout.LayoutParams(row.getHeight() * 2, row.getHeight() + 50));
        image.setVisibility(View.VISIBLE);

        clockButton.setOnClickListener(e -> {
            dragonFrames(); //switches frames of dragon
            dragonMotion(); //moves dragon
        });
    }

    /**
     * Flips through dragon animation.
     */
    public void dragonFrames() {
        int[] fireBallFrames = {
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

        if (GameScreenActivity.getTime() % 3 == 0 && frameClock) {
            image.setImageResource(fireBallFrames[frame]);
            frameClock = false;
            frame++;
        }
        if (GameScreenActivity.getTime() % 3 == 1) {
            frameClock = true;
        }
        if (frame >= 12) {
            frame = 0;
        }
    }

    /**
     * Translates dragon across road.
     */
    public void dragonMotion() {
        checkForCollision();
        if (GameScreenActivity.getTime() % 5 == 0 && motionClock) {
            image.setX(image.getX() - 30);
            motionClock = false;
        }
        if (GameScreenActivity.getTime() % 5 == 1) {
            frameClock = true;
        }
        if (image.getX() < -image.getWidth()) {
            image.setX(row.getWidth());
        }
    }

    /**
     * Animates Minecarts.
     */
    public void animateMineCarts() {
        tracks.setY(row.getY() - 25);
        tracks.setX(0);
        tracks.setImageResource(R.drawable.traintracks);
        tracks.setLayoutParams(new FrameLayout.LayoutParams(row.getWidth(), row.getHeight()));
        tracks.setVisibility(View.VISIBLE);

        image.setY(row.getY() - 10);
        image.setX(row.getWidth());
        image.setImageResource(R.drawable.minecarts);
        image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth() * 1.5), row.getHeight() - 20));
        image.setVisibility(View.VISIBLE);

        clockButton.setOnClickListener(e -> {
            mineCartMotion(); //moves train
        });
    }

    /**
     * Translates minecarts across road.
     */
    public void mineCartMotion() {
        checkForCollision();
        if (GameScreenActivity.getTime() % 10 == 0) {
            launch = true;
        }
        if (GameScreenActivity.getTime() % 2 == 0 && launch && motionClock) {
            image.setX(image.getX() - 30);
            motionClock = false;
        }
        if (GameScreenActivity.getTime() % 2 == 1) {
            motionClock = true;
        }
        if (image.getX() < -image.getWidth()) {
            image.setX(row.getWidth());
            launch = false;
        }
    }

    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getGlobalVisibleRect(rect1);

        Rect rect2 = new Rect();
        playerImage.getGlobalVisibleRect(rect2);

        if (Rect.intersects(rect1, rect2)) {
           System.out.println("Game Over!!!");
           game.reset();
        }
    }


}
