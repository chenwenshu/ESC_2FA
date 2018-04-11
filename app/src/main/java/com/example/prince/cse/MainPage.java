package com.example.prince.cse;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainPage extends AppCompatActivity {

    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        welcomeMessage = (TextView) findViewById(R.id.welcomeMessage);
        Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/nicefont.ttf");
        welcomeMessage.setTypeface(typeface);

        Intent intent = getIntent();
        String nameOfUser = intent.getStringExtra("nameOfUser");

        welcomeMessage.setText("Welcome, "+nameOfUser);


    }



}
