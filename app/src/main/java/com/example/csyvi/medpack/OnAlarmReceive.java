package com.example.csyvi.medpack;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
