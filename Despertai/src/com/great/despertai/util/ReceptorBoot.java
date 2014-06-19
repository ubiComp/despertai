package com.great.despertai.util;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
 
public class ReceptorBoot extends BroadcastReceiver {
 
    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	configurarAlarme(context);
    	
    	
    }
    
    public static void configurarAlarme(Context contexto) {
    	Log.i("teste", "Chegou ?");
    	/* Neste método, obtemos o AlarmManager, e obtemos as preferências 
    	 * do usuário para o alarme (se existirem), 
    	 * e a montamos em um objeto do tipo Calendar. 
    	 * Caso alarme seja anterior ao horário atual,
    	 *  adicionamos um dia a ele e configuramos para 
    	 *  repeti-lo diariamente*/
        AlarmManager gerenciador = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        
        
        //Colocar um loop para pegar as horas do banco e setar os eventos
        cal.set(Calendar.HOUR_OF_DAY, 18);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        if (cal.getTimeInMillis() < System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
     //Definir os intervalos de cada hora
        gerenciador.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, obterIntentPendente(contexto));
    }
     
    public static void cancelarAlarme(Context contexto) {
    	/*cancelamos o alarme vinculado ao contexto,
    	 *  obtendo o AlarmManager e obtendo um objeto
    	 *   PendingIntent (como se fosse uma tarefa pendente)
    	 *    com o método obterIntentPendente().*/
        AlarmManager gerenciador = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
        gerenciador.cancel(obterIntentPendente(contexto));
    } 
     
    private static PendingIntent obterIntentPendente(Context contexto) {
        Intent i = new Intent(contexto, ReceptorAlarme.class);
        return PendingIntent.getBroadcast(contexto, 0, i, 0);
    }
}

