package com.example.college_bookstore;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.function.Consumer;

public class uploadedbookadapter extends RecyclerView.Adapter<uploadedbookadapter.viewHolder> {
    private List<Book> bookList;
    private Consumer<Book> onDeleteClick;
    Context context;
    public uploadedbookadapter(Context context,List<Book> bookList,Consumer<Book> onDeleteClick){
        this.context=context;
        this.bookList=bookList;
        this.onDeleteClick=onDeleteClick;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.str_uploadedbook,parent,false);
        return new  viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Book book=bookList.get(position);
        holder.pricetextview.setText("Price: "+String.valueOf(book.getPrice()));
        holder.collegetextview.setText("College: "+book.getcollegeName());
        holder.booktextview.setText("Book: "+book.getbookName());
        holder.authortextview.setText("Author: "+book.getauthorName());
        holder.sellernametextview.setText("Seller: "+book.getSellerName());
        holder.sellercontecttextview.setText("Number: "+book.getphoneNumber());
        Glide.with(context).load(book.getImageUrl()).into(holder.bookimageview);
        holder.deleteButton.setOnClickListener(v ->{
            Log.d("DeleteButton", "Delete button clicked for book: " + book.getbookName());
                onDeleteClick.accept(book);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView bookimageview;
        TextView pricetextview, collegetextview,booktextview,authortextview,sellernametextview,sellercontecttextview;
        Button deleteButton;
     public viewHolder(@NonNull View itemView) {
         super(itemView);
         bookimageview=itemView.findViewById(R.id.bookimageview);
         pricetextview=itemView.findViewById(R.id.pricetextview);
         collegetextview=itemView.findViewById(R.id.collegetextview);
         booktextview=itemView.findViewById(R.id.booktextview);
         authortextview=itemView.findViewById(R.id.authortextview);
         sellernametextview=itemView.findViewById(R.id.sellernametextview);
         sellercontecttextview=itemView.findViewById(R.id.sellercontecttextview);
         deleteButton=itemView.findViewById(R.id.deletebtn);
     }
 }

}