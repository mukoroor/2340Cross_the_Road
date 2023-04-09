package com.example.team18;

import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Fireball extends Vehicle {
    private static final int[] fireBallFrames = {
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
        image.setLayoutParams(new FrameLayout.LayoutParams((int) (gameBlockSize * 1.5),
                gameBlockSize));
        image.setX(-gameBlockSize);
        image.setRotation(180);
        frameIndex = r.nextInt(fireBallFrames.length);
    }

    @Override
    public void animateFrames(CoupledListeners l) {
        l.addListener(e -> {
            if (time % 2 == 0) {
                image.setImageResource(fireBallFrames[frameIndex]);
                frameIndex = (frameIndex + fireBallFrames.length + 1) % fireBallFrames.length;
            }
        });
    }

    @Override
    public void animateMovement(CoupledListeners l) {
        l.addListener(e -> {
            checkForCollision();
            image.setY(row.getY());
            if (time % 3 == 0) {
                image.setX(image.getX() + 20);
            }

            if (image.getX() > row.getWidth()) {
                image.setX(-image.getWidth());
            }
        });
    }
}
