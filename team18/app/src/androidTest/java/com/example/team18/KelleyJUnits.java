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
    public void leftBoundary() {
        activityScenario =
        activityScenario.onActivity(activity -> {
            ViewInteraction leftButton = onView(withId(R.id.leftButton));
            for (int i = 0; i < 10; i++) {
                leftButton.perform(click());
            }

            ImageView player = (ImageView) withId(R.id.player);
            int playerX = (int) player.getX();

            assertTrue(playerX >= 0);
        });
    }

    @Test
    public void rightBoundary() {
            activityScenario.onActivity(activity -> {
            ViewInteraction rightButton = onView(withId(R.id.rightButton));
            for (int i = 0; i < 10; i++) {
                rightButton.perform(click());
            }

            ImageView player = (ImageView) withId(R.id.player);
            int playerX = (int) player.getX();

            FrameLayout mainFrame = (FrameLayout) withId(R.id.mainFrame);
            int screenSize = (int) player.getWidth();

            assertTrue(playerX <= screenSize);
        });
    }
}
