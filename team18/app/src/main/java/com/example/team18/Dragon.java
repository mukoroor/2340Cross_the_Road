package com.example.team18;

import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Dragon extends Vehicle{
    private static final int[] dragonFrames = {
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
        frameIndex = r.nextInt(dragonFrames.length);
    }

    @Override
    public void animateFrames(CoupledListeners l) {
        l.addListener(e -> {
            if (time % 2 == 0) {
                image.setImageResource(dragonFrames[frameIndex]);
                frameIndex = (frameIndex + dragonFrames.length + 1) % dragonFrames.length;
            }
        });
    }

    @Override
    public void animateMovement(CoupledListeners l) {
        l.addListener(e -> {
            checkForCollision();
            image.setY(row.getY());
            if (time % 4 == 0) {
                image.setX(image.getX() - 40);
            }

            if (image.getX() < -image.getWidth()) {
                image.setX(row.getWidth());
            }
        });
    }
}