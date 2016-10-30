package com.jitesh.filtr;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by jitesh on 19/10/16.
 */


public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)){  //battery is low!
            Log.v("myReceiver", "battery low");
            Toast.makeText(context, "Battery Low! Please connect to a power source", Toast.LENGTH_LONG).show();
        }
    }
}
