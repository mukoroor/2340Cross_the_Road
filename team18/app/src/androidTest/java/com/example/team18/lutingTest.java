package com.example.team18;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

public class lutingTest {
    @Test
    public void resetPositionWhenOnRoad() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());


        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            int initialLives = curr.getPlayer().getLives();
            int row = findBlockType(GameBlockTypes.ROAD, curr);

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            while(finalPos-- > 1) {
                g.moveUp();
            }

            int finalLives = curr.getPlayer().getLives();

            assertEquals(initialLives, finalLives);
        });
    }

    @Test
    public void resetPositionWhenOnLog() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());


        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.setBlockSize(160);
            GameBlock[][] block = curr.getGameBlockArray();
            curr.changePosition(-4, 0);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int initialLives = curr.getPlayer().getLives();


            boolean needBreak = false;
            int iniScore = curr.getScore();
            for(int q  = 14; q >= 0 ; q--) {

                if (block[q][0].blockType == GameBlockTypes.RIVER) {

                    for (int j = 0; j < block.length-1; j++) {
                        if (block[q][j].blockType == GameBlockTypes.LOG) {
                            curr.changePosition(j, q - 14);
                            if(block[q][j+1].blockType == GameBlockTypes.LOG) {
                                g.moveRight();
                            }
//                            assertEquals(5, curr.getPosition()[1] / 160);
                            needBreak = true;
                            break;
                        }
                    }
                }
                if (needBreak) {
                    break;
                }
            }





            int finalLives = curr.getPlayer().getLives();

            assertEquals(initialLives, finalLives);
        });
    }

    public int findBlockType(GameBlockTypes g, Game curr) {
        GameBlock[][] gameBlockArr = curr.getGameBlockArray();

        int startingY = 13;

        while (gameBlockArr[startingY][4].blockType != g) {
            startingY--;
        }
        return startingY;
    }
}
