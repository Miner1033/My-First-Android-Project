package com.example.canvashouse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InsertProductActivity extends AppCompatActivity {

    private EditText productNameEditText;
    private EditText productPriceEditText;
    private EditText productQuantityEditText;
    private EditText productDescriptionEditText;
    private ImageView selectedImageView;
    private Button selectImageButton;
    private Button insertProductButton;
    private DataCenter dataCenter;
    private byte[] imageByteArray;

    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        // Initialize views
        productNameEditText = findViewById(R.id.product_name_et);
        productPriceEditText = findViewById(R.id.product_price_et);
        productQuantityEditText = findViewById(R.id.product_quantity_et);
        productDescriptionEditText = findViewById(R.id.product_description_et);
        selectedImageView = findViewById(R.id.select_image_iv);
        selectImageButton = findViewById(R.id.select_image_btn);
        insertProductButton = findViewById(R.id.insert_product_btn);

        //DataCenter instance
        dataCenter = new DataCenter(this);

        // Initialize image picker launcher
        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    selectedImageView.setImageBitmap(imageBitmap);
                    imageByteArray = bitmapToByteArray(imageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        // Set click listener for selecting an image
        selectImageButton.setOnClickListener(view -> showImageSelectionDialog());

        // Set click listener for inserting a product
        insertProductButton.setOnClickListener(view -> insertProduct());

        // Optionally, insert test data to ensure the database is created and functional
        //insertTestProduct();
    }

    private void showImageSelectionDialog() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        imagePickerLauncher.launch(pickIntent);
    }

    private byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void insertProduct() {
        String name = productNameEditText.getText().toString().trim();
        String priceString = productPriceEditText.getText().toString().trim();
        String quantityString = productQuantityEditText.getText().toString().trim();
        String description = productDescriptionEditText.getText().toString().trim();

        if (name.isEmpty() || priceString.isEmpty() || quantityString.isEmpty() || description.isEmpty() || imageByteArray == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceString);
            quantity = Integer.parseInt(quantityString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price or quantity", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert the product into the database
        boolean isInserted = dataCenter.insertProduct(name, price, quantity, description, imageByteArray);
        if (isInserted) {
            Toast.makeText(this, "Product inserted successfully", Toast.LENGTH_SHORT).show();
            // Clear input fields and image view after successful insertion
            clearFields();
        } else {
            Toast.makeText(this, "Error inserting product", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        productNameEditText.setText("");
        productPriceEditText.setText("");
        productQuantityEditText.setText("");
        productDescriptionEditText.setText("");
        selectedImageView.setImageResource(android.R.color.transparent);
        imageByteArray = null;
    }

    // optionally, this method to insert test data during development/testing
//    private void insertTestProduct() {
//        byte[] dummyImage = new byte[0];  // Replace with actual byte array of a test image
//        boolean isInserted = dataCenter.insertProduct("Test Product", 9.99, 10, "Test Description", dummyImage);
//        if (isInserted) {
//            Toast.makeText(this, "Test Product inserted successfully", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Error inserting Test Product", Toast.LENGTH_SHORT).show();
//        }
//    }
}
