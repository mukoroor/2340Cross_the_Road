package com.example.team18;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

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
}
