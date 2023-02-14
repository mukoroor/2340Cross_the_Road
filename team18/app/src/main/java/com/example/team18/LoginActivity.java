package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.SocketPermission;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edUsername;

    ImageView selectedSprite;

    ConstraintLayout screen;
    Button pre, submit;
    Spinner spinner;
    String[] dif = {"easy", "medium", "hard"};

    String selectedDiff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int spriteInd = retrieveSpriteInd();

        screen = findViewById(R.id.full_screen);
        int[] screenSize = new int[]{screen.getWidth(), screen.getHeight()};

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item,dif);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        selectedSprite = findViewById(R.id.imageView);
        selectedSprite.setImageResource(Sprite.spriteOptions[spriteInd][0]);


        edUsername = findViewById(R.id.editTextLoginUserName);


        pre = findViewById(R.id.pre);
        pre.setOnClickListener(view -> {
            finish();
        });


        submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            boolean isValid = true;
            String username = edUsername.getText().toString().trim();
            if(username.length()==0 || username.length() > 10) {
                Toast.makeText(getApplicationContext(), "The username should be 1-10 characters long",
                        Toast.LENGTH_SHORT).show();
                isValid = false;
            }
                if (isValid) {
                    Sprite player = new Sprite(spriteInd, username);
                    toGame(player, spinner.getSelectedItemPosition());
                }

        });

    }
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        String content = parent.getItemAtPosition(position).toString();
        switch (parent.getId()) {
            case R.id.spinner:
                Toast.makeText(LoginActivity.this, "selected difficulties is " + content,
                        Toast.LENGTH_SHORT).show();
        }
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                selectedDiff = dif[0];
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                selectedDiff = dif[1];
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                selectedDiff = dif[2];
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }

    private void toGame(Sprite player, int difficulty) {
        //TODO: link activity to game activity
        Intent playIntent = new Intent(this, SpriteSelector.class);
        playIntent.putExtra("level", difficulty);
        // TODO: create a player and pass on to next screen
        playIntent.putExtra("player", player.toString());
        startActivity(playIntent);
    }

    private int retrieveSpriteInd() {
        return getIntent().getIntExtra("index", 0);
    }

}