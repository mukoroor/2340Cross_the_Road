package com.example.team18;

/**
 * Structural Class.
 * Stores Global variables for game.
 */
public class Game {
    private final Sprite player;

    protected boolean playerOnLog = false;

    private Object[] currBlock;

    private int[] playerPosition;

    private static final GameBlock[][] GAME_BLOCK_ARRAY = new GameBlock[16][9];
    private int blockSize;
    private int maxHeight;
    private int score;

    /**
     * Constructor for new Game object.
     *
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
     *
     * @param deltaX change in position in X-axis.
     * @param deltaY change in position in Y-axis.
     */
    public void changePosition(int deltaX, int deltaY) {
        playerPosition[0] += deltaX * blockSize;
        playerPosition[1] += deltaY * blockSize;
        currBlock[1] = (Integer) currBlock[1] + deltaX;
        currBlock[2] = (Integer) currBlock[2] + deltaY;
        currBlock[0] = GAME_BLOCK_ARRAY[(Integer) currBlock[2]][(Integer) currBlock[1]];
        playerOnLog = ((GameBlock) currBlock[0]).blockType == GameBlockTypes.LOG;
    }


    public int[] getPosition() {
        return playerPosition;
    }

    /**
     * Method for changing current game score.
     *
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
     *
     * @return the current score of the game.
     */
    public int getScore() {
        return score;
    }

    /**
     * Method for getting the Sprite object of the player.
     *
     * @return The sprite object corresponding to the current game.
     */
    public Sprite getPlayer() {
        return player;
    }

    /**
     * Method for getting the size of grid block.
     *
     * @return Uniform block size of grid.
     */
    public int getBlockSize() {
        return blockSize;
    }

    /**
     * Method for setting block-size
     *
     * @param newBlockSize the new value for block-size
     */
    public void setBlockSize(int newBlockSize) {
        blockSize = newBlockSize;
        playerPosition = new int[]{4 * blockSize, 14 * blockSize};
        currBlock = new Object[]{GAME_BLOCK_ARRAY[14][4], 4, 14};
    }

    public void setMaxHeight(int newMaxHeight) {
        this.maxHeight = newMaxHeight;
    }

    /**
     * Method for getting gameBlock array for game.
     *
     * @return gameBlock array
     */
    public static GameBlock[][] getGameBlockArray() {
        return GAME_BLOCK_ARRAY;
    }

    /**
     * gets block player is currently on
     *
     * @return the block which maps to the players position
     */
    public GameBlock getCurrBlock() {
        //System.out.println("here");
        return GAME_BLOCK_ARRAY[playerPosition[1] / blockSize][playerPosition[0] / blockSize];
    }

    /**
     * Method for shifting a row in gameBlockArray with wrap around
     *
     * @param row    the row that is being shifted in gameBlockArray
     * @param deltaX the amount the row is being shifted by (-) is to left (+) is right
     */
    public static void shiftGameRow(int row, int deltaX) {
        int colLength = GAME_BLOCK_ARRAY[0].length;

        deltaX = (((deltaX % colLength) + colLength) % colLength);
        GameBlock[] temp = new GameBlock[colLength];
        int count = 0;

        for (int i = deltaX; i < colLength; i++) {
            temp[i] = GAME_BLOCK_ARRAY[row][count];
            count++;
        }

        for (int i = 0; i < deltaX; i++) {
            temp[i] = GAME_BLOCK_ARRAY[row][9 - deltaX + i];
        }

        GAME_BLOCK_ARRAY[row] = temp;
    }

    public void reset() {
        setScore(score / 2);
        player.setLives(player.getLives() - 1);
        currBlock[1] = 4;
        currBlock[2] = 14;
        currBlock[0] = GAME_BLOCK_ARRAY[(Integer) currBlock[2]][(Integer) currBlock[1]];
        playerPosition = new int[]{4 * blockSize, 14 * blockSize};
    }
}
