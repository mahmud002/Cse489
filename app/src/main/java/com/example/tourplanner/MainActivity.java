package com.example.tourplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.linkSignup).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });
    }
}