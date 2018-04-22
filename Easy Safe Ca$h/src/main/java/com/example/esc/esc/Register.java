package com.example.esc.esc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class Register extends AppCompatActivity{
	
	
	private BackgroundTask backgroundTask = null;
	
	EditText inputName;
	EditText inputEmail;
	EditText inputPassword;
	EditText inputConfirmPassword;
	
	
	Button buttonRegister;
	
	
	public static String pin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		inputName = findViewById(R.id.editName);
		inputEmail = findViewById(R.id.editEmail);
		inputPassword = findViewById(R.id.editPassword);
		inputConfirmPassword = findViewById(R.id.editConfirmPassword);
		
		buttonRegister = findViewById(R.id.buttonRegister);
		
		
		buttonRegister.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				
				attemptSendEmail();
				
			}
		});
		
	}
	
	private void attemptSendEmail(){
		if (backgroundTask != null){
			return;
		}
		
		// Reset errors.
		inputName.setError(null);
		inputEmail.setError(null);
		inputPassword.setError(null);
		inputConfirmPassword.setError(null);
		
		// Store values at the time of the login attempt.
		String name = inputName.getText().toString();
		String email = inputEmail.getText().toString();
		String password = inputPassword.getText().toString();
		String confirmPassword = inputConfirmPassword.getText().toString();
		
		boolean cancel = false;
		View focusView = null;
		
		
		// Check for a valid full name.
		if (TextUtils.isEmpty(name) || !isValidUsername(name){
			inputName.setError(getString(R.string.error_field_required));
			focusView = inputName;
			cancel = true;
		}
		
		// Check for a valid email address.
		if (TextUtils.isEmpty(email)){
			inputEmail.setError(getString(R.string.error_field_required));
			focusView = inputEmail;
			cancel = true;
		} else if (!isEmailValid(email)){
			inputEmail.setError(getString(R.string.error_invalid_email));
			focusView = inputEmail;
			cancel = true;
		}
		
		
		// Check for a valid password, if the user entered one.
		if (TextUtils.isEmpty(password) || isPasswordInvalid(password)){
			inputPassword.setError(getString(R.string.error_invalid_password));
			focusView = inputPassword;
			cancel = true;
		}
		
		if (TextUtils.isEmpty(confirmPassword) || isPasswordInvalid(confirmPassword)){
			inputConfirmPassword.setError(getString(R.string.error_invalid_password));
			focusView = inputConfirmPassword;
			cancel = true;
		}
		
		//check if password and confirm password is the same
		
		if (!(password.equals(confirmPassword))){
			
			inputConfirmPassword.setError(getString(R.string.password_not_matching));
			focusView = inputConfirmPassword;
			cancel = true;
			
		}
		
		
		if (cancel){
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else{
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			
			//sendEmail();
			
			
			Intent intent = new Intent(getApplicationContext(), OTPVerification.class);
			intent.putExtra("fromWhere", "register");
			intent.putExtra("name", inputName.getText().toString());
			intent.putExtra("password", inputPassword.getText().toString());
			intent.putExtra("email", inputEmail.getText().toString());
			startActivity(intent);
			
			
		}
	}
		    
	public static boolean isValidUsername(String name){
        return ((name.length()>=5) && !(name.isEmpty()) && (name.length()<=15) && !Character.isDigit(name.charAt(0)) && name.matches("[A-Za-z0-9]+"));
    }
		    
	public static boolean isEmailValid(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
	
	
	public static boolean isPasswordValid(String password) {
        return ((password.length() >= 6) && (password.length()<=20) && (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!()*^&+=])(?=\\S+$).{6,}$")));
    }
	
	
}
