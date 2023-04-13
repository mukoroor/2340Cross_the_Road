package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameOverScreenActivity extends AppCompatActivity {
    private String finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        TextView scoreTextView1 = findViewById(R.id.finalScoreView1);
        TextView scoreTextView2 = findViewById(R.id.finalScoreView2);
        RelativeLayout quitButton = findViewById(R.id.quit);
        RelativeLayout playButton = findViewById(R.id.restart);


        finalScore = String.valueOf(getIntent().getIntExtra("finalScore", 0));

        scoreTextView1.setText(finalScore);
        scoreTextView2.setText(finalScore);

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
        return Integer.parseInt(finalScore);
    }
}