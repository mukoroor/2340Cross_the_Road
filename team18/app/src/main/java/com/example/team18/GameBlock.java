package com.example.team18;

import android.widget.ImageView;

public class GameBlock {
    protected static int[] imageOptions = new int[]
        {R.drawable.road0, R.drawable.lava_block, R.drawable.rock_block,
            R.drawable.road1, R.drawable.log_block};

    protected ImageView gridBlock;

    protected GameBlockTypes blockType;

    /**
     * Game block constructor
     * @param gridBlock ImageView representing GameBlock in an activity;
     */
    public GameBlock(ImageView gridBlock) {
        this.gridBlock = gridBlock;
    }

    /**
     * sets GameBlock position in 2D array gameBlockArray
     * @param row row index in gameBlockArray
     * @param column column index in gameBlockArray
     */
    public void setBlockPosition(int row, int column) {
        Game.getGameBlockArray()[row][column] = this;
    }

}
