package com.example.team18;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.Random;

public class OruaroUnitTest {


    @Test
    public void testCorrectSpriteIndex() {
        // launch the sending activity and retrieve the scenario
        ActivityScenario<SpriteSelector> scenario1 = ActivityScenario.launch(SpriteSelector.class);

        Random r = new Random();
        int chosen = r.nextInt(16);

        for (int i = 0; i < chosen; i++) {
            onView(withId(R.id.rightButton)).perform(click());
        }

        // verify that the variable sent to the next activity is correct
        onView(withId(R.id.nextButton)).perform(click());
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor((IntentFilter) null, null, false);
        Activity nextActivity = getInstrumentation().waitForMonitorWithTimeout(monitor, 5000);

        // Get a reference to the next activity
        if (nextActivity != null) {
            Intent t = nextActivity.getIntent();
            int index = t.getIntExtra("index", 0);
            assertEquals(chosen % 4, index);
        }
    }



    @Test
    public void testGameBlockMapping() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            int row = r.nextInt(9);
            int column = r.nextInt(16);

            LinearLayout grid = activity.findViewById(R.id.backgroundGrid);
            LinearLayout rowLayout = (LinearLayout) grid.getChildAt(row);
            ImageView block = (ImageView) rowLayout.getChildAt(column);

            GameBlock c = curr.getGameBlockArray()[row][column];
            ImageView compare = c.gridBlock;

            assertEquals(compare, block);
        });
    }
}
