package com.comp2100.todolist;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationManagerCompat;

public class App extends Activity {
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        creatNotificationChannels();


    }

    private void creatNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.BASE) {

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"Channel 1", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("This is channel 1");
            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID,"Channel 2",NotificationManager.IMPORTANCE_DEFAULT);
            channel2.setDescription("This is channel 2");
            notificationManager.createNotificationChannel(channel1);
            notificationManager.createNotificationChannel(channel2);
        }
    }


}
