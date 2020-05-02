package com.inihood.loginandregistermvvm.auth.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inihood.loginandregistermvvm.R;
import com.inihood.loginandregistermvvm.auth.DashboardActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private EditText email;
    private Button loginBtn;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = this.getSharedPreferences("USER_CREDENTIALS",MODE_PRIVATE);
        final Boolean isloggedin = sharedPreferences.getBoolean("ISLOGGEDIN",false);

        final String required_email = sharedPreferences.getString("EMAIL","DEFAULT_EMAIL");
        final String required_password = sharedPreferences.getString("PASSWORD","DEFAULT_PASSWORD");
        final String required_username = sharedPreferences.getString("USERNAME","DEFAULT_USERNAME");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.btnCreatesignin);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(required_email, required_password, required_username);
            }
        });
    }

    private void validate(String required_email, String required_password, String required_username) {
        String email_in = email.getText().toString();
        String password_in = password.getText().toString();
        if (!TextUtils.isEmpty(email_in) && !TextUtils.isEmpty(password_in)){
            if (email_in.contains("@")){
                isEmailLogin(email_in, password_in, required_email, required_password);
            }else {
                isUsernameLogin(email_in, password_in, required_password, required_username);
            }
        }else {
            Toast.makeText(this,"Email Please check the fields",Toast.LENGTH_LONG).show();
        }
    }

    private void isUsernameLogin(String username_in, String password_in, String required_password, String required_username) {
        if(username_in.equals(required_username) && password_in.equals(required_password)) {
            sharedPreferences.edit().putBoolean("ISLOGGEDIN",false).apply();
            startMainActivity();
        }
        else {
            Toast.makeText(this,"Username or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void isEmailLogin(String email, String password, String required_email, String required_password) {
        if(email.equals(required_email) && password.equals(required_password)) {
            sharedPreferences.edit().putBoolean("ISLOGGEDIN",false).apply();
            startMainActivity();
        }
        else {
            Toast.makeText(this,"Email address or password is incorrect",Toast.LENGTH_LONG).show();
        }
    }

    private void startMainActivity() {
        DashboardActivity.start(this);
        finish();
    }

    public void reg(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
