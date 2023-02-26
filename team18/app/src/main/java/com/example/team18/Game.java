package com.example.team18;

/**
 * Structural Class.
 * Stores Global variables for game.
 */
public class Game {
    private Sprite player;

    private int[] playerPosition;

    private static GameBlock[][] gameBlockArray = new GameBlock[16][9];
    private int blockSize;
    private int score;

    /**
     * Constructor for new Game object.
     * @param player The Sprite the player has chosen.
     */
    public Game(Sprite player) {
        this.player = player;
        this.score = 0;
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


    public int[] getPosition() {
        return playerPosition;
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
    public int getBlockSize() {
        return blockSize;
    }

    /**
     * Method for setting block-size
     * @param newBlockSize the new value for block-size
     */
    public void setBlockSize(int newBlockSize) {
        blockSize = newBlockSize;
        playerPosition = new int[] {4 * blockSize, 14 * blockSize};
    }

    /**
     * Method for getting gameBlock array for game.
     * @return gameBlock array
     */
    public static GameBlock[][] getGameBlockArray() {
        return gameBlockArray;
    }

    /**
     * Method for shifting a row in gameBlockArray with wrap around
     * @param row the row that is being shifted in gameBlockArray
     * @param deltaX the amount the row is being shifted by (-) is to left (+) is right
     */
    public static void shiftGameRow(int row, int deltaX) {
        int colLength = gameBlockArray[0].length;

        deltaX = (((deltaX % colLength) + colLength) % colLength);
        GameBlock[] temp = new GameBlock[colLength];
        int count = 0;

        for (int i = deltaX; i < colLength; i++) {
            temp[i] = gameBlockArray[row][count];
            count++;
        }

        for (int i = 0; i < deltaX; i++) {
            temp[i] = gameBlockArray[row][9 - deltaX + i];
        }

        gameBlockArray[row] = temp;
    }
}
