package com.example.tourplanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(context,"a").
                setSmallIcon(R.drawable.e).
                setContentTitle("Reminder test").
                setContentText("hello world").
                setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager= NotificationManagerCompat.from(context);
        notificationManager.notify(200,builder.build());

    }

}
