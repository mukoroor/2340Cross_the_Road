package com.example.team18;

/**
 * Class which describes a sprite.
 */
public class Sprite {
    private final String name;
    private final int spriteIndex;

    protected static int[][] spriteOptions = new int[][]
            {{R.drawable.man1}, {R.drawable.man2}, {R.drawable.mermaid}, {R.drawable.charmeleon}};

    protected static String[] spriteDescriptions = new String[]
            {"MAN1", "MAN2", "MERMAID", "CHARM"};

    /**
     * Constructor for creating a Sprite.
     * @param spriteIndex The index corresponding to the sprites drawable
     *                    representation in the spriteOptions array.
     * @param playerName The name the player has given to the Sprite.
     */
    public Sprite(int spriteIndex, String playerName) {
        this.spriteIndex = spriteIndex;
        this.name = playerName;
    }

    @Override
    public String toString() {
        return spriteIndex + "|" + name;
    }

    /**
     * Method for converting String object to sprite.
     * @param object String representing a sprite.
     * @return the created Sprite.
     */
    public static Sprite parseString(String object) {
        String[] tokens = object.split("[|]");
        int ind = Integer.parseInt(tokens[0]);
        String name = tokens[1];
        return new Sprite(ind, name);
    }

    /**
     * Getter for sprite index.
     * @return sprite index
     */
    public int getSpriteIndex() {
        return spriteIndex;
    }

    /**
     * Getter for sprite name.
     * @return sprite name
     */
    public String getName() {
        return name;
    }
}