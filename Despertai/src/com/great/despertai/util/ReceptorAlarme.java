package com.great.despertai.util;

import com.great.despertai.view.AlarmSettingsActivity;
import com.great.despertai.view.AlarmeActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
 
public class ReceptorAlarme extends BroadcastReceiver {
 
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AlarmeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
      
    }
    

}