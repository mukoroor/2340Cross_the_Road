package com.example.team18;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;

public class Sprint5Junits {
    @Test
    public void logsHaveDifferentSizes() {
        Game g = new Game(new Sprite(1,"TEST"),1);
//        g.createGrid(new Context());
        ArrayList<Integer> result = logLengthFinder(g.getGameBlockArray());
    }

    private ArrayList<Integer> logLengthFinder(GameBlock[][] arr) {
        ArrayList<Integer> logLenghts = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0].blockType == GameBlockTypes.RIVER ||
                    arr[i][0].blockType == GameBlockTypes.LOG) {
                int count = 0;
                for (int k = 0; k < arr[i].length; k++) {
                    if (arr[i][k].blockType == GameBlockTypes.LOG) {
                        count++;
                    }
                }
                logLenghts.add(count);
            }
        }
        return logLenghts;
    }


}
