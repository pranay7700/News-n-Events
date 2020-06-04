package com.vaagdevi.newsnevents;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class Notifications extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getBody());
    }

    public void showNotification(String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, Notification.class), 0);
        android.app.Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.notification_image)
                .setContentTitle("News n Events")
                .setContentText(message)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

}
