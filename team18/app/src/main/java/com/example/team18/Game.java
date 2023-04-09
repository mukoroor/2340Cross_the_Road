package com.example.team18;

/**
 * Structural Class.
 * Stores Global variables for game.
 */
public class Game {
    private final Sprite player;

    private int[] playerPosition;

    private static final GameBlock[][] gameBlockArray = new GameBlock[32][9];
    private int blockSize;
    private int maxHeight;
    private int score;

    /**
     * Constructor for new Game object.
     * @param player The Sprite the player has chosen.
     */
    public Game(Sprite player) {
        this.player = player;
        this.maxHeight = 0;
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
        if (playerPosition[1] < maxHeight) {
            score = newScore;
            maxHeight = playerPosition[1];
        }
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

    public void setMaxHeight(int newMaxHeight) {
        this.maxHeight = newMaxHeight;
    }

    /**
     * Method for getting gameBlock array for game.
     * @return gameBlock array
     */
    public static GameBlock[][] getGameBlockArray() {
        return gameBlockArray;
    }

    /**
     * gets block player is currently on
     * @return the block which maps to the players position
     */
    public GameBlock getCurrBlock() {
//        System.out.println("here");
        return gameBlockArray[playerPosition[1] / blockSize][playerPosition[0] / blockSize];
    }
    /**
     * Method for shifting a row in gameBlockArray with wrap around
     * @param row the row that is being shifted in gameBlockArray
     * @param deltaX the amount the row is being shifted by (-) is to left (+) is right
     */
    public static void shiftGameRow(int row, int deltaX) {
        int colLength = gameBlockArray[0].length;

        GameBlock temp;

        if (deltaX > 0) {
            temp = gameBlockArray[row][colLength - 1];
            for (int i = colLength - 1; i > 0; i--) {
                gameBlockArray[row][i] = gameBlockArray[row][i - 1];
            }
            gameBlockArray[row][0] = temp;
        } else if (deltaX < 0) {
            temp = gameBlockArray[row][0];
            for (int i = 0; i < colLength - 1; i++) {
                gameBlockArray[row][i] = gameBlockArray[row][i + 1];
            }
            gameBlockArray[row][colLength - 1] = temp;
        }
    }

    public void reset() {
        setScore(score / 2);
        player.setLives(player.getLives() - 1);
        playerPosition = new int[] {4 * blockSize, 14 * blockSize};
    }
}
