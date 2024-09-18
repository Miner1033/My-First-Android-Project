package com.example.canvashouse;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserAccountActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private TextView textViewAddress;

    private DataCenter databaseCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        textViewName = findViewById(R.id.user_info_name_tv);
        textViewEmail = findViewById(R.id.user_info_email_tv);
        textViewPhone = findViewById(R.id.user_info_phone_tv);
        textViewAddress = findViewById(R.id.user_info_address_tv);

        databaseCenter = new DataCenter(this);


    }


}
