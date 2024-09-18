package com.example.canvashouse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText etUsername = findViewById(R.id.signup_username_et);
        EditText etEmail = findViewById(R.id.signup_email_et);
        EditText etPassword = findViewById(R.id.signup_password_et);
        EditText etConfirmPassword = findViewById(R.id.signup_confirm_password_et);
        EditText etMobile = findViewById(R.id.signup_phone_number_et);
        EditText etAddress = findViewById(R.id.signup_address_user_et);
        Button btnRegister = findViewById(R.id.signup_btn);
        Button btnLogin = findViewById(R.id.login_btn);




        btnRegister.setOnClickListener(v-> {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();
            String mobile = etMobile.getText().toString();
            String address = etAddress.getText().toString();


            if (password.isEmpty() || username.isEmpty() || email.isEmpty() || confirmPassword.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    DataCenter dbCenter = new DataCenter(SignUpActivity.this);
                    boolean valInserted = dbCenter.insertUser(username, email, password, mobile, address);

                    if (valInserted) {
                        Toast.makeText(SignUpActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }




        });




        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUpActivity.this, "Login button clicked!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    }
