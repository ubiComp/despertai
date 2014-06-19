package com.great.despertai.view;


import com.great.despertai.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
 
public class AlarmeActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
       // showNotification2();
    }
    
    
	public void showNotification2(){
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

       int icon = R.drawable.ic_launcher_clock;        // icon from resources
        CharSequence tickerText = "Alarme"; //
        long when = System.currentTimeMillis();         // notification time
        Context context = getApplicationContext();      // application Context
        CharSequence contentTitle = "Despertaí";  // message title
        CharSequence contentText = "Acorda!!";      // message text

        Intent notificationIntent = new Intent(this, AlarmSettingsActivity.class);
//        Intent notificationIntent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // the next two lines initialize the Notification, using the configurations above
        Notification notification = new Notification(icon,tickerText,when);
        //notification.when=3000;
//        notification.defaults |= Notification.DEFAULT_SOUND;
        //notification.defaults |= Notification.DEFAULT_VIBRATE;
        //notification.sound = Uri.parse("android.resource://" + getPackageName() + "/R.raw.notificationsound");
        
        notification.sound = Uri.parse("content://media/internal/audio/media/43");
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
        mNotificationManager.notify(1, notification);
        
}
}