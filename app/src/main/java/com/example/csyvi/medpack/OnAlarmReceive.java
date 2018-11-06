package com.example.csyvi.medpack;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class OnAlarmReceive extends BroadcastReceiver {
    Intent i;
    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.d("Medication", "BroadcastReceiver, in onReceive:");

        // Start the MainActivity
        i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(context,"Please consume your medicine", Toast.LENGTH_LONG).show();
        context.startActivity(i);
    }
}
