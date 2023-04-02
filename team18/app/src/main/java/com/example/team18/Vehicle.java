package com.example.team18;

import android.graphics.Rect;
import android.view.View;
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

    private Game game;

    private int time = 0;

    private int frame = 0;

    /**
     * Constructor for Vehicle Class.
     * @param row road that vehicle will cross
     * @param image vehicle display
     * @param tracks track that minecart would ride on
     * @param type type of vehicle
     */
    public Vehicle(LinearLayout row, ImageView image, ImageView tracks, int type, ImageView playerImage, CoupledListeners movementListener, Game game) {
        this.row = row;
        this.image = image;
        this.tracks = tracks;
        this.playerImage = playerImage;
        this.movementListener = movementListener;
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

        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time <= 60) {
                    time++;
                    System.out.print(time);
                } else {
                    time = 0;
                }
            }
        };
        movementListener.addListener(v);
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

        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fireballFrames(); //switches frames of fireball
                fireballMotion(); //moves fireball
            }
        };
        movementListener.addListener(v);
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

        if (time % 3 == 0) {
            image.setImageResource(fireBallFrames[frame]);
            frame++;
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
        if (time % 4 == 0) {
            image.setX(image.getX() + 30);
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

        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dragonFrames(); //switches frames of dragon
                dragonMotion(); //moves dragon
            }
        };
        movementListener.addListener(v);
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

        if (time % 3 == 0) {
            image.setImageResource(fireBallFrames[frame]);
            frame++;
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
        if (time % 5 == 0) {
            image.setX(image.getX() - 30);
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

        mineCartMotion(); //moves train
    }

    /**
     * Translates minecarts across road.
     */
    public void mineCartMotion() {
        final boolean[] switched = {false};
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (time % 10 == 0) {
                    switched[0] = true;
                }
                if (time % 3 == 0 && switched[0] == true) {
                    image.setX(image.getX() - 30);
                }
                if (image.getX() < -image.getWidth()) {
                    image.setX(row.getWidth());
                    switched[0] = false;
                }
            }
        };
        movementListener.addListener(v);
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
