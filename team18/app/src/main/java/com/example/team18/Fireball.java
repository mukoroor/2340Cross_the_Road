package com.example.team18;

import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Fireball extends Vehicle {
    private Integer frameIndex;
    private static final int[] FIREBALL_FRAMES = {
        R.drawable.fball_0,
        R.drawable.fball_1,
        R.drawable.fball_2,
        R.drawable.fball_3,
        R.drawable.fball_4,
        R.drawable.fball_5,
        R.drawable.fball_6,
        R.drawable.fball_7
    };

    public Fireball(LinearLayout row, ImageView image, int gameBlockSize) {
        super(row, image);
        image.setLayoutParams(new FrameLayout.LayoutParams(gameBlockSize,
                gameBlockSize));
        image.setX(-gameBlockSize);
        image.setRotation(180);
        frameIndex = r.nextInt(FIREBALL_FRAMES.length);
    }

    @Override
    public void animateFrames(Clock c) {
        c.addScheduledEvents(e -> {
            if (c.getTime() % 2 == 0) {
                image.setImageResource(FIREBALL_FRAMES[frameIndex]);
                frameIndex = (frameIndex + FIREBALL_FRAMES.length + 1) % FIREBALL_FRAMES.length;
            }
        });
    }

    @Override
    public void animateMovement(Clock c) {
        c.addScheduledEvents(e -> {
            checkForCollision();
            image.setY(row.getY());
            if (c.getTime() % 3 == 0) {
                image.setX(image.getX() + 20);
            }

            if (image.getX() > row.getWidth()) {
                image.setX(-image.getWidth());
            }
        });
    }
}
