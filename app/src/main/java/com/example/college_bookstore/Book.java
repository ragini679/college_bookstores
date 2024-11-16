package com.example.college_bookstore;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String bookName;
    private String authorName;
    private String collegeName;
    private double price;
    private String imageUrl;
    private String sellerName;
    private String phoneNumber;
    private String userId;
    private String id;
    public Book(){}
    public Book( String bookName, String authorName, String collegeName,double price, String imageUrl,
                 String sellerName, String sellerPhoneNumber,String userId) {
        this.bookName=bookName;
        this.authorName = authorName;
        this.collegeName = collegeName;
        this.price = price;
        this.imageUrl = imageUrl;
        this.sellerName = sellerName;
        this.phoneNumber = sellerPhoneNumber;
        this.userId=userId;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getbookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getauthorName() {
        return authorName;
    }

    public void setauthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getcollegeName() {
        return collegeName;
    }

    public void setcollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getphoneNumber() {
        return phoneNumber;
    }

    public void setphoneNumber(String sellerPhoneNumber) {
        this.phoneNumber = sellerPhoneNumber;
    }

    public String getuserId() {
        return userId;
    }
    public void setuserId(String userId) {
        this.userId = userId;
    }

    // Parcelable implementation
    protected Book(Parcel in) {
        bookName = in.readString();
        authorName = in.readString();
        collegeName = in.readString();
        price = in.readDouble();
        imageUrl = in.readString();
        sellerName = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
        dest.writeString(authorName);
        dest.writeString(collegeName);
        dest.writeDouble(price);
        dest.writeString(imageUrl);
        dest.writeString(sellerName);
        dest.writeString(phoneNumber);
    }



}
