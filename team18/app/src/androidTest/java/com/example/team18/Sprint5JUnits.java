package com.example.team18;

import static androidx.core.content.ContextCompat.getSystemService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.ActivityManager;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
            ActivityManager activityManager = (ActivityManager) activity.getSystemService(activity.getApplicationContext().ACTIVITY_SERVICE);
            String currentActivity = activityManager.getAppTasks().get(0).getTaskInfo().topActivity.getClassName();
            String[] c = currentActivity.split("\\.");
            assertEquals(c[c.length-1], "SpriteSelector");
        });
    }

    @Test
    public void gwsQuitButton() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameWinScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameWinScreenActivity> scenario = ActivityScenario.launch(playIntent);
        GameWinScreenActivity[] a = new GameWinScreenActivity[1];
        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            RelativeLayout quit = activity.findViewById(R.id.quit);
            quit.performClick();
            a[0] = activity;
        });
        assertTrue(a[0].isDestroyed());
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
                    break;
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
                    break;
                }
            }

            assertTrue(found);
        });
    }

    @Test
    public void checkForMinecarts() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("Justin",5);

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;

            ArrayList<Vehicle> vehicles = g.getVehicleList();
            boolean found = false;

            for (Vehicle vehicle : vehicles) {
                if (vehicle instanceof Minecart) {
                    found = true;
                    break;
                }
            }

            assertTrue(found);
        });

    }
    @Test
    public void checkMinecartAttributes() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("Justin", 5);

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;

            // Create a new LinearLayout object with specific properties
            LinearLayout row = new LinearLayout(g.getApplicationContext());
            row.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            row.setLayoutParams(rowParams);

            // Create a new ImageView object with a specific image resource
            ImageView image = new ImageView(g.getApplicationContext());
            image.setImageResource(R.drawable.minecarts);

            // Create a new ImageView object with a specific image resource
            ImageView tracks = new ImageView(g.getApplicationContext());
            tracks.setImageResource(R.drawable.traintracks);


            // Create a new Minecart object
            Minecart minecart = new Minecart(row, image, tracks);


            // Check if the parameters match the parameters passed to the constructor
            assertEquals(row, minecart.getRow());
            assertEquals(image, minecart.getImage());
            assertEquals(tracks, minecart.getTracks());
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


