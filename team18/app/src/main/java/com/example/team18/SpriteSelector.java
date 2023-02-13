package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SpriteSelector extends AppCompatActivity {
    private static Sprite selected;
    private int pointer = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sprite_selector);

        int[][] spriteOptions = Sprite.spriteOptions;
        String[] spriteDescriptions = Sprite.spriteDescriptions;

        final Button leftButton = findViewById(R.id.leftButton);
        final Button rightButton = findViewById(R.id.rightButton);
        final ImageView spriteView = findViewById(R.id.spriteView);
        final TableRow characterGallery = findViewById(R.id.characterGallery);

        final TextView spriteDescription = findViewById(R.id.spriteDescription);

        final Button submitButton = findViewById(R.id.submitButton);
        final Button returnButton = findViewById(R.id.returnButton);
        final TableRow navigation = findViewById(R.id.navigation);

        final int viewWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        final int viewHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        int percentHeight = (viewHeight / 10);

        characterGallery.requestLayout();
        navigation.requestLayout();
        characterGallery.setLayoutParams(new LinearLayout.LayoutParams(viewWidth, viewHeight - 4 * percentHeight));
        navigation.setLayoutParams(new LinearLayout.LayoutParams(viewWidth, 4 * percentHeight));



        leftButton.setOnClickListener(e -> {
            pointer = (pointer - 1 + spriteOptions.length) % spriteOptions.length;
            spriteView.setImageResource(spriteOptions[pointer][0]);
            spriteDescription.setText(spriteDescriptions[pointer]);
        });

        rightButton.setOnClickListener(e -> {
            pointer = (pointer + 1 + spriteOptions.length) % spriteOptions.length;
            spriteView.setImageResource(spriteOptions[pointer][0]);
            spriteDescription.setText(spriteDescriptions[pointer]);
        });

        submitButton.setOnClickListener( e -> {
            Intent login = new Intent(this, LoginActivity.class);
            login.putExtra("index", pointer);
            startActivity(login);
        });

        returnButton.setOnClickListener( e -> {
            finish();
        });
    }
}