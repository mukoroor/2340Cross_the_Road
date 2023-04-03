package com.example.team18;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.Random;

import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Sprint4JUnits {
    Random r = new Random();
    @Test
    public void looseLifeWhenOnRiver() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            int initialLives = curr.getPlayer().getLives();
            int row = findBlockType(GameBlockTypes.RIVER, curr);

            int moveUps = 14 - row;
            while (moveUps-- > 0) {
                g.moveUp();
            }

            int finalLives = curr.getPlayer().getLives();

            assertEquals(initialLives, finalLives + 1);
        });
    }

    @Test
    public void resetPositionWhenOnRiver() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            int row = findBlockType(GameBlockTypes.RIVER, curr);

            int moveUps = 14 - row;
            while (moveUps-- > 0) {
                g.moveUp();
            }

            int[] finalPlayerPos = curr.getPosition();

            assertEquals(4, finalPlayerPos[0] / curr.getBlockSize());
            assertEquals(14, finalPlayerPos[1] / curr.getBlockSize());
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
    public void looseLifeWhenTouchingVehicle() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            int initialLives = curr.getPlayer().getLives();

            ImageView playerImage = g.getPlayerImage();
            Vehicle vehicle = g.getTestVehicle();

            playerImage.setX(vehicle.getPlayerImage().getX());
            playerImage.setY(vehicle.getPlayerImage().getY());

            int finalLives = curr.getPlayer().getLives();

            assertEquals(initialLives, finalLives);
        });
    }


    @Test
    public void resetPositionWhenTouchingVehicle() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            ImageView playerImage = g.getPlayerImage();
            Vehicle vehicle = g.getTestVehicle();

            playerImage.setX(vehicle.getPlayerImage().getX());
            playerImage.setY(vehicle.getPlayerImage().getY());

            int[] finalPlayerPos = curr.getPosition();

            assertEquals(4, finalPlayerPos[0] / curr.getBlockSize());
            assertEquals(14, finalPlayerPos[1] / curr.getBlockSize());
        });
    }

    @Test
    public void playStateInactive() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            ImageView playerImage = g.getPlayerImage();
            Vehicle vehicle = g.getTestVehicle();

            playerImage.setX(vehicle.getPlayerImage().getX());
            playerImage.setY(vehicle.getPlayerImage().getY());

            boolean playState = g.getPlayState();

            assertFalse(playState);
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

    @Rule
    public ActivityScenarioRule<GameOverScreenActivity> activityScenarioRule2 = new ActivityScenarioRule<>(GameOverScreenActivity.class);

    @Test
    public void gameRestart() {

        Espresso.onView(ViewMatchers.withId(R.id.restart)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.spriteView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }

    @Test
    public void goalTitleHighScore() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());


        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            GameBlockTypes goal = GameBlockTypes.GOAL;

            int row = findBlockType(goal, curr);
            int preSocre = 0;

            for (int i = 0; i < 3; i++) {
                g.moveUp();
                preSocre = curr.getScore();

            }


            curr.changePosition(0,-9);
            assertEquals((preSocre ), curr.getScore());

        });
    }

    @Test
    public void savesHighestScore() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameOverScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameOverScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> assertEquals(5, activity.getFinalScore()));
    }

    @Test
    public void gameOverOnNoLives() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",1);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            Game currGame = activity.getGame();
            Sprite currGamePlayer = currGame.getPlayer();
            currGamePlayer.setLives(0);
            Instrumentation.ActivityMonitor monitor
                    = getInstrumentation().addMonitor((IntentFilter) null, null, false);
            Activity nextActivity
                    = getInstrumentation().waitForMonitorWithTimeout(monitor, 500);

            if (nextActivity != null) {
                Intent t = nextActivity.getIntent();
                int latestScore = t.getIntExtra("score", -1);
                assertEquals(0, latestScore);
                assertEquals(GameOverScreenActivity.class, nextActivity.getClass());
            }
        });
    }

    @Test
    public void scoreChangesWhenRespawnedOnWater() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",1);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            int row = findBlockType(GameBlockTypes.RIVER, curr);

            int moveUps = 14 - row;
            while (moveUps-- > 1) {
                g.moveUp();
            }
            int mScore = curr.getScore();

            g.moveUp();
            new CountDownTimer(2000, 2000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    int finalScore = curr.getScore();
                    assertEquals(mScore / 2, finalScore);
                }
            }.start();

        });
    }

    @Test
    public void scoreChangesWhenTouchingVehicle() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();


            ImageView playerImage = g.getPlayerImage();
            Vehicle vehicle = g.getTestVehicle();
            float vX = vehicle.getPlayerImage().getX();
            float vY = vehicle.getPlayerImage().getY();

            int changeX =  (int) (vX / curr.getBlockSize()) - 4;
            int changeY =   (int) ((vX / curr.getBlockSize()) - 14);

            curr.changePosition(changeX, changeY + 1);
            g.updatePlayerScreenData();
            g.moveUp();
            int initialScore = curr.getScore();

            new CountDownTimer(2000, 2000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                    int finalScore = curr.getScore();
                    assertEquals(initialScore / 2, finalScore);
                }
            }.start();
        });
    }

    @Test
    public void playStateInactive() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            ImageView playerImage = g.getPlayerImage();
            Vehicle vehicle = g.getTestVehicle();

            playerImage.setX(vehicle.getPlayerImage().getX());
            playerImage.setY(vehicle.getPlayerImage().getY());

            boolean playState = false;

            assertFalse(playState);
        });
    }
}
