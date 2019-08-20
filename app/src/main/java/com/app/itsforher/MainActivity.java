package com.app.itsforher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText access_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Welcome");

        access_code = findViewById(R.id.access_code);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (access_code.getText().toString().length() > 1) {

                    Prefs prefs = new Prefs(MainActivity.this);
                    prefs.putUser(access_code.getText().toString());
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                    finish();
                } else
                    Toast.makeText(getBaseContext(), "Please enter a nickname, must be more than 2 letters", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
