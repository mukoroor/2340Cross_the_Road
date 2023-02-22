package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;

public class BackgroundGenerator extends AppCompatActivity {

    protected BlockTypes[][] gameBlockArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_generator);

        TableLayout grid = findViewById(R.id.grid);
        Game c = new Game(new Sprite(1,"hablo"), 1, 1440);

        gameBlockArray = new BlockTypes[20][9];


        createGrid(grid, c);
        fillGridWaveFunctionCollapse();
//        fillGridReg();

    }


    public void createGrid(TableLayout gridContainer, Game currentGame) {
        int blockSize = currentGame.getBlockSize();
        for (int row = 0; row < 20; row++) {
            TableRow rowBlock = new TableRow(this);

            TableLayout.LayoutParams params1 = new TableLayout.LayoutParams(blockSize, blockSize * 18);

            rowBlock.setLayoutParams(params1);

            for (int column = 0; column < 9; column++) {
                ImageView gridBlock = new ImageView(this, null);
                GameBlock g = new GameBlock(row, column, gridBlock);

                TableRow.LayoutParams params2 = new TableRow.LayoutParams(blockSize, blockSize);
                gridBlock.setLayoutParams(params2);

                rowBlock.addView(gridBlock);
            }
            gridContainer.addView(rowBlock);
        }

    }

    public void fillGridWaveFunctionCollapse() {
        Random r = new Random();
        dfs(GameBlock.gameBlockArray[0][0], r);
    }

    public void dfs(GameBlock curr, Random r) {
        Integer[] optn = curr.options.toArray(new Integer[curr.options.size()]);
        if (optn.length != 0) {
            int blockType = optn[r.nextInt(optn.length)];
            int[] temp =GameBlock.blockOptions[blockType];
            curr.setGridBlockImg(blockType,  r.nextInt(temp.length));
        }
        if (curr.column < 8 && !GameBlock.gameBlockArray[curr.row][curr.column + 1].options.isEmpty()) {
            dfs(GameBlock.gameBlockArray[curr.row][curr.column + 1], r);
        }
        if (curr.column == 0 && curr.row < 19 && !GameBlock.gameBlockArray[curr.row + 1][curr.column].options.isEmpty()) {
            dfs(GameBlock.gameBlockArray[curr.row + 1][curr.column], r);
        }
    }

    public void fillGridReg() {
        int sameCount = 0;
        int prevImg = 0;
        Random r = new Random();
        int blockOptionsLen = GameBlock.blockOptions.length;

        for (int i = 0; i < 20; i++) {
            GameBlock[] row = GameBlock.gameBlockArray[i];
            int currImg = r.nextInt(blockOptionsLen);

            if (prevImg == currImg) {
                sameCount++;
            }

            if (sameCount == 5) {
                currImg = (currImg + 1) % blockOptionsLen;
                sameCount = 0;
            }
            prevImg = currImg;

            for (GameBlock g: row) {
                g.setGridBlockImg(currImg, 0);
            }
        }
    }

}
