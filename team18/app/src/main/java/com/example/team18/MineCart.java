package com.example.team18;

import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MineCart extends Vehicle{

    private Boolean switched = false;

    private final ImageView track;

    public MineCart(LinearLayout row, ImageView image, ImageView track, int gameBlockSize) {
        super(row, image);
        this.track = track;
        image.setLayoutParams(new FrameLayout.LayoutParams((int) (9 * gameBlockSize * 1.5),
                gameBlockSize - 20));
        image.setImageResource(R.drawable.minecarts);
        image.setX(9 * gameBlockSize);
        track.setLayoutParams(new FrameLayout.LayoutParams(9 * gameBlockSize, gameBlockSize));
        track.setImageResource(R.drawable.traintracks);
        track.setX(0);
    }

    @Override
    public void animateFrames(CoupledListeners l) {
    }

    @Override
    public void animateMovement(CoupledListeners l) {
        l.addListener(e -> {
            checkForCollision();
            track.setY(row.getY() - 25);
            image.setY(row.getY() - 10);
            if (time % 10 == 0) {
                switched = true;
            }
            if (time % 2 == 0 && switched) {
                image.setX(image.getX() - 30);
            }
            if (image.getX() < -image.getWidth()) {
                image.setX(row.getWidth());
                switched = false;
            }
        });
    }
}