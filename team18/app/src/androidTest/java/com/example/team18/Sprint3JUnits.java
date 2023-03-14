package com.example.team18;

import android.content.Intent;

import android.widget.Button;


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



@RunWith(AndroidJUnit4.class)

public class Sprint3JUnits {
    @Test
    public void noScoreChangeOnLeft() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Button leftButton = activity.findViewById(R.id.leftButton);
            leftButton.performClick();
            Game game = g.getGame();

            assertEquals(0, game.getScore());
        });
    }



    @Test
    public void noScoreChangeOnRight() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Button rightButton = activity.findViewById(R.id.rightButton);
            rightButton.performClick();
            rightButton.performClick();
            Game game = g.getGame();

            assertEquals(0, game.getScore());
        });
    }

}
