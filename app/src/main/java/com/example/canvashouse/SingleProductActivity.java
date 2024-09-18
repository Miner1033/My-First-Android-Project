package com.example.canvashouse;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
public class SingleProductActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName;
    private EditText editTextPrice;
    private EditText editTextQuantity;
    private EditText editTextDescription;
    private ImageView imageViewProduct;
    private Button buttonUpdate;
    private Button buttonSelectImage;
    private Button buttonSearch;
    private TextView textViewProductId;

    private DataCenter databaseCenter;
    private byte[] productImageByteArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_product);

        setContentView(R.layout.activity_update_product);
        editTextName = findViewById(R.id.product_name_et);
        editTextPrice = findViewById(R.id.product_price_et);
        editTextQuantity = findViewById(R.id.product_quantity_et);
        editTextDescription = findViewById(R.id.product_description_et);
        imageViewProduct = findViewById(R.id.image_view_product);

       // buttonSearch = findViewById(R.id.button_search);
        textViewProductId = findViewById(R.id.text_view_product_id);

        databaseCenter = new DataCenter(this);
        buttonSearch.setOnClickListener(view -> searchProduct());

    }
    private void searchProduct() {
        String productName = editTextName.getText().toString().trim();
        if (productName.isEmpty()) {
            Toast.makeText(this, "Please enter a product name to search", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = databaseCenter.getProductByName(productName);
        if (cursor != null && cursor.moveToFirst()) {
            int productId = cursor.getInt(cursor.getColumnIndexOrThrow(DataCenter.COL_ID));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(DataCenter.COL_PRODUCT_PRICE));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(DataCenter.COL_PRODUCT_QUANTITY));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DataCenter.COL_PRODUCT_DESCRIPTION));
            byte[] image = cursor.getBlob(cursor.getColumnIndexOrThrow(DataCenter.COL_PRODUCT_IMAGE_URI));

            editTextPrice.setText(String.valueOf(price));
            editTextQuantity.setText(String.valueOf(quantity));
            editTextDescription.setText(description);
            textViewProductId.setText("Product ID: " + productId);

            if (image != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                imageViewProduct.setImageBitmap(bitmap);
                productImageByteArray = image;
            }
            cursor.close();
        } else {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
        }
    }
}