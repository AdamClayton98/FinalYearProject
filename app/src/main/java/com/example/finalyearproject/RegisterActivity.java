package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText registerName;
    EditText registerUsername;
    EditText registerEmail;
    EditText registerPassword;
    EditText registerConfirmPass;
    Button registerButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerName = findViewById(R.id.registerName);
        registerUsername=findViewById(R.id.registerUsername);
        registerEmail=findViewById(R.id.registerEmail);
        registerPassword=findViewById(R.id.registerPassword);
        registerConfirmPass=findViewById(R.id.registerConfirmPassword);
        registerButton=findViewById(R.id.buttonRegister);

        Button backButton = findViewById(R.id.registerBackButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();

        validateAndCreateUser();

    }

    public void validateAndCreateUser(){
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=registerName.getText().toString();
                String username=registerUsername.getText().toString();
                String email=registerEmail.getText().toString();
                String password=registerPassword.getText().toString();
                String confirmPassword=registerConfirmPass.getText().toString();

                if(name.isEmpty()){
                    registerName.setError("Name is required");
                    return;
                }

                if(username.isEmpty()){
                    registerUsername.setError("Username is required");
                    return;
                }

                if(email.isEmpty()){
                    registerName.setError("Email is required");
                    return;
                }

                if(password.isEmpty()){
                    registerPassword.setError("Password is required");
                    return;
                }

                if(confirmPassword.isEmpty()){
                    registerConfirmPass.setError("Password confirmation is required");
                    return;
                }

                //TODO Implement validation at some point

                if(!password.equals(confirmPassword)){
                    registerConfirmPass.setError("Confirmation password does not match password");
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}