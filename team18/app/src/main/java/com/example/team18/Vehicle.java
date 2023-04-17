package com.example.team18;

public abstract class Vehicle {
    protected static Clock c;
    /**
     * Constructor for Abstract Vehicle Class.
     **/
    public Vehicle() {
    }
    public abstract void launch();

    public abstract void animateFrames();

    public abstract void animateMovement();

    public abstract void checkForCollision();
}