package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GameScreenActivity extends AppCompatActivity {

    private int level;

    private Sprite player;

    private ImageView playerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        FrameLayout mainFrame = findViewById(R.id.mainFrame);
        Sprite sprite = new Sprite();

        player = sprite.parseString(retrievePlayerInfo());

        level = retrieveGameDifficulty();

        switch (player.)


        }

        mainFrame.addView(player);
    }

    private int retrieveGameDifficulty() {
        return getIntent().getIntExtra("level", 0);
    }

    private String retrievePlayerInfo() {
        return getIntent().getStringExtra("player");
    }
}