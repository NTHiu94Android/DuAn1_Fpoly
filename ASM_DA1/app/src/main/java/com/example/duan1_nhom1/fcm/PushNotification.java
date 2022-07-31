package com.example.duan1_nhom1.fcm;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class PushNotification extends Application {
    public static final String CHANNEL_ID = "HEADS_UP_NOTIFICATION";
    @Override
    public void onCreate() {
        super.onCreate();
        createPusnotification();
    }

    private void createPusnotification() {
        NotificationChannel channel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
