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

    private final ImageView playerImage;

    private int delay = 0;

    final int[] i = {0};
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

    private boolean launched = false;

    public Fireball (LinearLayout row, ImageView image, ImageView playerImage) {
        super();
        this.row = row;
        this.image = image;
        this.playerImage = playerImage;

        Random rand = new Random();
        delay = rand.nextInt(150) + 1;

        launch();
        animateFrames();
        animateMovement();
    }

    public void launch() {
        image.setVisibility(View.INVISIBLE);
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (GameScreenActivity.getTime() > delay && !launched) {
                    launched = true;

                    image.setY(row.getY());
                    image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getHeight() * 1.5),
                            row.getHeight()));
                    image.setX(-image.getWidth());
                    image.setRotation(180);
                    image.setVisibility(View.VISIBLE);
                }
            }
        };
        l.addListener(v);
    }

    @Override
    public void animateFrames() {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (launched) {
                    if (GameScreenActivity.getTime() % 5 == 0) {
                        image.setImageResource(fireBallFrames[i[0]]);
                        i[0]++;

                        if (i[0] >= 8) {
                            i[0] = 0;
                        }
                    }
                }
            }
        };
        l.addListener(v);
    }

    @Override
    public void animateMovement() {
            View.OnClickListener v = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (launched) {
                        image.setVisibility(View.VISIBLE);
                        checkForCollision();
                        if (GameScreenActivity.getTime() % 2 == 0) {
                            image.setX(image.getX() + 50);
                        }

                        if (image.getX() > row.getWidth()) {
                            image.setX(-image.getWidth());
                        }
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

        if (Rect.intersects(rect1, rect2) && launched) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }
}
