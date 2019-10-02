package com.comp2100.todolist;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

public class App extends Application {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    @Override
    public void onCreate(){
        super.onCreate();
        creatNotificationChannels();


    }

    private void creatNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("This is channel 1");
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID,"Channel 2",NotificationManager.IMPORTANCE_DEFAULT);
            channel2.setDescription("This is channel 2");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
        }
    }


}
