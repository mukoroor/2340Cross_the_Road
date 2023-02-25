package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        //this make a button object that is linked to the exit button
        RelativeLayout quitButton = findViewById(R.id.quitButton);
        RelativeLayout playButton = findViewById(R.id.playButton);

        TableRow gameTitle = findViewById(R.id.gameImage);
        TableRow navigation = findViewById(R.id.navigation);

        final int viewWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        final int viewHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        /*distributing heights of Layout
          gameTitle: 60%;
          navigation: 40%;
        * */
        int percentHeight = (viewHeight / 10);

        gameTitle.requestLayout();
        navigation.requestLayout();
        gameTitle.setLayoutParams(new LinearLayout.LayoutParams(viewWidth,
                viewHeight - 4 * percentHeight));
        navigation.setLayoutParams(new LinearLayout.LayoutParams(viewWidth, 4 * percentHeight));

        //I used an action event to set what happens when my button is clicked on.

        quitButton.setOnClickListener(e -> {
            //this entire method is basically just exits out the game from the start menu.
            finish();
            System.exit(0);
        });

        playButton.setOnClickListener(e -> {
            Intent player = new Intent(this, SpriteSelector.class);
            startActivity(player);
        });
    }
}