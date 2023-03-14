package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

public class Sprint2JUnits {

    /**
     * Checks that at least one fireball is on game screen.
     */
    @Test
    public void AtLeastOneFireball() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity game = (GameScreenActivity) activity;
            assertNotNull(game.findViewById(1));
        });
    }

    /**
     * Checks that at least one dragon is on game screen.
     */
    @Test
    public void AtLeastOneDragon() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity game = (GameScreenActivity) activity;
            assertNotNull(game.findViewById(2));
        });
    }

    /**
     * Checks that at least one minecart is on game screen.
     */
    @Test
    public void AtLeastOneMineCart() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity game = (GameScreenActivity) activity;
            assertNotNull(game.findViewById(3));
        });
    }

    @Test
    public void testLogScore() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
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
            boolean needBreak = false;
            int iniScore = curr.getScore();
            for(int q  = 14; q >= 0 ; q--) {

                if (block[q][0].blockType == GameBlockTypes.RIVER) {

                    for (int j = 0; j < block.length; j++) {
                        if (block[q][j].blockType == GameBlockTypes.LOG) {
                            curr.changePosition(j, q - 14);
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
            int afterScore = curr.getScore();
            int res = afterScore - iniScore;
//            assertEquals(3, res );

//            curr.changePosition(-4, 0);
//            assertEquals(3, curr.getPosition()[1] / 160);
            int score = curr.getCurrBlock().blockType.travelGain;
            assertEquals(3, score);


        });
    }
    @Test
    public void testRiverScore() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
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
//            boolean needBreak = false;
            int iniScore = curr.getScore();
            for(int q  = 14; q >= 0 ; q--) {

                if (block[q][0].blockType == GameBlockTypes.RIVER) {
                    curr.changePosition(0, q - 14);
                    break;
                }

            }
            int afterScore = curr.getScore();
            int res = afterScore - iniScore;
//            assertEquals(3, res );

//            curr.changePosition(-4, 0);
//            assertEquals(3, curr.getPosition()[1] / 160);
            int score = curr.getCurrBlock().blockType.travelGain;
            assertEquals(0, score);


        });
    }


}
