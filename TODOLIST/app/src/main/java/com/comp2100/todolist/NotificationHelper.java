package com.comp2100.todolist;

import android.content.Context;
import android.app.Notification;
import androidx.core.app.NotificationCompat;


public class NotificationHelper {
    NotificationCompat.Builder builder;


    void setBuilder (Context context, String title, String description){
        builder = new NotificationCompat.Builder(context, App.CHANNEL_1_ID)
                .setSmallIcon(R.drawable. ic_priority_high_black_24dp)
                .setContentTitle("this is notification")
                .setContentText("ok...")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("fine..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
    Notification getNotification(){
       return builder.build();

    }

}
