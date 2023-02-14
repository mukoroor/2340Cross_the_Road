package com.example.team18;

import java.util.Arrays;

public class Sprite {
    private String name;

    protected static int[][] spriteOptions = new int[][] {{R.drawable.__bit_character_1}, {R.drawable.jordle}};

    protected static String[] spriteDescriptions = new String[] {"MAN", "LLLL"};
    private int[] selectedSprite;

    private int spriteIndex;
    private int lives = 5;

    public Sprite(int spriteIndex, String playerName) {
        selectedSprite = spriteOptions[spriteIndex];
        this.spriteIndex = spriteIndex;
        name = playerName;
    }

    public void setLives(int newLives) {
        lives = newLives;
    }

    public int getLives() {
        return lives;
    }

    @Override
    public String toString() {
        return name + "|" + spriteIndex + "|" + lives;
    }

    public Sprite parseString(String object) {
        String[] tokens = object.split("[|]");
        String name = tokens[0];
        int ind = Integer.parseInt(tokens[1]);
        int lives = Integer.parseInt(tokens[2]);
        Sprite parsed = new Sprite(ind, name);
        parsed.setLives(lives);

        return parsed;
    }
}
