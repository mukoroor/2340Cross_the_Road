package com.example.team18;

/**
 * Structural Class.
 * Stores Global variables for game.
 */
public class Game {
    private Sprite player;

    private int[] playerPosition;

    private int blockSize;
    private int score;
    private int difficulty;

    /**
     * Constructor for new Game object.
     * @param player The Sprite the player has chosen.
     * @param difficulty The difficulty level of the game.
     * @param deviceWidth The width of the device the game is being played on.
     */
    public Game(Sprite player, int difficulty, int deviceWidth) {
        this.player = player;
        this.blockSize = deviceWidth / 9;
        this.playerPosition = new int[] {4 * blockSize, blockSize};
        this.score = 0;
        this.difficulty = difficulty;
    }

    /**
     * Calculates movements of Sprite.
     * Moves Sprite position by deltaX * block-size and deltaY * block-size
     * in their respective dimensions.
     * @param deltaX change in position in X-axis.
     * @param deltaY change in position in Y-axis.
     */
    public void changePosition(int deltaX, int deltaY) {
        playerPosition[0] += deltaX * blockSize;
        playerPosition[1] += deltaY * blockSize;
    }

    /**
     * Method for changing current game score.
     * @param newScore the newScore the player has.
     */
    public void setScore(int newScore) {
        score = newScore;
    }

    /**
     * Method for getting the current game score.
     * @return the current score of the game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Method for getting the Sprite object of the player.
     * @return The sprite object corresponding to the current game.
     */
    public Sprite getPlayer() {
        return player;
    }

    /**
     * Method for getting the size of grid block.
     * @return Uniform block size of grid.
     */
    public int getBlockSize() {return blockSize;}
}
