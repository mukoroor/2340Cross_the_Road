package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Sprint5Junits {
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
    public void checkForDragonObject() {
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
                if (vehicle instanceof Dragon) {
                    found = true;
                }
            }

            assertTrue(found);
        });
    }
}
