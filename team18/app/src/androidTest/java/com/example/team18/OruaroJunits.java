package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.Random;

public class OruaroJunits {
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

            int initialScore = curr.getPlayer().getScore();
            int row = findBlockType(GameBlockTypes.RIVER, curr);

            int finalPos = curr.getPosition()[1] / curr.getBlockSize();
            finalPos -= row;
            finalPos++;

            while(finalPos-- > 1) {
                g.moveUp();
            }
            int finalScore = curr.getPlayer().getScore();

            assertEquals(initialScore, finalScore * 2);
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
