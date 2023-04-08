package com.example.team18;

import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public abstract class Vehicle {
    protected static Random r = new Random();
    protected ImageView image;
    public static int time = 0;

    /**
     * Constructor for Abstract Vehicle Class.
     **/
    public Vehicle(ImageView image) {
        this.image = image;
    }
    abstract public void launch();

    abstract public void animateFrames(CoupledListeners l);

    abstract public void animateMovement(CoupledListeners l);

    public boolean checkForCollision(ImageView object) {
        Rect rect1 = new Rect();
        image.getGlobalVisibleRect(rect1);

        Rect rect2 = new Rect();
        object.getGlobalVisibleRect(rect2);

        if (Rect.intersects(rect1, rect2)) {
            GameScreenActivity.setCollidedWithVehicle(true);
            return true;
        }
        return false;
    }

    public ImageView getVehicleImage() {
        return image;
    }
}