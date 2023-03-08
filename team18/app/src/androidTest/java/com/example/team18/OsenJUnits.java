package com.example.team18;


import static org.junit.Assert.assertEquals;
import android.content.Intent;
import android.widget.Button;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import org.junit.Test;
import java.util.Random;


public class OsenJUnits {

    @Test
    public void testLeftButton() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button leftButton = activity.findViewById(R.id.leftButton);
            leftButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0] - curr.getBlockSize());
            assertEquals(newPos[1], currPos[1]);
        });
    }


    @Test
    public void testRightButton() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button rightButton = activity.findViewById(R.id.rightButton);
            rightButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0] + curr.getBlockSize());
            assertEquals(newPos[1], currPos[1]);
        });
    }
}


