package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GameScreenActivity extends AppCompatActivity {
    private Sprite player;

    ImageView playerImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        player = Sprite.parseString(getPlayerInfo());

        int displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int difficulty= getGameDifficulty();

        Game currentGame = new Game(player, difficulty, displayWidth);


        playerImage = findViewById(R.id.player);

        int spriteIndex = player.getSpriteIndex();
        playerImage.setImageResource(Sprite.spriteOptions[spriteIndex][0]);
    }

    private int getGameDifficulty() {
        return getIntent().getIntExtra("level", 0);
    }

    private String getPlayerInfo() {
        return getIntent().getStringExtra("player");
    }
}