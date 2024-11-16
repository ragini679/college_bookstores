package com.example.college_bookstore;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
Button btn1;
Button btn2;
    FirebaseAuth mauth;
    static final String CHANNEL_ID = "default_channel_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        mauth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("ragini","hello");
        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Map<String, Object> userActivity = new HashMap<>();
        userActivity.put("lastActive", FieldValue.serverTimestamp());
        db.collection("users").document(userId).set(userActivity , SetOptions.merge())
                .addOnSuccessListener(aVoid -> Log.d("UserActivity", "Last active time updated"))
                .addOnFailureListener(e -> {
                    Log.w("UserActivity", "Error updating last active time", e);
                    Toast.makeText(this, "Failed to update last active time: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        btn1.setOnClickListener(new View.OnClickListener(){

      @Override
      public void onClick(View v) {
       Intent intend=new Intent(MainActivity.this,buyer_details_page.class);
       startActivity(intend);
      }
  });
        btn2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intend=new Intent(MainActivity.this, seller_details.class);
                startActivity(intend);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int itemid=menuItem.getItemId();
        if(itemid==R.id.action_share){
            shareAppLink();
            return true;
        }
        if(itemid==R.id.logoutButton){
            mauth.signOut();
            Intent intent = new Intent(MainActivity.this, login_page.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(itemid==R.id.uploadedbtn){
            Intent intent = new Intent(MainActivity.this,uploadedbook_list.class);
            startActivity(intent);
            return true;
        }
        if (itemid == android.R.id.home) {
            // Handle the back button event
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }
    public void shareAppLink(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String sharemassege=  "Check out this app: " + getString(R.string.app_link);
        shareIntent.putExtra(Intent.EXTRA_TEXT,sharemassege);
        startActivity(Intent.createChooser(shareIntent,"Share App"));
    }
    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel(CHANNEL_ID,"Default Channel", importance);
            channel.setDescription("This is the default notification channel");
            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}