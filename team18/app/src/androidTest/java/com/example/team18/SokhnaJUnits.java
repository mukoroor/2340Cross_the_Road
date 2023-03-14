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

public class SokhnaJUnits {
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
    public void validUserNameTests() {
        scenario = ActivityScenario.launch(LoginActivity.class);
        String name = "valid";
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
            (IntentFilter) null, null, false);

        onView(withId(R.id.editTextLoginUserName)).perform(replaceText(name));
        onView(withId(R.id.submit)).perform(click());

        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 500);
        if (nextActivity != null) {
            Intent intent = nextActivity.getIntent();
            Sprite player = Sprite.parseString(intent.getStringExtra("player"));
            assertEquals("valid", player.getName());
            nextActivity.finish(); // Finish the next activity if needed
        }

    }



    @Test
    public void invalidUsernameTest() {
        scenario = ActivityScenario.launch(LoginActivity.class);
        String[] names_list = {"", "  ", "aaaaaaaaaaaaaa", "valid"};
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(
            (IntentFilter) null, null, false);

        for (String name : names_list) {
            onView(withId(R.id.editTextLoginUserName)).perform(replaceText(name));
            onView(withId(R.id.submit)).perform(click());
        }


        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 500);
        if (nextActivity != null) {
            Intent intent = nextActivity.getIntent();
            Sprite player = Sprite.parseString(intent.getStringExtra("player"));
            assertEquals("valid", player.getName());
            nextActivity.finish(); // Finish the next activity if needed
        }


    }

    @After
    public void teardown() {
        scenario.close();
    }
}
