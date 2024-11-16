package com.example.college_bookstore;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class book_list extends AppCompatActivity {
private RecyclerView booksRecyclerView;
private TextView noBooksTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
       booksRecyclerView=findViewById(R.id.booksRecyclerView);
       noBooksTextView=findViewById(R.id.noBooksTextView);
        Toolbar toolbar=findViewById(R.id.booklist_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       ArrayList<Book> bookList=getIntent().getParcelableArrayListExtra("bookList");
        if (bookList != null && !bookList.isEmpty()) {
            booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            BookAdapter adapter = new BookAdapter(this, bookList);
            booksRecyclerView.setAdapter(adapter);
        } else {
            noBooksTextView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No books to display.", Toast.LENGTH_SHORT).show();
        }

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