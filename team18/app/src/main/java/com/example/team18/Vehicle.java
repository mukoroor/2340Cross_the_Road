package com.example.team18;

import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class Vehicle {
    protected static CoupledListeners l;
    LinearLayout row;

    ImageView image;

    ImageView tracks;

    ImageView playerImage;

    Game curr;

    public static int time = 0;

    /**
     * Constructor for Vehicle Class.
     * @param row road that vehicle will cross
     * @param image vehicle display
     * @param tracks track that minecart would ride on
     * @param type type of vehicle
     * @param
     * @param
     * @param
     */
    public Vehicle(LinearLayout row, ImageView image, ImageView tracks, int type, ImageView playerImage, Game curr) {
        this.row = row;
        this.image = image;
        this.tracks = tracks;
        this.playerImage = playerImage;
        this.curr = curr;

        this.image.setVisibility(View.INVISIBLE);
        this.tracks.setVisibility(View.INVISIBLE);
        //type = 1 -> fireball
        //type = 2 -> dragon
        //type = 3 -> minecarts
        image.setId(type);
        switch (type) {
        case 1:
            animateFireball();
            break;
        case 2:
            animateDragon();
            break;
        case 3:
            animateMineCarts();
            break;
        default:
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
                image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getHeight() * 1.5),
                        row.getHeight()));

                image.setX(-image.getWidth());
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
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (time % 3 == 0) {
                    image.setX(image.getX() + 30);
                }

                if (image.getX() > row.getWidth()) {
                    image.setX(-image.getWidth());
                }
            }
        };
        l.addListener(v);
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
                image.setLayoutParams(new FrameLayout.LayoutParams(row.getHeight() * 2,
                        row.getHeight() + 50));
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
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (time % 4 == 0) {
                    image.setX(image.getX() - 30);
                }

                if (image.getX() < 0) {
                    image.setX(row.getWidth());
                }
            }
        };
        l.addListener(v);
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
                tracks.setLayoutParams(new FrameLayout.LayoutParams(row.getWidth(),
                        row.getHeight()));
                tracks.setVisibility(View.VISIBLE);
            }

            public void onFinish() {
                image.setY(row.getY() - 10);
                image.setX(row.getWidth());
                image.setImageResource(R.drawable.minecarts);
                image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getWidth() * 1.5),
                        row.getHeight() - 20));
                image.setVisibility(View.VISIBLE);
                mineCartMotion(); //moves train
            }
        }.start();
    }

    /**
     * Translates minecarts across road.
     */
    public void mineCartMotion() {
        View.OnClickListener v = new View.OnClickListener() {
            final boolean[] switched = {false};
            @Override
            public void onClick(View view) {
                checkForCollision();
                if (time % 10 == 0) {
                    switched[0] = true;
                }
                if (time % 2 == 0 && switched[0]) {
                    image.setX(image.getX() - 30);
                }
                if (image.getX() < -image.getWidth()) {
                    image.setX(row.getWidth());
                    switched[0] = false;
                }
            }
        };
        l.addListener(v);
    }

    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getGlobalVisibleRect(rect1);

        Rect rect2 = new Rect();
        playerImage.getGlobalVisibleRect(rect2);

        if (Rect.intersects(rect1, rect2)) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }

    public ImageView getPlayerImage() {
        return playerImage;
    }
}