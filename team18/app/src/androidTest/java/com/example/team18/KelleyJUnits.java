package com.example.team18;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class KelleyJUnits {
    private ActivityScenario<GameScreenActivity> activityScenario;

    @Before
    public void setUp() throws Exception {
        activityScenario = ActivityScenario.launch(GameScreenActivity.class);
    }

    @After
    public void tearDown() {
        activityScenario.close();
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.example.team18", appContext.getPackageName());
    }

    @Test
    public void testleftBoundary() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.changePosition(-200, 0);

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button upButton = activity.findViewById(R.id.upButton);
            upButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1]);
        });
    }


    @Test
    public void testrightBoundary() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.changePosition(200, 0);

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button upButton = activity.findViewById(R.id.upButton);
            upButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1]);
        });
    }
}
