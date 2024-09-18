package com.example.canvashouse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataCenter extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CanvasHouse_DB";
    public static final int DATABASE_VERSION = 2;
    public static final String TABLE_REGISTER = "registration";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_MOBILE = "mobile";
    public static final String COL_ADDRESS = "address";
    public static final String COL_PRODUCT_NAME = "productName";
    public static final String COL_PRODUCT_PRICE = "productPrice";
    public static final String COL_PRODUCT_QUANTITY = "productQuantity";
    public static final String COL_PRODUCT_DESCRIPTION = "productDescription";
    public static final String COL_PRODUCT_IMAGE_URI = "productImageUri";

    public DataCenter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create registration table
        String CREATE_REGISTER_TABLE = "CREATE TABLE " + TABLE_REGISTER + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT, " +
                COL_MOBILE + " TEXT, " +
                COL_ADDRESS + " TEXT)";
        db.execSQL(CREATE_REGISTER_TABLE);

        // Create products table
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_PRODUCT_NAME + " TEXT, " +
                COL_PRODUCT_PRICE + " REAL, " +
                COL_PRODUCT_QUANTITY + " INTEGER, " +
                COL_PRODUCT_DESCRIPTION + " TEXT, " +
                COL_PRODUCT_IMAGE_URI + " BLOB)";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        // Create tables again
        onCreate(db);
    }

    // Method to insert a user into registration table
    public boolean insertUser(String username, String email, String password, String mobile, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);
        values.put(COL_MOBILE, mobile);
        values.put(COL_ADDRESS, address);
        long result = db.insert(TABLE_REGISTER, null, values);
        db.close();
        return result != -1;
    }

    // Method to check if a user exists by username and password
    public boolean checkUserByUsername(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_REGISTER + " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    // Method to insert a product into products table
    public boolean insertProduct(String name, double price, int quantity, String description, byte[] imageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, name);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_QUANTITY, quantity);
        values.put(COL_PRODUCT_DESCRIPTION, description);
        values.put(COL_PRODUCT_IMAGE_URI, imageByteArray);
        long result = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        if (result == -1) {
            Log.e("DataCenter", "Error inserting product into database");
        } else {
            Log.i("DataCenter", "Product inserted successfully with ID: " + result);
        }
        return result != -1;
    }
    public Cursor getAllProducts() {
        SQLiteDatabase dbc = this.getReadableDatabase();
        return dbc.rawQuery("SELECT * FROM " + TABLE_PRODUCTS, null);
    }
    public Cursor getProductByName(String productName) {
        SQLiteDatabase dbc = this.getReadableDatabase();
        return dbc.rawQuery("SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COL_PRODUCT_NAME + " = ?", new String[]{productName});

    }
    public boolean updateProduct(int productId, String productName, double price, int quantity, String productDescription, byte[] productImageByteArray) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_NAME, productName);
        values.put(COL_PRODUCT_PRICE, price);
        values.put(COL_PRODUCT_QUANTITY, quantity);
        values.put(COL_PRODUCT_DESCRIPTION, productDescription);
        values.put(COL_PRODUCT_IMAGE_URI, productImageByteArray);

        int rowsAffected = db.update(TABLE_PRODUCTS, values, COL_ID + " = ?", new String[]{String.valueOf(productId)});
        return rowsAffected > 0;
    }
    public int deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COL_PRODUCT_NAME + "=?";
        String[] whereArgs = {productName};
        int deletedRows = db.delete(TABLE_PRODUCTS, whereClause, whereArgs);
        db.close();
        return deletedRows;
    }
    public Cursor getUserByName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_REGISTER + " WHERE " + COL_USERNAME + " = ?";
        return db.rawQuery(query, new String[]{username});
    }




}
