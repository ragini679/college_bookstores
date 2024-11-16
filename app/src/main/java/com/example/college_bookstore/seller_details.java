package com.example.college_bookstore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class seller_details extends AppCompatActivity {
    private Uri imageUri;
    private ImageView bookimage;
    private StorageReference storageReference;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_details);
        FirebaseApp.initializeApp(this);
        AutoCompleteTextView collegename = findViewById(R.id.collegename);
        AutoCompleteTextView bookname = findViewById(R.id.bookname);
        AutoCompleteTextView authorname = findViewById(R.id.authorname);
        AutoCompleteTextView price = findViewById(R.id.price);
        AutoCompleteTextView sellername = findViewById(R.id.sellername);
        AutoCompleteTextView phonenumber = findViewById(R.id.phonenumber);
        bookimage = findViewById(R.id.book_image);
        Button upload_button = findViewById(R.id.upload_button);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        price.addTextChangedListener(new InputValidation(price, "price"));
        collegename.addTextChangedListener(new InputValidation(collegename, "Latter"));
        bookname.addTextChangedListener(new InputValidation(bookname, "Latter"));
        authorname.addTextChangedListener(new InputValidation(authorname, "Latter"));
        sellername.addTextChangedListener(new InputValidation(sellername, "Latter"));
        phonenumber.addTextChangedListener(new InputValidation(phonenumber, "price"));
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        bookimage.setOnClickListener(v -> openFileChooser());
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri = result.getData().getData();
                        bookimage.setImageURI(imageUri);
                    }
                });
        upload_button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(collegename.getText().toString()) ||
                    TextUtils.isEmpty(bookname.getText().toString()) ||
                    TextUtils.isEmpty(authorname.getText().toString()) ||
                    TextUtils.isEmpty(price.getText().toString()) ||
                    TextUtils.isEmpty(sellername.getText().toString()) ||
                    TextUtils.isEmpty(phonenumber.getText().toString())) {

                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show();
            }
            // Check if imageUri is not null
            else if (imageUri == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
            // Check if storage reference is valid
            else if (storageReference == null) {
                Toast.makeText(this, "Storage reference is not available", Toast.LENGTH_SHORT).show();
            }
            // If all fields and image are provided, proceed to upload
            else {
                uploadImageToFirebase(
                        collegename.getText().toString().trim(),
                        bookname.getText().toString().trim(),
                        authorname.getText().toString(),
                        price.getText().toString(),
                        sellername.getText().toString(),
                        phonenumber.getText().toString()
                );
            }
        });
    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(intent);
    }

    private void uploadImageToFirebase(String collegename, String bookName, String authorName, String price, String sellerName, String phoneNumber) {
        String fileName = UUID.randomUUID().toString();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference fileReference = storageReference.child(fileName + ".jpg");
        fileReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Store the image URL and other book details in Firestore
                    storeBookDetails(collegename ,bookName, authorName, price, sellerName, phoneNumber, uri.toString(),userId);
                }))
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void storeBookDetails(  String collegename,String bookName, String authorName, String price, String sellerName, String phoneNumber, String imageUrl,String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (imageUrl == null || imageUrl.isEmpty()) {
            Toast.makeText(this, "Image URL is missing!", Toast.LENGTH_SHORT).show();
            return;  // Do not proceed with Firestore upload
        }
        Map<String, Object> book = new HashMap<>();
        book.put("collegeName", collegename);
        book.put("bookName", bookName);
        book.put("authorName", authorName);
        book.put("price", Double.parseDouble(price));
        book.put("sellerName", sellerName);
        book.put("phoneNumber", phoneNumber);
        book.put("imageUrl", imageUrl);
        book.put("userId", userId);
        db.collection("books").add(book)
                .addOnSuccessListener(documentReference -> Toast.makeText(this, "Book uploaded successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to upload book details: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getOnBackPressedDispatcher().onBackPressed();  // Navigate to the previous activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}