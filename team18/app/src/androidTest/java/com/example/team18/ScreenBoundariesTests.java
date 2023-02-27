package com.example.team18;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.EditText;



import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.team18.LoginActivity;

import java.util.Random;

@RunWith(AndroidJUnit4.class)

public class ScreenBoundariesTests {
    private EditText username;
    private Button submit;

    private ActivityScenario<LoginActivity> scenario;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup() {
        scenario = activityScenarioRule.getScenario();
        username = new EditText(ApplicationProvider.getApplicationContext());
        username.setId(R.id.editTextLoginUserName);
        submit = new Button(ApplicationProvider.getApplicationContext());
        submit.setId(R.id.submit);
    }


    @Test
    public void testUpperBoundary() {

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
            curr.changePosition(0, -200);

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
    public void testLowerBoundary() {

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
            curr.changePosition(0, 200);

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
    @After
    public void teardown() {
        scenario.close();
    }
}

