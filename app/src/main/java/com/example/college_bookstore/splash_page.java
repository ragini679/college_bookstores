package com.example.college_bookstore;

import static android.os.Build.VERSION_CODES.M;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class splash_page extends AppCompatActivity {
     FirebaseAuth mauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mauth=FirebaseAuth.getInstance();
        FirebaseUser currentuser=mauth.getCurrentUser();
        if(currentuser!=null) {
            Intent intent = new Intent(this, MainActivity.class);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
        else{
            Intent splash = new Intent(this, login_page.class);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(splash);
                    finish();
                }
            }, 3000);
        }
    }
}