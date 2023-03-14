package com.example.team18;



import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Random;

public class JustinSprint3JUnitsTest extends TestCase {
    @Test
    public void testScore(){
        Random r = new Random();
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            assertEquals(curr.getScore(), 0);

        });

    }

    public void testNoScoreGoingBackwards(){
        Random r = new Random();
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.changePosition(0, -1);
            g.updatePlayerScreenData();

            int[] currPosDown = new int[2];
            currPosDown[0] = curr.getPosition()[0];
            currPosDown[1] = curr.getPosition()[1];

            Button downButton = activity.findViewById(R.id.downButton);
            downButton.performClick();
            int currentScore = curr.getScore();

            int[] newPosDown = curr.getPosition();


            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button upButton = activity.findViewById(R.id.upButton);
            upButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(currentScore,curr.getScore());

        });

    }






    public void setUp() throws Exception {
        super.setUp();



    }

    public void tearDown() throws Exception {
    }
}