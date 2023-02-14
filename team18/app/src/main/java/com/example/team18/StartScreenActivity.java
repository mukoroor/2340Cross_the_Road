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
        //this make a button object that is linked to the start button
        Button btn2 = (Button) findViewById(R.id.playbutton);

        btn1.setOnClickListener(new View.OnClickListener() {
            //this entire method is basically just exits out the game from the start menu.
            @Override
            /**
             * A method that takes in View so when the exit button a clicked on the view it
             * actually exits
             */
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
                System.exit(0);
            }
        });
        //this entire method is basically just starts the game and moves to the spriteSelctor.

        btn2.setOnClickListener(e -> {
            Intent start = new Intent(this, SpriteSelector.class);
            startActivity(start);
        });


    }
}