package com.comp2100.todolist;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationManagerCompat;

class AlertReceive extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent){
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        NotificationHelper notificationHelper = new NotificationHelper();
        notificationHelper.setBuilder(context,"this is the time","!!!!!!");
        notificationManagerCompat.notify(1,notificationHelper.getNotification());
    }
}
