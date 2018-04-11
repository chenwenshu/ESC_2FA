package com.example.prince.cse;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Random;
import static android.Manifest.permission.SEND_SMS;

/**
 * Created by doanthanh on 3/4/18.
 */

public class OTPVerification extends AppCompatActivity{
    private BackgroundTask backgroundTask = null;


    EditText inputOTP;

    Button buttonVerify;

    static String pin;
    String name;
    String email;
    String password;
    String fromWhere;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);


        String[] to = {"esc.kyc1@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "OTP VERIFICATION");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Please enter this OTP: "+generateOTP());
        try{
            startActivity(Intent.createChooser(emailIntent, "Sending Email..."));

        } catch (ActivityNotFoundException ex){

        }


        inputOTP = (EditText) findViewById(R.id.verifiedOTP);

        buttonVerify = (Button) findViewById(R.id.buttonVerify);

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (inputOTP.getText().toString().equals(pin)){
                    if (fromWhere.equals("register")) {
                        attemptRegister();
                    }
                    else if (fromWhere.equals("login")) {
                        attemptLogin();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    inputOTP.setError("OTP does not match.");
                }
            }
        });


        Intent intent = getIntent();
        fromWhere = intent.getStringExtra("fromWhere");
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");



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
