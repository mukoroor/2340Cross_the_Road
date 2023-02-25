package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class SpriteSelector extends AppCompatActivity {
    private static int pointer = 0;

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

        final TextView spriteDescription0 = findViewById(R.id.spriteDescription0);
        final TextView spriteDescription1 = findViewById(R.id.spriteDescription1);
        final TableRow spriteDescriptionWrapper = findViewById(R.id.spriteDescriptionWrapper);

        final RelativeLayout nextButton = findViewById(R.id.nextButton);
        final RelativeLayout backButton = findViewById(R.id.backButton);
        final TableRow navigation = findViewById(R.id.navigation);

        final int viewWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        final int viewHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        /*distributing heights of Layout
          characterGallery: 50%;
          spriteDescriptionWrapper: 20%;
          navigation: 30%;
        * */
        int percentHeight = (viewHeight / 10);

        characterGallery.requestLayout();
        spriteDescriptionWrapper.requestLayout();
        navigation.requestLayout();
        characterGallery.setLayoutParams(new LinearLayout.LayoutParams(viewWidth,
                viewHeight - 5 * percentHeight));
        spriteDescriptionWrapper.setLayoutParams(new LinearLayout.LayoutParams(viewWidth,
                percentHeight));
        navigation.setLayoutParams(new LinearLayout.LayoutParams(viewWidth,
                4 * percentHeight));



        leftButton.setOnClickListener(e -> {
            pointer = (pointer - 1 + spriteOptions.length) % spriteOptions.length;
            spriteView.setImageResource(spriteOptions[pointer][0]);
            spriteDescription0.setText(spriteDescriptions[pointer]);
            spriteDescription1.setText(spriteDescriptions[pointer]);
            leftButton.setForeground(ContextCompat.getDrawable(this, R.drawable.leftarrowhover));
            rightButton.setForeground(ContextCompat.getDrawable(this, R.drawable.rightarrow));
        });

        rightButton.setOnClickListener(e -> {
            pointer = (pointer + 1 + spriteOptions.length) % spriteOptions.length;
            spriteView.setImageResource(spriteOptions[pointer][0]);
            spriteDescription0.setText(spriteDescriptions[pointer]);
            spriteDescription1.setText(spriteDescriptions[pointer]);
            rightButton.setForeground(ContextCompat.getDrawable(this, R.drawable.rightarrowhover));
            leftButton.setForeground(ContextCompat.getDrawable(this, R.drawable.leftarrow));
        });


        nextButton.setOnClickListener(e -> {
            Intent login = new Intent(this, LoginActivity.class);
            login.putExtra("index", pointer);
            startActivity(login);
        });

        backButton.setOnClickListener(e -> finish());
    }
}