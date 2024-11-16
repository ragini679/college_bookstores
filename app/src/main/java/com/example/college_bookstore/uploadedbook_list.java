package com.example.college_bookstore;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class uploadedbook_list extends AppCompatActivity {
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private uploadedbookadapter adapter;
    private ImageView noBooksImageView;
    private TextView noBooksTextView;
    private List<Book> uploadedBooks = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadedbook_list);
        recyclerView=findViewById(R.id.booklist);
        noBooksImageView = findViewById(R.id.no_books_image); // Initialize ImageView
        noBooksTextView = findViewById(R.id.no_books_text);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = FirebaseFirestore.getInstance();
        fetchUploadedBooks();
        if (isDarkMode()) {
            noBooksImageView.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_IN);
        } else {
            // Remove any color filters in light mode
            noBooksImageView.clearColorFilter();
        }
    }
    private void fetchUploadedBooks(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db.collection("books")
                .whereEqualTo("userId",userId)
                .get()
                .addOnCompleteListener(task->{
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document: task.getResult()){
                            Book book=document.toObject(Book.class);
                            book.setId(document.getId());
                            uploadedBooks.add(book);
                        }
                        if (uploadedBooks.isEmpty()) {
                            // No books found, show the image and message
                            noBooksImageView.setVisibility(View.VISIBLE);
                            noBooksTextView.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            // Books found, hide the image and message
                            noBooksImageView.setVisibility(View.GONE);
                            noBooksTextView.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE); // Show RecyclerView
                            adapter = new uploadedbookadapter(this, uploadedBooks, this::deleteBook);
                            recyclerView.setAdapter(adapter);
                        }
                        adapter=new uploadedbookadapter(this,uploadedBooks,this::deleteBook);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(this, "No books uploaded by this user.", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public boolean isDarkMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
        private void deleteBook(Book book) {
        String documentId = book.getId(); // Retrieve the stored document ID
        if (documentId != null) {
            db.collection("books").document(documentId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Log.d("FirestoreDelete", "Successfully deleted book with ID: " + documentId);
                        uploadedBooks.remove(book);
                        adapter.notifyDataSetChanged(); // Notify adapter about the data change
                        Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirestoreDelete", "Failed to delete book", e);
                        Toast.makeText(this, "Failed to delete book", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Log.e("FirestoreDelete", "Document ID is null");
        }
    }
}