package com.example.esc.esc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Prince on 2/17/2018.
 */

public class BackgroundTask extends AsyncTask<String, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    BackgroundTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {

//        String registrationURL = "http://10.12.176.163/escdb/register.php";
//        String loginURL = "http://10.12.176.163/escdb/login.php";

        String registrationURL = "http://10.12.118.242:8888/register.php";
        String loginURL = "http://10.12.118.242:8888/login.php";
        
        String method = strings[0];
        if (method.equals("register")) {

            String name = strings[1];
            String email = strings[2];
            String password = strings[3];

            try {
                URL url = new URL(registrationURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));

                String data = URLEncoder.encode("name","UTF-8") + "=" + URLEncoder.encode(name,"UTF-8") + "&" + URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&" + URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream IS = httpURLConnection.getInputStream();
                IS.close();

                return "Registration Success";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        else if (method.equals("login")) {

            String email = strings[1];
            String password = strings[2];

            try {

                Log.i("print",email+password);
                URL url = new URL(loginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();

                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("email","UTF-8") + "=" + URLEncoder.encode(email,"UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();

                return response.toString();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {


        if (result.equals("Registration Success")) {

            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context,LoginActivity.class);
            context.startActivity(intent);
        }

        else {

            if (result.equals("Login Failed! Please try again")) {

                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                Intent intent = new Intent((context),LoginActivity.class);
                context.startActivity(intent);
            }

            else {

                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();


                //login success


//                Intent intent = new Intent(context,MainPage.class);
//                intent.putExtra("nameOfUser",result);
//                context.startActivity(intent);
                Intent intent = new Intent(context, FingerprintActivity.class);
                intent.putExtra("nameOfUser", result);
                context.startActivity(intent);
            }

        }
    }


}
