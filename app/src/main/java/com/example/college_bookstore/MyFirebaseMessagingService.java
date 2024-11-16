package com.example.college_bookstore;

import static com.example.college_bookstore.MainActivity.CHANNEL_ID;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remotemessage) {
           if(remotemessage.getNotification()!=null){
               showNotification(remotemessage.getNotification().getTitle(),remotemessage.getNotification().getBody());
           }
    }
    public void showNotification(String title,String body){
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.img_1)
                .setContentTitle(title)                    // Notification title (passed in)
                .setContentText(body)                      // Notification content (passed in)
                .setAutoCancel(true)                       // Notification will disappear when clicked
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
