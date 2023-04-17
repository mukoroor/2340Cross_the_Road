package com.example.team18;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;

import org.junit.Test;

import java.util.ArrayList;

public class JustinSprint5JUniuts {


    @Test
    public void checkForMinecarts() {
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("Justin",5);

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;

            ArrayList<Vehicle> vehicles = g.getVehicleList();
            boolean found = false;

            for (Vehicle vehicle : vehicles) {
                if (vehicle instanceof Minecart) {
                    found = true;
                }
            }

            assertTrue(found);
        });

    }
    @Test
    public void checkMinecartAttributes(){
        Intent playIntent = new Intent(ApplicationProvider.getApplicationContext(),
                GameScreenActivity.class);
        playIntent.putExtra("Justin",5);

        // Launch the activity with the intent
        ActivityScenario<GameScreenActivity> scenario = ActivityScenario.launch(playIntent);

        // Assert that the activity is in the resumed state
        scenario.onActivity(activity -> {
            GameScreenActivity g = (GameScreenActivity) activity;

            ArrayList<Vehicle> vehicles = g.getVehicleList();
            boolean found = false;

            // Create a new LinearLayout object with specific properties
            LinearLayout row = new LinearLayout(g.getApplicationContext());
            row.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            row.setLayoutParams(rowParams);

            // Create a new ImageView object with a specific image resource
            ImageView image = new ImageView(g.getApplicationContext());
            image.setImageResource(R.drawable.minecarts);

            // Create a new ImageView object with a specific image resource
            ImageView tracks = new ImageView(g.getApplicationContext());
            tracks.setImageResource(R.drawable.traintracks);


            // Create a new Minecart object
            Minecart minecart = new Minecart(row, image, tracks);



            // Check if the parameters match the parameters passed to the constructor
            assertEquals(row, minecart.getRow());
            assertEquals(image, minecart.getImage());
            assertEquals(tracks, minecart.getTracks());


        });


    }
}
