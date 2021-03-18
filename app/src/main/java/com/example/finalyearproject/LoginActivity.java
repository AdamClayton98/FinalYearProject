package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){
        //   Intent intent = new Intent(this, LoginActivity.class);
        EditText email_field = (EditText) findViewById(R.id.loginEmail);
        EditText password_field = (EditText) findViewById(R.id.loginPassword);
        String email = email_field.getText().toString();
        String password = password_field.getText().toString();


    }
}