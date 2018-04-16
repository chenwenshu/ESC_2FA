package com.example.esc.esc;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by doanthanh on 3/4/18.
 */

public class OTPVerification extends AppCompatActivity{
    private BackgroundTask backgroundTask = null;
    
    EditText inputOTP;
    Button buttonVerify;
    static String pin;
    String name, email, password, fromWhere;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        
        Intent intent = getIntent();
        fromWhere = intent.getStringExtra("fromWhere");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        
        String[] to = {email};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "OTP VERIFICATION");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter this OTP: "+generateOTP());
        try{
            startActivity(Intent.createChooser(emailIntent, "Sending Email..."));

        } catch (ActivityNotFoundException ignored){

        }
        
        inputOTP = findViewById(R.id.verifiedOTP);
        buttonVerify = findViewById(R.id.buttonVerify);
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputOTP.getText().toString().equals(pin)){
                    switch (fromWhere){
                        case "register":
                            attemptRegister();
                            break;
                        case "login":
                            attemptLogin();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                } else {
                    inputOTP.setError("OTP does not match.");
                }
            }
        });
    }

    private void attemptLogin() {
        String method = "login";
        backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(method,email,password);
    }

    private void attemptRegister(){
        String method = "register";
        backgroundTask = new BackgroundTask(getApplicationContext());
        backgroundTask.execute(method,name,email,password);
        
        finish();
    }


    private static String generateOTP() {
        String chars = "abcdefghijklmnopqrstuvwxyz"
                + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789!@%$%&^?|~'\"#+=";
        final int PW_LENGTH = 6;
        Random rnd = new SecureRandom();
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < PW_LENGTH; i++)
            pass.append(chars.charAt(rnd.nextInt(chars.length())));

        pin = pass.toString();
        return pin;
    }
}
