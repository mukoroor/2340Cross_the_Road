package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Sprint5Junits {
    Random r = new Random();
    @Test
    public void logsHaveDifferentSizes() {
        Game g = new Game(new Sprite(1,"TEST"),r.nextInt(3) * 2 + 1);
        g.createGrid();
        g.populateGrid();
        ArrayList<Integer> result = logLengthsFinder(Game.getGameBlockArray());
        Set<Integer> unique = new HashSet<>(result);
        assertTrue(unique.size() > 1);
    }

    private ArrayList<Integer> logLengthsFinder(GameBlock[][] arr) {
        ArrayList<Integer> logLengths = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i][0].blockType == GameBlockTypes.RIVER ||
                    arr[i][0].blockType == GameBlockTypes.LOG) {
                int count = 0;
                for (int k = 0; k < arr[i].length; k++) {
                    if (arr[i][k].blockType == GameBlockTypes.LOG) {
                        count++;
                    }
                }
                logLengths.add(count);
            }
        }
        return logLengths;
    }

}
