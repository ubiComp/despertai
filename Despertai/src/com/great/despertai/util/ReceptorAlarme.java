package com.great.despertai.util;

import java.io.Serializable;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.great.despertai.model.vo.Alarm;
import com.great.despertai.view.AlarmeActivity;

public class ReceptorAlarme extends BroadcastReceiver {

	private Alarm alarm;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.i("BroadcastReceiver", "Starting alarm..");
		
		alarm = (Alarm) intent.getExtras().getSerializable(
				"alarmToReceiverAlarm");
		
		Intent i = new Intent(context, AlarmeActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Bundle bundle = new Bundle();
		bundle.putSerializable("alarmToSound", (Serializable) alarm);
		i.putExtras(bundle);
		context.startActivity(i);
		
		alarm = null;
		
	}

}