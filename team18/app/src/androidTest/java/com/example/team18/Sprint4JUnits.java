package com.example.team18;

<<<<<<< HEAD
=======
import static org.junit.Assert.assertArrayEquals;
>>>>>>> fa3afca26024a5b680fe4e930fcd22d0df5e2bce
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
<<<<<<< HEAD
import android.widget.ImageView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.Random;
=======
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
>>>>>>> fa3afca26024a5b680fe4e930fcd22d0df5e2bce

public class Sprint4JUnits {
    Random r = new Random();

<<<<<<< HEAD
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

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            while(finalPos-- > 1) {
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

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            while(finalPos-- > 1) {
                g.moveUp();
            }
            int[] finalPlayerPos = curr.getPosition();

            assertEquals(4, finalPlayerPos[0] / curr.getBlockSize());
            assertEquals(14, finalPlayerPos[1] / curr.getBlockSize());
        });
    }

=======
    @Rule
    public ActivityScenarioRule<GameOverScreenActivity> activityScenarioRule2 = new ActivityScenarioRule<>(GameOverScreenActivity.class);

    @Test
    public void gameRestart() {

        Espresso.onView(ViewMatchers.withId(R.id.restart)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.spriteView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }
>>>>>>> fa3afca26024a5b680fe4e930fcd22d0df5e2bce
    public int findBlockType(GameBlockTypes g, Game curr) {
        GameBlock[][] gameBlockArr = curr.getGameBlockArray();

        int startingY = 13;

        while (gameBlockArr[startingY][4].blockType != g) {
            startingY--;
        }
        return startingY;
    }

<<<<<<< HEAD
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

            int initialLives = curr.getPlayer().getLives();

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
=======

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


>>>>>>> fa3afca26024a5b680fe4e930fcd22d0df5e2bce
}
