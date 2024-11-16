package com.example.college_bookstore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;
import android.view.MenuItem;

public class buyer_details_page extends AppCompatActivity {
       AutoCompleteTextView collegename;
    AutoCompleteTextView branchname;
    AutoCompleteTextView bookname;
    AutoCompleteTextView cityname;
    Button searchbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_details_page);
        Toolbar toolbar=findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);
      if(getSupportActionBar()!=null){
          getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          getSupportActionBar().setDisplayShowHomeEnabled(true);
      }
     collegename=findViewById(R.id.col_name);
        branchname=findViewById(R.id.branch_name);
        bookname=findViewById(R.id.Book_name);
        searchbtn=findViewById(R.id.searchbtn);
     ArrayList<String> collegeList= collegedata.getcollegelist();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,collegeList);
      collegename.setAdapter(adapter);
      collegename.setThreshold(1);
        ArrayList<String> branchlist= branch_data.getbranchlist();
        ArrayAdapter<String> branch_adapter=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,branchlist);
        branchname.setAdapter(branch_adapter);
        branchname.setThreshold(1);
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buyerInputCollege = collegename.getText().toString().trim();
                String buyerInputBookName = bookname.getText().toString().trim() ;
                ProgressDialog progressDialog = new ProgressDialog(buyer_details_page.this);
                progressDialog.setMessage("Searching...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                SearchBooks searchbooks=new SearchBooks(buyer_details_page.this);
               searchbooks.searchbooks(buyerInputCollege,buyerInputBookName,new SearchBooks.OnSearchCompleteListener(){

                   @Override
                   public void onSearchComplete(List<Book> bookList) {
                       progressDialog.dismiss();
                       if (bookList.isEmpty()) {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(buyer_details_page.this, "No books found for the given criteria.", Toast.LENGTH_SHORT).show();
                               }
                           });
                       }
                      else{ runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Intent intent = new Intent(buyer_details_page.this, book_list.class);
                                   intent.putParcelableArrayListExtra("bookList", new ArrayList<>(bookList));
                                   startActivity(intent);
                               }
                           });
                       }
                   }

                   @Override
                   public void onError(Exception e) {
                       progressDialog.dismiss();
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               Log.d("Firestore", "Error getting documents: ", e);
                               Toast.makeText(buyer_details_page.this,
                                       "Error retrieving books. Please try again.", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               });
            }
        });
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