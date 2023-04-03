package com.example.team18;

import static android.content.Intent.getIntent;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Button;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.Random;

public class Sprint4Junits {
    Random r = new Random();

    @Test
    public void savesHighestScore() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
            GameOverScreenActivity.class);
        playIntent.putExtra("finalScore",5);

        // Launch the activity with the intent
        ActivityScenario<GameOverScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> assertEquals(5, activity.getFinalScore()));
    }

    @Test
    public void gameOverOnNoLives() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
            GameScreenActivity.class);
        playIntent.putExtra("lives",1);
        Sprite player = new Sprite(3, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            Game currGame = activity.getGame();
            Sprite currGamePlayer = currGame.getPlayer();
            currGamePlayer.setLives(0);
            Instrumentation.ActivityMonitor monitor
                = getInstrumentation().addMonitor((IntentFilter) null, null, false);
            Activity nextActivity
                = getInstrumentation().waitForMonitorWithTimeout(monitor, 500);

            if (nextActivity != null) {
                Intent t = nextActivity.getIntent();
                int latestScore = t.getIntExtra("score", -1);
                assertEquals(0, latestScore);
                assertEquals(GameOverScreenActivity.class, nextActivity.getClass());
            }
        });


    }
}
