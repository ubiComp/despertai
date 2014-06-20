package com.great.despertai.util;

import java.io.Serializable;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.great.despertai.model.vo.Alarm;
 
public class ReceptorBoot extends BroadcastReceiver {
 
	private static Alarm alarm;
	
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	alarm = (Alarm) intent.getExtras().getSerializable(
				"alarmToReceiver");
    	
    	configureAlarm(context);
    	
    }
    
    public static void configureAlarm(Context context) {
		/*
		 * Neste metodo, obtemos o AlarmManager, e obtemos as preferencias do
		 * usuario para o alarme (se existirem), e a montamos em um objeto do
		 * tipo Calendar. Caso alarme seja anterior ao horario atual,
		 * adicionamos um dia a ele e configuramos para repeti-lo diariamente
		 */
    	
    	Log.i("BroadcastReceiver", "Setting alarm..");
    	
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Calendar cal = Calendar.getInstance();
        
        // Setar horario do alarme
        
        Log.d("HOUR", ""+alarm.getHours());
        Log.d("HOUR", ""+alarm.getMinutes());
        
        cal.set(Calendar.HOUR_OF_DAY, alarm.getHours());
        cal.set(Calendar.MINUTE, alarm.getMinutes());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        Log.i("BroadcastReceiver", "Current alarm was set.");
        
        // Definir quantos dias se repete
        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
        
        // Definir os intervalos de cada hora
        manager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, getPendingIntent(context));
        
        alarm = null;
      
    }
     
    public static void cancelAlarm(Context context) {
		/*
		 * Cancelamos o alarme vinculado ao contexto, obtendo o AlarmManager e
		 * obtendo um objeto PendingIntent (como se fosse uma tarefa pendente)
		 * com o metodo obterIntentPendente().
		 */
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        manager.cancel(getPendingIntent(context));
    } 
     
    private static PendingIntent getPendingIntent(Context context) {
    	
    	Intent intent = new Intent(context, ReceptorAlarme.class);
		Bundle bundle = new Bundle();
		bundle.putSerializable("alarmToReceiverAlarm", (Serializable) alarm);
		intent.putExtras(bundle);
		
        return PendingIntent.getBroadcast(context, 0, intent, 0);
        
    }
}
