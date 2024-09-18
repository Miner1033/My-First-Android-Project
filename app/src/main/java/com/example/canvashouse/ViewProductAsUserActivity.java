package com.example.canvashouse;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewProductAsUserActivity extends AppCompatActivity {
    private ListView listViewProducts;
    private DataCenter databaseCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_as_user);

        listViewProducts = findViewById(R.id.list_view_pro);
        ImageView ivlogin = findViewById(R.id.image_view_login);
        ImageView ivmcart = findViewById(R.id.image_view_cart);
        ImageView ivaccount = findViewById(R.id.image_view_account);
        ImageView ivinfo = findViewById(R.id.image_view_about_us);
        SearchView svview = findViewById(R.id.search_view);

        databaseCenter = new DataCenter(this);

        // Handle search view click event
        svview.setOnSearchClickListener(v -> {
            Intent intent = new Intent(ViewProductAsUserActivity.this, SingleProductActivity.class);
            startActivity(intent);
        });

        ivlogin.setOnClickListener(v -> {
            Intent intent = new Intent(ViewProductAsUserActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ivmcart.setOnClickListener(v -> {
            Intent intent = new Intent(ViewProductAsUserActivity.this, UserCartActivity.class);
            startActivity(intent);
        });

        ivaccount.setOnClickListener(v -> {
            Intent intent = new Intent(ViewProductAsUserActivity.this, UserAccountActivity.class);
            startActivity(intent);
        });

        ivinfo.setOnClickListener(v -> {
            Intent intent = new Intent(ViewProductAsUserActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });

        displayProducts(); // Display products initially
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
}
