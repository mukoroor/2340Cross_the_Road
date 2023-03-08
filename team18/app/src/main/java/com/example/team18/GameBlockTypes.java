package com.example.team18;

public enum GameBlockTypes {
    ROAD(2),
    RIVER(0),
    SAFE(0),
    GOAL(4),
    LOG(3);

    final int travelGain;

    GameBlockTypes (int travelGain) {
        this.travelGain = travelGain;
    }
}
