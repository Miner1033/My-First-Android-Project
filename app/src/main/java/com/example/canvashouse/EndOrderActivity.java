package com.example.canvashouse;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EndOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_end_order);
        TextView con=findViewById(R.id.continue_shopping_tv);
        con.setOnClickListener(v->{
           // Toast.makeText(FinalOrderActivity.this, "Payment Done", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(EndOrderActivity.this, ViewProductAsUserActivity.class);
            startActivity(intent);
        });

    }
}