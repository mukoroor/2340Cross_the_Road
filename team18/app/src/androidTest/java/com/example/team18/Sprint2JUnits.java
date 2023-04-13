package com.example.team18;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Random;

public class Sprint2JUnits {
    @Test
    public void testUpButton() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button upButton = activity.findViewById(R.id.upButton);
            upButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1] - curr.getBlockSize());
        });
    }

    @Test
    public void testDownButton() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.changePosition(0, -1);
            g.updatePlayerScreenData();

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button downButton = activity.findViewById(R.id.downButton);
            downButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1] + curr.getBlockSize());
        });
    }

    @Test
    public void testLeftButton() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button leftButton = activity.findViewById(R.id.leftButton);
            leftButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0] - curr.getBlockSize());
            assertEquals(newPos[1], currPos[1]);
        });
    }


    @Test
    public void testRightButton() {
        Random r = new Random();

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("lives",5);
        Sprite player = new Sprite(r.nextInt(4), "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button rightButton = activity.findViewById(R.id.rightButton);
            rightButton.performClick();

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0] + curr.getBlockSize());
            assertEquals(newPos[1], currPos[1]);
        });
    }

    @Test
    public void testLeftBoundary() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.changePosition(-4, 0);

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button leftButton = activity.findViewById(R.id.leftButton);
            for (int i = 0; i < 200; i++) {
                leftButton.performClick();
            }

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1]);
        });
    }


    @Test
    public void testRightBoundary() {

        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(), GameScreenActivity.class);
        playIntent.putExtra("lives", 5);
        Sprite player = new Sprite(0, "TEST");
        playIntent.putExtra("player", player.toString());

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> activityScenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        activityScenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;
            Game curr = g.getGame();
            curr.changePosition(4, 0);

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button rightButton = activity.findViewById(R.id.rightButton);
            for (int i = 0; i < 200; i++) {
                rightButton.performClick();
            }

            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1]);
        });
    }

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

    private Spinner spinner;
    private ActivityScenario<LoginActivity> scenario;


    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule1 = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp1() throws Exception {
        scenario = activityScenarioRule1.getScenario();
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
        ActivityScenario<LoginActivity> scenario = activityScenarioRule1.getScenario();

        // perform an action that triggers the intent to start the next activity
        onView(withId(R.id.submit)).perform(click());

        // verify that the variable sent to the next activity is correct
        scenario.onActivity(activity -> {
            Intent intent = activity.getIntent();
            int variableValue = intent.getIntExtra("lives", 5);
            assertEquals(5, variableValue);
        });

    }

    private EditText username;
    private Button submit;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule2 = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setup2() {
        scenario = activityScenarioRule2.getScenario();
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
    public void teardown1() {
        scenario.close();
    }

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
            curr.changePosition(0, -14);

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button upButton = activity.findViewById(R.id.upButton);
            for (int i = 0; i < 200; i++) {
                upButton.performClick();
            }


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

            int[] currPos = new int[2];
            currPos[0] = curr.getPosition()[0];
            currPos[1] = curr.getPosition()[1];

            Button downButton = activity.findViewById(R.id.downButton);
            for (int i=0; i < 200; i++) {
                downButton.performClick();
            }


            int[] newPos = curr.getPosition();

            assertEquals(newPos[0], currPos[0]);
            assertEquals(newPos[1], currPos[1]);
        });
    }
    @After
    public void teardown2() {
        scenario.close();
    }
}
