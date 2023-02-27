package com.example.team18;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LutingJUnits {
    private Spinner spinner;
    private ActivityScenario<LoginActivity> scenario;


    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() throws Exception {
        scenario = activityScenarioRule.getScenario();
        spinner = new Spinner(ApplicationProvider.getApplicationContext());
        spinner.setId(R.id.spinner);
        String[] options = {"EASY", "MEDIUM", "HARD"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ApplicationProvider.getApplicationContext(), android.R.layout.simple_spinner_item, options);
        spinner.setAdapter(adapter);
    }


    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.team18", appContext.getPackageName());
    }

    @Test
    public void testSpinnerSelection() {
        // simulate selecting an option from the spinner
        spinner.setSelection(1);

        // check that the selected option is the expected one
        assertEquals("MEDIUM", spinner.getSelectedItem().toString());
    }

    @Test
    public void testVariableSentToNextActivity() {
        // launch the sending activity and retrieve the scenario
        ActivityScenario<LoginActivity> scenario = activityScenarioRule.getScenario();

        // perform an action that triggers the intent to start the next activity
        onView(withId(R.id.submit)).perform(click());

        // verify that the variable sent to the next activity is correct
        scenario.onActivity(activity -> {
            Intent intent = activity.getIntent();
            int variableValue = intent.getIntExtra("lives", 5);
            assertEquals(5, variableValue);
        });

    }
}