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
        scenario = ActivityScenario.launch(LoginActivity.class);
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
            (IntentFilter) null, null, false);

        onView(withId(R.id.editTextLoginUserName)).perform(replaceText("hi"));
        onView(withId(R.id.submit)).perform(click());


        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 500);
        if (nextActivity != null) {
            for (int i = 0; i < 50; i++) {
                onView(withId(R.id.upButton)).perform(click());
            }

            GameScreenActivity gsa = (GameScreenActivity) nextActivity;
            int[] pos = gsa.getGame().getPosition();

            assertTrue(pos[1] > 0);
        }

    }

    @After
    public void teardown() {
        scenario.close();
    }
}

