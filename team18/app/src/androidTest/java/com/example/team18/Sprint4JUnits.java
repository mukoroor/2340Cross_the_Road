package com.example.team18;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Sprint4JUnits {
    Random r = new Random();

    @Rule
    public ActivityScenarioRule<GameOverScreenActivity> activityScenarioRule2 = new ActivityScenarioRule<>(GameOverScreenActivity.class);

    @Test
    public void gameRestart() {

        Espresso.onView(ViewMatchers.withId(R.id.restart)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.spriteView)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

    }
    public int findBlockType(GameBlockTypes g, Game curr) {
        GameBlock[][] gameBlockArr = curr.getGameBlockArray();

        int startingY = 13;

        while (gameBlockArr[startingY][4].blockType != g) {
            startingY--;
        }
        return startingY;
    }


    @Test
    public void goalTitleHighScore() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());


        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            GameBlockTypes goal = GameBlockTypes.GOAL;

            int row = findBlockType(goal, curr);
            int preSocre = 0;

            for (int i = 0; i < 3; i++) {
                g.moveUp();
                preSocre = curr.getScore();

            }


            curr.changePosition(0,-9);
            assertEquals((preSocre ), curr.getScore());

        });
    }


}
