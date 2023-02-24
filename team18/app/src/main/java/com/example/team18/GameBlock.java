package com.example.team18;

import android.widget.ImageView;

import java.util.Arrays;
import java.util.HashSet;

public class GameBlock {
    protected static int[] blockOptions = new int[]
            {R.drawable.road0, R.drawable.lava_block, R.drawable.rock_block, R.drawable.road1, R.drawable.log_block};

    protected ImageView gridBlock;


    public GameBlock(int row, int column, ImageView gridBlock) {
        this.gridBlock = gridBlock;
        setBlockPosition(row, column);
    }

    public void setBlockPosition(int row, int column) {
        Game.gameBlockArray[row][column] = this;
    }

}
