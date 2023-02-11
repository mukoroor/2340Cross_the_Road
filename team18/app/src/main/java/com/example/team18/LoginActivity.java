package com.example.team18;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edUsername;
    Button pre, submit;
    Spinner spinner;
    String[] dif = {"easy", "medium", "hard"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LoginActivity.this,
                android.R.layout.simple_spinner_item,dif);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        pre = findViewById(R.id.pre);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                if(username.length()==0) {
                    System.out.println("here");
                    Toast.makeText(getApplicationContext(), "Please fill the username ",
                            Toast.LENGTH_SHORT).show();
                }
//                startActivity(new Intent(LoginActivity.this, NewActivity.class));
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
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO
    }
}