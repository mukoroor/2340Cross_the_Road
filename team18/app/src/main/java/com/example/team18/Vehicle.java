package com.example.team18;

import android.widget.LinearLayout;
import android.widget.ImageView;
import android.graphics.Rect;
import java.util.Random;

public abstract class Vehicle {
    protected static Random r = new Random();
    protected static ImageView playerImage;

    protected LinearLayout row;
    protected final ImageView image;

    /**
     * Constructor for Abstract Vehicle Class.
     * @param row
     * @param image
     */
    public Vehicle(LinearLayout row, ImageView image) {
        this.row = row;
        this.image = image;
    }

    public abstract void animateFrames(Clock c);

    public abstract void animateMovement(Clock c);

    public void checkForCollision() {
        Rect rect1 = new Rect();
        image.getHitRect(rect1);

        Rect rect2 = new Rect();
        playerImage.getHitRect(rect2);

        if (Rect.intersects(rect1, rect2)) {
            GameScreenActivity.setCollidedWithVehicle(true);
        }
    }

    public static void setPlayerImage(ImageView playerImage) {
        Vehicle.playerImage = playerImage;
    }
    public ImageView getVehicleImage() {
        return image;
    }
}