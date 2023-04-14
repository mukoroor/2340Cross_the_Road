package com.example.team18;

import android.widget.ImageView;

public class GameBlock {
    protected static int[] imageOptions = new int[]
        {R.drawable.road0, R.drawable.lava_block, R.drawable.rock_block,
            R.drawable.road1, R.drawable.log_block, R.drawable.start};

    protected ImageView gridBlock;

    protected GameBlockTypes blockType;

    /**
     * Game block constructor
     * @param gridBlock ImageView representing GameBlock in an activity;
     */
    public GameBlock(ImageView gridBlock) {
     this.gridBlock = gridBlock;
    }

}
