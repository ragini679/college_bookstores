package com.example.college_bookstore;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

public class SearchBooks {
    private FirebaseFirestore dp;
    private Activity activity;

    // Constructor where you pass the activity context
    public SearchBooks(Activity activity) {
        this.activity = activity;
        // Initialize FirebaseFirestore
        this.dp = FirebaseFirestore.getInstance();
    }

    public void searchbooks(String buyerInputCollege, String buyerInputBookName, OnSearchCompleteListener listener) {
        Query query = dp.collection("books");
        if (!buyerInputCollege.isEmpty()) {
            query = query.whereEqualTo("collegeName", buyerInputCollege);
        }

        if (!buyerInputBookName.isEmpty()) {
            query = query.whereEqualTo("bookName", buyerInputBookName);
        }

        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<Book> bookList = new ArrayList<>();
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    Book book = document.toObject(Book.class);
                    bookList.add(book);
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onSearchComplete(bookList);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onError(e);
                    }
                });
            }
        });
    }
    public interface OnSearchCompleteListener {
        void onSearchComplete(List<Book> bookList); // Explicitly type the list
        void onError(Exception e);
    }
}