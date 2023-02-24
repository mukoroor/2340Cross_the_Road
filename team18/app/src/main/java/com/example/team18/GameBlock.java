package com.example.team18;

import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashSet;

public class GameBlock {
    protected static GameBlock[][] gameBlockArray = new GameBlock[16][9];
    protected static int[] blockOptions = new int[]
            {R.drawable.road0, R.drawable.lava_block, R.drawable.rock_block, R.drawable.road1, R.drawable.log_block};

    protected int row;
    protected int column;

    protected ImageView gridBlock;


    public GameBlock(int row, int column, ImageView gridBlock) {
        this.row = row;
        this.column = column;
        this.gridBlock = gridBlock;
        gameBlockArray[row][column] = this;
    }

}
