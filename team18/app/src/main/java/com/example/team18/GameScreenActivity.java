package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GameScreenActivity extends AppCompatActivity {

    private int level;

    private Sprite player;

    private int playerIndex;

    ImageView playerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        level = retrieveGameDifficulty();
        player = Sprite.parseString(retrievePlayerInfo());

        playerIndex = player.getSpriteIndex();

        playerImage = findViewById(R.id.player);

        playerImage.setImageResource(player.spriteOptions[playerIndex][0]);
    }

    private int retrieveGameDifficulty() {
        return getIntent().getIntExtra("level", 0);
    }

    private String retrievePlayerInfo() {
        return getIntent().getStringExtra("player");
    }
}