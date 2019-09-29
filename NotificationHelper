package com.comp2100.todolist;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    NotificationCompat.Builder builder;
    public NotificationHelper(Context context){
        builder = new NotificationCompat.Builder(context, App.CHANNEL_1_ID).setSmallIcon(R.drawable.ic_star)
                .setContentTitle("My notification").setContentText("Alarm!").setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
    public NotificationCompat.Builder getBuilder(){
        return builder;
    }
}
