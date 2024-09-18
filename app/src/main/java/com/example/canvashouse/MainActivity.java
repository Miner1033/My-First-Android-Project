package com.example.canvashouse;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView welcometv =findViewById(R.id.welcome_tv);
        TextView CanvasHousetv =findViewById(R.id.Canvas_tv);
       // TextView qualitytv =findViewById(R.id.quality_tv);
        Button getstartbtn=findViewById(R.id.get_st_btn);
        getstartbtn.setOnClickListener(v->{
            Toast.makeText(MainActivity.this, "Getting start the CanvasHouse app", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        });

    }
}