package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameOverScreenActivity extends AppCompatActivity {
    int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        TextView scoreTextView = findViewById(R.id.finalScoreView);
        RelativeLayout quitButton = findViewById(R.id.quit);
        RelativeLayout playButton = findViewById(R.id.restart);


        finalScore = getIntent().getIntExtra("finalScore", 0);

        float fontSize = 40;

        scoreTextView.setText("Final Score:\n" + finalScore);
//        scoreTextView.setTextAlignment("center");
        scoreTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        quitButton.setOnClickListener(e -> {

            finish();
            System.exit(0);
        });

        playButton.setOnClickListener(e -> {
            Intent player = new Intent(this, SpriteSelector.class);
            startActivity(player);
        });
    }

    public int getFinalScore() {
        return finalScore;
    }
}