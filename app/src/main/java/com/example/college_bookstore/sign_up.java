package com.example.college_bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.college_bookstore.databinding.ActivityMainBinding;
import com.example.college_bookstore.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up extends AppCompatActivity {
    ActivitySignUpBinding binding;
    private FirebaseAuth mauth;
    String username,emailid,password,repeatpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth=FirebaseAuth.getInstance();
        binding.signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(sign_up.this,login_page.class);
                startActivity(intent);
                finish();
            }
        });
       binding.signupbtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               username=binding.username.getText().toString();
               emailid=binding.emailid.getText().toString().trim();
               password=binding.password.getText().toString();
               repeatpassword=binding.repeatpassword.getText().toString();

               if(username.isEmpty() || emailid.isEmpty() || password.isEmpty() || repeatpassword.isEmpty()){
                   Toast.makeText(sign_up.this, "Please Fill All The Ditails", Toast.LENGTH_SHORT).show();
               }
               else if(!password.equals(repeatpassword)){
                   Toast.makeText(sign_up.this, "Repeat Pasword Must Be Same", Toast.LENGTH_SHORT).show();
               }
               else{
                mauth.createUserWithEmailAndPassword(emailid,password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(sign_up.this, "sign-up Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(sign_up.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(sign_up.this, "Sign-Up Failed"+errorMessage,Toast.LENGTH_SHORT).show();
                            }
                            }
                        });
               }
           }
       });
    }
}