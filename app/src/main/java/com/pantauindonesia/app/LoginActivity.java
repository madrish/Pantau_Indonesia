package com.pantauindonesia.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    Button login,signup;
    EditText user,pass;
    String usernametxt,passwordtxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText)findViewById(R.id.editText);
        pass = (EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.button);
        signup=(Button)findViewById(R.id.button2);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                login.setEnabled(false);
                signup.setEnabled(false);
                usernametxt = user.getText().toString();
                passwordtxt = pass.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    status("status",true);
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(
                                            LoginActivity.this,
                                            //MapsActivity.class
                                            MainActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),
                                            "Successfully Logged in",
                                            Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "User belum terdaftar atau password salah",
                                            Toast.LENGTH_LONG).show();
                                    login.setEnabled(true);
                                    signup.setEnabled(true);
                                }
                            }
                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });

        if(loadStatus("status")){
            Intent intent = new Intent(
                    LoginActivity.this,
                    MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }

    }
    private boolean loadStatus(String key) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean(key, false);
    }

    private void status(String key, boolean val) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }
}
