package com.example.canvashouse;


import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;




public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditText etUsername = findViewById(R.id.username_et);
        EditText etPassword = findViewById(R.id.password_et);
        Button btnLogin = findViewById(R.id.login_btn);
        TextView tvSignUp = findViewById(R.id.signup_tv);


        tvSignUp.setOnClickListener(v -> {

            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();


            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
            } else {


                if (username.equals("adminhc") && password.equals("adminhc")) {
                    Toast.makeText(LoginActivity.this, "Welcome admin!!", Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class); // Assuming HomeActivity is the activity after login
                    startActivity(intent);
                } else {
                    DataCenter dbCenter = new DataCenter(LoginActivity.this);
                    boolean result = dbCenter.checkUserByUsername(username, password);
                    if (result) {
                        Toast.makeText(LoginActivity.this, "Welcome user!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, ViewProductAsUserActivity.class); // Assuming HomeActivity is the activity after login
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Username and password!", Toast.LENGTH_SHORT).show();


                    }
                }
            }
        });
    }

    }





