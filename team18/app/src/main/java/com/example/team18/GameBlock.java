package com.example.team18;

import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashSet;

public class GameBlock {
    protected static GameBlock[][] gameBlockArray = new GameBlock[20][9];
    protected static int[][] blockOptions = new int[][] {
            {R.drawable.lava_block},
            {R.drawable.road0, R.drawable.road1}
    };

    protected static int[] neighborRules = new int[] {-1, 0};

    protected HashSet<Integer> options = new HashSet<>();

    protected int imageNum = 0;
    protected int row;
    protected int column;

    protected ImageView gridBlock;


    public GameBlock(int row, int column, ImageView gridBlock) {
        options.addAll(Arrays.asList(0, 1));
        this.row = row;
        this.column = column;
        this.gridBlock = gridBlock;
        gameBlockArray[row][column] = this;
    }

    public void setGridBlockImg(int blockType, int ind) {
        imageNum = ind;
        if (column != 0 && ind == gameBlockArray[row][column - 1].imageNum) {
          imageNum = (ind + blockOptions[blockType].length) % blockOptions[blockType].length;
        }
        gridBlock.setImageResource(blockOptions[blockType][imageNum]);
        options.clear();
        if (column < 8) {
            if (blockType != 0) {
                gameBlockArray[row][column + 1].options.remove(0);
            } else {
                gameBlockArray[row][column + 1].options.clear();
                gameBlockArray[row][column + 1].options.add(0);}
        }
    }
}
