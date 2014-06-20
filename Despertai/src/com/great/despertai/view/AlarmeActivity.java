package com.great.despertai.view;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.great.despertai.R;
import com.great.despertai.model.vo.Alarm;
 
public class AlarmeActivity extends Activity implements OnClickListener {
 
	private Alarm alarm;
	
	private TextView tvTitle, tvHour;
	private Button btStop;
	
	private Notification notification;
	private NotificationManager mNotificationManager;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_layout);
        
        alarm = (Alarm) getIntent().getExtras().getSerializable(
				"alarmToSound");
        
        tvTitle = (TextView) findViewById(R.id.alarm_tv_title);
        tvHour = (TextView) findViewById(R.id.alarm_tv_hour);
        btStop = (Button) findViewById(R.id.alarm_bt_stop);
        btStop.setOnClickListener(this);
        
        tvTitle.setText(alarm.getTitle());
        tvHour.setText(alarm.getHourStr());
        
        showNotification();
        
        alarm = null;
    }
    
	public void showNotification(){
        String ns = Context.NOTIFICATION_SERVICE;
        mNotificationManager = (NotificationManager) getSystemService(ns);

        notification = new Notification();
        notification.sound = Uri.parse(alarm.getSound());

        mNotificationManager.notify(1, notification);
}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.alarm_bt_stop:
			actionListenerStopButton();
			break;

		default:
			break;
		}
		
	}

	private void actionListenerStopButton() {
		Log.d("BUTTON", "Stop button selected.");
		mNotificationManager.cancel(1);
		finish();
	}
    
}