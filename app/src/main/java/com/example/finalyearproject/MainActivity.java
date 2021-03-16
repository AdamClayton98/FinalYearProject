package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
     //   Intent intent = new Intent(this, LoginActivity.class);
        EditText email_field = (EditText) findViewById(R.id.input_email);
        EditText password_field = (EditText) findViewById(R.id.input_password);
        String email = email_field.getText().toString();
        String password = password_field.getText().toString();



    }
}