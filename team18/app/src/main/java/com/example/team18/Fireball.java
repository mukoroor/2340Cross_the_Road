package com.example.team18;

import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public class Fireball extends Vehicle {
    private LinearLayout row;
    private ImageView playerImage;
    private Game curr;

    private Integer frameIndex = 0;
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

    public Fireball(LinearLayout row, ImageView image, ImageView playerImage, Game curr) {
        super();
        this.row = row;
        this.image = image;
        this.playerImage = playerImage;
        this.curr = curr;//do we need curr

        Random rand = new Random();
        int delay = rand.nextInt(9) + 1;
    }

    public void launch() {
        image.setVisibility(View.VISIBLE);
        image.setLayoutParams(new FrameLayout.LayoutParams((int) (row.getHeight() * 1.5),
                row.getHeight()));
        image.setY(row.getY());
        image.setX(60);
        System.out.println("added");
//        image.setRotation(180);
    }

    @Override
    public void animateFrames(CoupledListeners l) {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time % 2 == 0) {
                    image.setImageResource(fireBallFrames[frameIndex]);
                    frameIndex = (frameIndex + fireBallFrames.length) % fireBallFrames.length;
                }
            }
        };
        l.addListener(v);
    }

    @Override
    public void animateMovement(CoupledListeners l) {
        View.OnClickListener v = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                checkForCollision(playerImage);
                if (time % 3 == 0) {
                    image.setX(image.getX() + 100);
                }

                if (image.getX() > row.getWidth()) {
                    image.setX(-image.getWidth());
                }
            }
        };
        l.addListener(v);
    }
}
