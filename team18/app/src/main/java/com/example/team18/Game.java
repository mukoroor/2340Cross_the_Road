package com.example.team18;

import android.content.Context;
import android.widget.ImageView;

import java.util.Random;

/**
 * Structural Class.
 * Stores Global variables for game.
 */
public class Game {
    private final Sprite player;

    private int lives;
    private int[] playerPosition;
    private final Random r = new Random();
    private static GameBlock[][] gameBlockArray;
    private int blockSize;
    private int maxHeight;
    private int score;

    /**
     * Constructor for new Game object.
     * @param player The Sprite the player has chosen.
     * @param lives
     */
    public Game(Sprite player, int lives) {
        gameBlockArray = new GameBlock[16 * (lives + 1) / 2][9];
        this.player = player;
        this.lives = lives;
        this.maxHeight = gameBlockArray.length - 1;
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

    public void resetPosition() {
        playerPosition = new int[] {4 * blockSize, (gameBlockArray.length - 1) * blockSize};
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
    /**
     * Method for changing current game score.
     * @param newScore the newScore the player has.
     */
    public void setScore(int newScore) {
        if (playerPosition[1] / blockSize < maxHeight) {
            score = newScore;
            maxHeight = playerPosition[1] / blockSize;
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
        playerPosition = new int[] {4 * blockSize, (gameBlockArray.length - 1) * blockSize};
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

    public void createGrid() {
        for (int i = 0; i < gameBlockArray.length; i++) {
            for (int k = 0; k < gameBlockArray[i].length; k++) {
                gameBlockArray[i][k] = new GameBlock();
            }
        }
    }

    /**
     * Method for assigning row types to the created grid in the game
     * @return int array mapping integers to row types
     */
    public int[] populateGrid() {
        /*
        Start tile => 5
        log tile => 4
        Goal tile => 3
        Safe tile => 2
        River tile => 1
        Road tile => 0
         */
        int[] rowMap = new int[gameBlockArray.length];

        rowMap[0] = 3;
        rowMap[1] = 3;
        rowMap[gameBlockArray.length - 2] = 5;
        rowMap[gameBlockArray.length - 1] = 5;
        rowMap[8] = 2;
        if (lives > 1) {
            rowMap[16] = 2;
            rowMap[24] = 2;
        }
        if (lives > 3) {
            rowMap[32] = 2;
            rowMap[38] = 2;
        }

        int type = r.nextInt(2);
        for (int i = 2; i < gameBlockArray.length - 1; i++) {
            if (rowMap[i] == 0) {
                rowMap[i] = type;
            } else {
                if (type == 1) {
                    type = 0;
                } else {
                    type = 1;
                }
            }
        }

//        int[] imageOptions = GameBlock.imageOptions;
        GameBlockTypes[] gbt = GameBlockTypes.values();
        for (int i = 0; i < rowMap.length; i++) {
            GameBlock[] row = gameBlockArray[i];
            for (GameBlock g:row
            ) {
                g.blockType = gbt[rowMap[i]];
                g.imageIndex = rowMap[i];
//                g.gridBlock.setImageResource(imageOptions[rowMap[i]]);
            }
        }

        int begin = r.nextInt(9);
        for (int i = 0; i < rowMap.length; i++) {
            int len = r.nextInt(3) + 1;
            if (rowMap[i] == 1) {
                GameBlock[] riverRow = gameBlockArray[i];
                riverRow[begin].blockType = gbt[4];
                riverRow[begin].imageIndex = 4;
//                riverRow[begin].gridBlock.setImageResource(imageOptions[4]);
                begin = (begin + 1) % riverRow.length;

                if (len > 1) {
                    riverRow[begin].blockType = gbt[4];
                    riverRow[begin].imageIndex = 4;
//                    riverRow[begin].gridBlock.setImageResource(imageOptions[4]);
                    begin = (begin + 1) % riverRow.length;
                }
                if (len > 2) {
                    riverRow[begin].blockType = gbt[4];
                    riverRow[begin].imageIndex = 4;
//                    riverRow[begin].gridBlock.setImageResource(imageOptions[4]);
                    begin = (begin + 1) % riverRow.length;
                }
                begin = (begin - 1 + riverRow.length) % riverRow.length;
            }
        }
        return rowMap;
    }
}