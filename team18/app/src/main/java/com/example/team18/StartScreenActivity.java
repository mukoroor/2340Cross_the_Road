package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        //this make a button object that is linked to the exit button
        Button btn1 = (Button) findViewById(R.id.exitbutton);
        Button btn2 = (Button) findViewById(R.id.playbutton);
        //I used an action event to set what happens when my button is clicked on.
        btn1.setOnClickListener(new View.OnClickListener() {
            //this entire method is basically just exits out the game from the start menu.
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });

        btn2.setOnClickListener(e -> {
            Intent player = new Intent(this, SpriteSelector.class);
            startActivity(player);
        });
    }
}