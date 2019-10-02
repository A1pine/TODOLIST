package com.comp2100.todolist;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

class AlertReceive {
    public void onReceive(Context context, Intent intent){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationManagerCompat.notify(1,notificationHelper.getBuilder().build());
    }
}
