package com.pantauindonesia.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {
    EditText usernameEditText,passwordEditText,passwordAgainEditText, phoneTxt, emailTxt, addressTxt;
    Button submit;
    String usernametxt, passwordtxt, passwordagaintxt, phonetxt, emailtxt, addresstxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        submit = (Button) findViewById(R.id.button3);
        usernameEditText = (EditText) findViewById(R.id.user);
        passwordEditText = (EditText) findViewById(R.id.password1);
        passwordAgainEditText = (EditText) findViewById(R.id.password2);
        phoneTxt = (EditText) findViewById(R.id.nohapetxt);
        emailTxt = (EditText) findViewById(R.id.emailtxt);
        addressTxt = (EditText) findViewById(R.id.addresstxt);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(false);
                usernametxt = usernameEditText.getText().toString();
                passwordtxt = passwordEditText.getText().toString();
                passwordagaintxt = passwordAgainEditText.getText().toString();
                phonetxt = phoneTxt.getText().toString();
                emailtxt = emailTxt.getText().toString();
                addresstxt = addressTxt.getText().toString();

                if(!passwordtxt.equals(passwordagaintxt)){
                    submit.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Password tidak sama",Toast.LENGTH_LONG).show();
                }else{
                    if (usernametxt.equals("") && passwordtxt.equals("")  && phonetxt.equals("") &&
                        emailtxt.equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Silahkan diisi dengan benar",
                                Toast.LENGTH_LONG).show();
                        submit.setEnabled(true);

                    } else {
                        // Save new user data into Parse.com Data Storage
                        ParseUser user = new ParseUser();
                        user.setUsername(usernametxt);
                        user.setPassword(passwordtxt);
                        user.setEmail(emailtxt);
                        user.put("phone",phonetxt);
                        user.put("address",addresstxt);
                        user.saveInBackground();
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    status("status",true);
                                    // Show a simple Toast message upon successful registration
                                    Toast.makeText(getApplicationContext(),
                                            "Sukses mendaftar, Selamat Datang "+usernametxt,
                                            Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    SignUpActivity.this.finish();
                                } else {
                                    submit.setEnabled(true);
                                    Toast.makeText(getApplicationContext(),
                                            "Sign up Error", Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    private void status(String key, boolean val) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            Intent i = new Intent (SignUpActivity.this, LoginActivity.class);

            startActivity(i);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
