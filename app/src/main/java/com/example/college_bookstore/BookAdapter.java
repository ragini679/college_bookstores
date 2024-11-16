package com.example.college_bookstore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    Context context;
    List<Book> booklist;



    public BookAdapter(Context context, List<Book> booklist) {
        this.context=context;
        this.booklist=booklist;
    }


    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.str_of_booklist,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
     Book book=booklist.get(position);
        holder.pricetextview.setText("Price: "+String.valueOf(book.getPrice()));
     holder.collegetextview.setText("College: "+book.getcollegeName());
     holder.booktextview.setText("Book: "+book.getbookName());
     holder.authortextview.setText("Author: "+book.getauthorName());
     holder.sellernametextview.setText("Seller: "+book.getSellerName());
     holder.sellercontecttextview.setText("Number: "+book.getphoneNumber());
        Glide.with(context).load(book.getImageUrl()).into(holder.bookimageview);
    }

    @Override
    public int getItemCount() {
        return booklist.size();
    }

    public void updateData(List<Book> newBooks) {
        this.booklist.clear();
        this.booklist.addAll(newBooks);
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
          ImageView bookimageview;
          TextView  pricetextview, collegetextview,booktextview,authortextview,sellernametextview,sellercontecttextview;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookimageview=itemView.findViewById(R.id.bookimageview);
            pricetextview=itemView.findViewById(R.id.pricetextview);
            collegetextview=itemView.findViewById(R.id.collegetextview);
            booktextview=itemView.findViewById(R.id.booktextview);
            authortextview=itemView.findViewById(R.id.authortextview);
            sellernametextview=itemView.findViewById(R.id.sellernametextview);
            sellercontecttextview=itemView.findViewById(R.id.sellercontecttextview);
        }
    }
}
