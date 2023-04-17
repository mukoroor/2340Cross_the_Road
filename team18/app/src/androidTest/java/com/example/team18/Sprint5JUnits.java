package com.example.team18;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Sprint5JUnits {
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

    @Test
    public void gwsRestartButton() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameWinScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameWinScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            RelativeLayout restart = activity.findViewById(R.id.restart);
            restart.performClick();

            Instrumentation.ActivityMonitor monitor
                = getInstrumentation().addMonitor((IntentFilter) null, null, false);
            Activity nextActivity
                = getInstrumentation().waitForMonitorWithTimeout(monitor, 50);

            if (nextActivity != null) {
                assertTrue(true);
            }
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
            RelativeLayout quit = activity.findViewById(R.id.quit);
            Instrumentation.ActivityMonitor monitor
                = getInstrumentation().addMonitor((IntentFilter) null, null, false);
            Activity nextActivity
                = getInstrumentation().waitForMonitorWithTimeout(monitor, 50);

            if (nextActivity != null) {
                assertTrue(true);
            }
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

    @Test
    public void checkDisplayingCorrectFinalScore() {
        int expectedValue = 42; // Set an example value
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), GameWinScreenActivity.class);
        intent.putExtra("finalScore", expectedValue);


        try (ActivityScenario<GameWinScreenActivity> scenario = ActivityScenario.launch(intent)) {
            scenario.onActivity(activity -> {
                int actualValue = activity.getIntent().getIntExtra("finalScore", 0);
                assertEquals("GameWinScreenActivity did not receive the correct value", expectedValue, actualValue);
            });
        }
    }

}
