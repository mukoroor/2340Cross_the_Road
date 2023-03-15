package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.Random;

public class Sprint3JUnits {
    Random r = new Random();

    @Test
    public void atLeastOneFireball() {
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

    @Test
    public void atLeastOneDragon() {
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
    public void atLeastOneMineCart() {
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

    @Test
    public void noScoreChangeOnLeft() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Button leftButton = activity.findViewById(R.id.leftButton);
            leftButton.performClick();
            Game game = g.getGame();

            assertEquals(0, game.getScore());
        });
    }



    @Test
    public void noScoreChangeOnRight() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Button rightButton = activity.findViewById(R.id.rightButton);
            rightButton.performClick();
            rightButton.performClick();
            Game game = g.getGame();

            assertEquals(0, game.getScore());
        });
    }

    @Test
    public void testNoDecrementBack() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            for (int i = 0; i < r.nextInt(14); i++) {
                curr.changePosition(0, -1);
                g.updatePlayerScreenData();
            }

            int highScore = curr.getScore();

            for (int i = 0; i < r.nextInt(14); i++) {
                curr.changePosition(0, 1);
                g.updatePlayerScreenData();
            }

            assertEquals(highScore, curr.getScore());
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

    @Test
    public void testSafeTilePointGain() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = activity;
            Game curr = g.getGame();
            GameBlockTypes safe = GameBlockTypes.SAFE;

            int row = findBlockType(safe, curr);

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            int pointsBeforeLeavingSafe = 0;
            while(finalPos-- > 0) {
                if (finalPos == 1) {
                    pointsBeforeLeavingSafe = curr.getScore();
                }
                curr.changePosition(0, -1);
                g.updatePlayerScreenData();
            }

            int changeInPoints = curr.getScore() - pointsBeforeLeavingSafe;

            assertEquals(safe.travelGain, changeInPoints);
        });
    }

    @Test
    public void testRoadTilePointGain() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = activity;
            Game curr = g.getGame();
            GameBlockTypes road = GameBlockTypes.ROAD;

            int row = findBlockType(road, curr);

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            int pointsBeforeLeavingSafe = 0;
            while(finalPos-- > 0) {
                if (finalPos == 1) {
                    pointsBeforeLeavingSafe = curr.getScore();
                }
                curr.changePosition(0, -1);
                g.updatePlayerScreenData();
            }

            int changeInPoints = curr.getScore() - pointsBeforeLeavingSafe;

            assertEquals(road.travelGain, changeInPoints);
        });
    }


    @Test
    public void testGoalTilePointGain() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = activity;
            Game curr = g.getGame();
            GameBlockTypes goal = GameBlockTypes.GOAL;

            int row = findBlockType(goal, curr);

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            int pointsBeforeLeavingSafe = 0;
            while(finalPos-- > 0) {
                if (finalPos == 1) {
                    pointsBeforeLeavingSafe = curr.getScore();
                }
                curr.changePosition(0, -1);
                g.updatePlayerScreenData();
            }

            int changeInPoints = curr.getScore() - pointsBeforeLeavingSafe;

            assertEquals(goal.travelGain, changeInPoints);
        });
    }
}
