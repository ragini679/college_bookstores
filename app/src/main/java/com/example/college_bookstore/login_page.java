package com.example.college_bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.college_bookstore.databinding.ActivityLoginPageBinding;
import com.example.college_bookstore.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login_page extends AppCompatActivity {
    ActivityLoginPageBinding binding;
    String loginemailid,loginpassword;
    FirebaseAuth mauth;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();
        binding.loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              loginemailid=  binding.loginemailid.getText().toString().trim();
              loginpassword=  binding.loginpassword.getText().toString();
                if(loginemailid.isEmpty() || loginpassword.isEmpty()){
                    Toast.makeText(login_page.this, "please fill all details", Toast.LENGTH_SHORT).show();
                }
                else{
                   mauth.signInWithEmailAndPassword(loginemailid,loginpassword)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if(task.isSuccessful()){
                                       Toast.makeText(login_page.this, "login Successful", Toast.LENGTH_SHORT).show();
                                       Intent logintomain=new Intent(login_page.this,MainActivity.class);
                                       startActivity(logintomain);
                                       finish();
                                   }
                                   else{
                                       Toast.makeText(login_page.this, "Login Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });

                }
            }
        });
       binding.signup.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(login_page.this,sign_up.class);
               startActivity(intent);
               finish();
           }
       });
    }
}