package com.example.team18;

/**
 * Class which describes a sprite.
 */
public class Sprite {
    private String name;

    protected static int[][] spriteOptions = new int[][]
        {{R.drawable.man1}, {R.drawable.man2}, {R.drawable.mermaid}, {R.drawable.charmeleon}};

    protected static String[] spriteDescriptions = new String[]
        {"MAN1", "MAN2", "MERMAID", "CHARMELEON"};
    private int[] selectedSprite;
    private int lives = 5;

    /**
     * Constructor for creating a Sprite.
     * @param spriteIndex The index corresponding to the sprites drawable
     *                    representation in the spriteOptions array.
     * @param playerName The name the player has given to the Sprite.
     */
    public Sprite(int spriteIndex, String playerName) {
        selectedSprite = spriteOptions[spriteIndex];
        name = playerName;
    }

    /**
     * Method for changing the lives of a Sprite.
     * @param newLives the new amount of lives the Sprite has.
     */
    public void setLives(int newLives) {
        lives = newLives;
    }

    /**
     * Method for getting the number of lives a Sprite has.
     * @return The number of Sprite lives.
     */
    public int getLives() {
        return lives;
    }
}
