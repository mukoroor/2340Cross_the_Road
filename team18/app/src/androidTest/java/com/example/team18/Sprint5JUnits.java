package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class Sprint5JUnits {
    Random r = new Random();
    @Test
    public void gwsRestartButton() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
            GameWinScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameWinScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            Button restart = activity.findViewById(R.id.restart);
            restart.performClick();
        });
    }

    @Test
    public void gwsQuitButton() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
            GameWinScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameWinScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            Button quit = activity.findViewById(R.id.quit);
            quit.performClick();
        });
    }

    @Test
    public void checkForFireballObject() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;

            ArrayList<Vehicle> vehicles = g.getVehicleList();
            boolean found = false;

            for (Vehicle vehicle : vehicles) {
                if (vehicle instanceof Fireball) {
                    found = true;
                }
            }

            assertTrue(found);
        });
    }

    @Test
    public void checkFinalScoreForGoalTile() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.setBlockSize(160);
            GameBlock[][] block = curr.getGameBlockArray();

            int initial = curr.getScore();
            curr.changePosition(0, -14);
            g.moveLeft();
            int after = curr.getScore();
            assertEquals(50,after-initial);
        });
    }


}
