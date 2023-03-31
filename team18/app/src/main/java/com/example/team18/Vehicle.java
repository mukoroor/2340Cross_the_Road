package com.example.team18;

import android.graphics.Rect;
import android.os.CountDownTimer;
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

    /**
     * Constructor for Vehicle Class.
     * @param row road that vehicle will cross
     * @param image vehicle display
     * @param tracks track that minecart would ride on
     * @param type type of vehicle
     */
    public Vehicle(LinearLayout row, ImageView image, ImageView tracks, int type, ImageView playerImage) {
        this.row = row;
        this.image = image;
        this.tracks = tracks;
        this.playerImage = playerImage;

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
        Random rand = new Random();
        int delay = rand.nextInt(9) + 1;

        //delays the start of the fireball animation
        new CountDownTimer(delay * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                image.setY(row.getY());
                image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getHeight() * 1.5), row.getHeight()));

                image.setX(- image.getWidth());
                image.setRotation(180);

                image.setVisibility(View.VISIBLE);
                fireballFrames(); //switches frames of fireball
                fireballMotion(); //moves fireball
            }
        }.start();

    }

    /**
     * Flips through fireball animation.
     */
    public void fireballFrames() {
        final int[] i = {0};
        final int[] fireBallFrames = {
                R.drawable.fball_0,
                R.drawable.fball_1,
                R.drawable.fball_2,
                R.drawable.fball_3,
                R.drawable.fball_4,
                R.drawable.fball_5,
                R.drawable.fball_6,
                R.drawable.fball_7
        };
        int speed = 250;
        new CountDownTimer(speed * fireBallFrames.length, speed) {
            public void onTick(long millisUntilFinished) {
                //changes fireball images
                image.setImageResource(fireBallFrames[i[0]]);
                i[0]++;
            }

            public void onFinish() {
                fireballFrames();
            }
        }.start();
    }

    /**
     * Translates fireball across road.
     */
    public void fireballMotion() {
        int sections = row.getWidth() / 20;
        int screenTime = 2000;
        new CountDownTimer(screenTime + 1000, screenTime / 20) {
            public void onTick(long millisUntilFinished) {
                image.setX(image.getX() + sections);
                checkForCollision();
            }
            public void onFinish() {
                image.setX(- image.getWidth());
                fireballMotion();
            }
        }.start();
    }

    /**
     * Animates Dragon.
     */
    public void animateDragon() {
        Random rand = new Random();
        int delay = rand.nextInt(9) + 1;

        //delays the start of the dragon animation
        new CountDownTimer(delay * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                image.setY(row.getY() - 50);
                image.setX(row.getWidth());
                image.setLayoutParams(new FrameLayout.LayoutParams(row.getHeight() * 2, row.getHeight() + 50));
                image.setVisibility(View.VISIBLE);
                dragonFrames(); //switches frames of dragon
                dragonMotion(); //moves dragon
            }
        }.start();
    }

    /**
     * Flips through dragon animation.
     */
    public void dragonFrames() {
        final int[] i = {0};
        final int[] fireBallFrames = {
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
        int speed = 100;
        new CountDownTimer(speed * fireBallFrames.length, speed) {
            public void onTick(long millisUntilFinished) {
                //changes dragon images
                image.setImageResource(fireBallFrames[i[0]]);
                i[0]++;
            }

            public void onFinish() {
                dragonFrames();
            }
        }.start();
    }

    /**
     * Translates dragon across road.
     */
    public void dragonMotion() {
        int sections = row.getWidth() / 20;
        int screenTime = 8000;
        new CountDownTimer(screenTime + 2000, screenTime / 20) {
            public void onTick(long millisUntilFinished) {
                image.setX(image.getX() - sections);
                checkForCollision();
            }
            public void onFinish() {
                image.setX(row.getWidth());
                dragonMotion();
            }
        }.start();
    }

    /**
     * Animates Minecarts.
     */
    public void animateMineCarts() {
        Random rand = new Random();
        int delay = rand.nextInt(9) + 1;

        //delays the start of train takeoff
        new CountDownTimer(delay * 1000, 200) {
            public void onTick(long millisUntilFinished) {
                tracks.setY(row.getY() - 25);
                tracks.setX(0);
                tracks.setImageResource(R.drawable.traintracks);
                tracks.setLayoutParams(new FrameLayout.LayoutParams(row.getWidth(), row.getHeight()));
                tracks.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                image.setY(row.getY() - 10);
                image.setX(row.getWidth());
                image.setImageResource(R.drawable.minecarts);
                image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth() * 1.5), row.getHeight() - 20));
                image.setVisibility(View.VISIBLE);
                mineCartMotion(); //moves train
            }
        }.start();
    }

    /**
     * Translates minecarts across road.
     */
    public void mineCartMotion() {
        int sections = (int) (row.getWidth() * 2) / 20;
        int screenTime = 5000;
        new CountDownTimer(screenTime + 1000, screenTime / 20) {
            public void onTick(long millisUntilFinished) {
                image.setX(image.getX() - sections);
                checkForCollision();
            }

            public void onFinish() {
                image.setX(row.getWidth());
                //delays next train takeoff
                new CountDownTimer(5000, 100) {
                    public void onTick(long millisUntilFinished) {
                    }

                    public void onFinish() {
                        mineCartMotion();
                    }
                }.start();
            }
        }.start();
    }

    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getGlobalVisibleRect(rect1);

        Rect rect2 = new Rect();
        playerImage.getGlobalVisibleRect(rect2);

        if (Rect.intersects(rect1, rect2)) {
           System.out.println("Game Over!!!");
        }
    }
}
