package com.example.team18;

import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Dragon extends Vehicle {
    private Integer frameIndex;
    private static final int[] DRAGON_FRAMES = {
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

    public Dragon(LinearLayout row, ImageView image, int gameBlockSize) {
        super(row, image);
        image.setLayoutParams(new FrameLayout.LayoutParams(gameBlockSize, gameBlockSize));
        image.setX(9 * gameBlockSize);
        frameIndex = r.nextInt(DRAGON_FRAMES.length);
    }

    @Override
    public void animateFrames(Clock c) {
        c.addScheduledEvents(e -> {
            if (c.getTime() % 2 == 0) {
                image.setImageResource(DRAGON_FRAMES[frameIndex]);
                frameIndex = (frameIndex + DRAGON_FRAMES.length + 1) % DRAGON_FRAMES.length;
            }
        });
    }

    @Override
    public void animateMovement(Clock c) {
        c.addScheduledEvents(e -> {
            checkForCollision();
            image.setY(row.getY());
            if (c.getTime() % 4 == 0) {
                image.setX(image.getX() - 40);
            }

            if (image.getX() < -image.getWidth()) {
                image.setX(row.getWidth());
            }
        });
    }
}