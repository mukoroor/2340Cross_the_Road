package com.example.team18;

import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MineCart extends Vehicle {

    private Boolean switched = false;

    private final ImageView track;

    public MineCart(LinearLayout row, ImageView image, ImageView track, int gameBlockSize) {
        super(row, image);
        this.track = track;
        image.setLayoutParams(new FrameLayout.LayoutParams(9 * gameBlockSize,
                gameBlockSize));
        image.setImageResource(R.drawable.minecarts);
        image.setX(9 * gameBlockSize);
        image.bringToFront();
        track.setLayoutParams(new FrameLayout.LayoutParams(9 * gameBlockSize, gameBlockSize));
        track.setImageResource(R.drawable.traintracks);
        track.setX(0);
    }

    @Override
    public void animateFrames(Clock c) {
    }

    @Override
    public void animateMovement(Clock c) {
        c.addScheduledEvents(e -> {
            checkForCollision();
            track.setY(row.getY() - 25);
            image.setY(row.getY());
            if (c.getTime() % 10 == 0) {
                switched = true;
            }
            if (c.getTime() % 2 == 0 && switched) {
                image.setX(image.getX() - 30);
            }
            if (image.getX() < -image.getWidth()) {
                image.setX(row.getWidth());
                switched = false;
            }
        });
    }
}