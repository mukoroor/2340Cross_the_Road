package com.example.team18;

public class Sprite {
    private String name;

    protected static int[][] spriteOptions = new int[][] {{R.drawable.__bit_character_1}, {R.drawable.jordle}};

    protected static String[] spriteDescriptions = new String[] {"MAN", "LLLL"};
    private int[] selectedSprite;
    private int lives = 5;

    public Sprite(int spriteIndex, String playerName) {
        selectedSprite = spriteOptions[spriteIndex];
        name = playerName;
    }

    public void setLives(int newLives) {
        lives = newLives;
    }

    public int getLives() {
        return lives;
    }
}
