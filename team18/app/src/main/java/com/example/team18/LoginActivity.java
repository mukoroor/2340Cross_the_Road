package com.example.team18;

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

import androidx.appcompat.app.AppCompatActivity;


/**
 * An Activity for displaying the login screen
 */

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText edUsername;

    private ImageView selectedSprite;
    private Button pre;
    private Button submit;
    private Spinner spinner;
    private String[] dif = {"EASY", "MEDIUM", "HARD"};

    private String selectedDiff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        int spriteInd = getSpriteInd();

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(LoginActivity.this,
                android.R.layout.simple_spinner_item, dif);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        selectedSprite = findViewById(R.id.imageView);
        selectedSprite.setImageResource(Sprite.spriteOptions[spriteInd][0]);


        edUsername = findViewById(R.id.editTextLoginUserName);


        pre = findViewById(R.id.pre);
        pre.setOnClickListener(view -> finish());


        submit = findViewById(R.id.submit);
        submit.setOnClickListener(view -> {
            boolean isValid = true;
            String username = edUsername.getText().toString().trim();
            if (username.length() == 0 || username.length() > 10) {
                Toast.makeText(getApplicationContext(),
                    "The username should be 1-10 characters long",
                        Toast.LENGTH_SHORT).show();
                isValid = false;
            }
            if (isValid) {
                Sprite player = new Sprite(spriteInd, username);
                toGame(player, spinner.getSelectedItemPosition());
            }

        });

    }

    /**
     * A method for controlling logic when an item is selected
     * @param parent The spinner object
     * @param view the view
     * @param position the index the user is selecting
     * @param id the id of the spinner
     */
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String content = parent.getItemAtPosition(position).toString();
        switch (parent.getId()) {
        case R.id.spinner:
            Toast.makeText(LoginActivity.this, "selected difficulties is " + content,
                Toast.LENGTH_SHORT).show();
            break;
        default:
            break;
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
        default:
            break;
        }
    }

    /**
     * Does nothing special when selected
     * @param parent the view
     */
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }

    /**
     * A method that creates a new intent and passes information to next activity
     * @param player The player to be passed into the game activity
     * @param difficulty the difficulty to be passed into game object of game activity
     */
    private void toGame(Sprite player, int difficulty) {
        Intent playIntent = new Intent(this, GameScreenActivity.class);
        playIntent.putExtra("level", difficulty);
        playIntent.putExtra("player", player.toString());
        startActivity(playIntent);
    }

    /**
     * Method for getting the image the player will use (as an index)
     * @return the index from the array full of images
     */
    private int getSpriteInd() {
        return getIntent().getIntExtra("index", 0);
    }

}