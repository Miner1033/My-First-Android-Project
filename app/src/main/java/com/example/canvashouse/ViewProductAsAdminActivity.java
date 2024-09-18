package com.example.canvashouse;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProductAsAdminActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private DataCenter databaseCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_as_admin); // Set the layout here

        listViewProducts = findViewById(R.id.list_view_products);
        Button buttonUpdate = findViewById(R.id.button_update);
        Button buttonDelete = findViewById(R.id.button_delete);
        databaseCenter = new DataCenter(this);

        displayProducts(); // Display products initially
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Update button click
                handleUpdate();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the Delete button click
                handleDelete();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the displayed products when returning to this activity
        displayProducts();
    }

    private void displayProducts() {
        Cursor cursor = databaseCenter.getAllProducts();
        ProductAdapterClass adapter = new ProductAdapterClass(this, cursor, 0);
        listViewProducts.setAdapter(adapter);
    }
    private void handleUpdate() {
        // Logic for updating a product
        Intent intent = new Intent(ViewProductAsAdminActivity.this, UpdateProductActivity.class); // Assuming HomeActivity is the activity after login
        startActivity(intent);
    }
    private void handleDelete() {
        Intent intent = new Intent(ViewProductAsAdminActivity.this, DeleteProductActivity.class); // Assuming HomeActivity is the activity after login
        startActivity(intent);
        Toast.makeText(this, "Delete button clicked", Toast.LENGTH_SHORT).show();
    }
}
