package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameWinScreenActivity extends AppCompatActivity {
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win_screen);
        score = retrieveFinalScore();

        TextView scoreTextView = findViewById(R.id.finalScoreView);
        RelativeLayout quitButton = findViewById(R.id.quit);
        RelativeLayout playButton = findViewById(R.id.restart);

        float fontSize = 40;

        scoreTextView.setText("Final Score:\n" + score);
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

    /**
     * Uses the intent to get the final game score
     * @return the final game score
     */
    private int retrieveFinalScore() {
        return getIntent().getIntExtra("finalScore", 0);
    }

}