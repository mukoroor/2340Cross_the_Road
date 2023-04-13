package com.example.team18;

import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Random;

public abstract class Vehicle {

    protected static CoupledListeners l;

    /**
     * Constructor for Abstract Vehicle Class.
     **/
    public Vehicle() {
    }
    abstract public void launch();

    abstract public void animateFrames();

    abstract public void animateMovement();

    abstract public void checkForCollision();
}