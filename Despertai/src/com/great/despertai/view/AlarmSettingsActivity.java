package com.great.despertai.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.great.despertai.R;
import com.great.despertai.adapter.SettingsPrefAdapter;
import com.great.despertai.model.vo.Alarm;

public class AlarmSettingsActivity extends FragmentActivity {

	private Alarm currentAlarm;
	private TimePicker tpHour;
	private ToggleButton tbSunday,tbMonday, tbTuesday, tbWednesday, tbThursday, tbFriday, tbSaturday;

	private SharedPreferences preferences;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alarm_settings_layout);
		SettingsPrefAdapter fragment2 = new SettingsPrefAdapter();
		getFragmentManager().beginTransaction()
				.replace(R.id.content, fragment2).commit();

		currentAlarm = null;
		currentAlarm = (Alarm) getIntent().getExtras().getSerializable(
				"currentAlarm");
		
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preferences.edit();
		
		// instantiate the objects in the layout file
		tpHour = (TimePicker) findViewById(R.id.alarmsettings_time_picker);
//		tpHour.setIs24HourView(true);
		tbSunday = (ToggleButton) findViewById(R.id.alarmsettings_tb_sunday);
		tbMonday = (ToggleButton) findViewById(R.id.alarmsettings_tb_monday);
		tbTuesday = (ToggleButton) findViewById(R.id.alarmsettings_tb_tuesday);
		tbWednesday = (ToggleButton) findViewById(R.id.alarmsettings_tb_wednesday);
		tbThursday = (ToggleButton) findViewById(R.id.alarmsettings_tb_thursday);
		tbFriday = (ToggleButton) findViewById(R.id.alarmsettings_tb_friday);
		tbSaturday = (ToggleButton) findViewById(R.id.alarmsettings_tb_saturday);
		
		// set values in the preferences from current alarm (item clicked)
		editor.putString(getString(R.string.alarmsettings_name_key), currentAlarm.getTitle());
		editor.putString(getString(R.string.alarmsettings_sound_key), currentAlarm.getSound());
		editor.putInt(getString(R.string.alarmsettings_volume_key), currentAlarm.getVolume());
		editor.putString(getString(R.string.alarmsettings_snooze_key), ""+currentAlarm.getSnoozeTime());
		editor.putString(getString(R.string.alarmsettings_shutdown_mode_key), ""+currentAlarm.getShutdownMode());
		editor.commit();
		
//		tpHour.setCurrentHour(currentAlarm.getHours()); // tratar string de horas
//		tpHour.setCurrentMinute(currentAlarm.getMinutes());
		
		// tratar daysweeklist
//		tbSunday.setChecked(currentAlarm.getDaysWeekList().get(0)==1 ? true : false);
//		tbMonday.setChecked(currentAlarm.getDaysWeekList().get(1)==1 ? true : false);
//		tbTuesday.setChecked(currentAlarm.getDaysWeekList().get(2)==1 ? true : false);
//		tbWednesday.setChecked(currentAlarm.getDaysWeekList().get(3)==1 ? true : false);
//		tbThursday.setChecked(currentAlarm.getDaysWeekList().get(4)==1 ? true : false);
//		tbFriday.setChecked(currentAlarm.getDaysWeekList().get(5)==1 ? true : false);
//		tbSaturday.setChecked(currentAlarm.getDaysWeekList().get(6)==1 ? true : false);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.alarm_settings_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.alarmsttings_optmenu_save:
			actionListenerSaveButton();
			return true;
		case R.id.alarmsttings_optmenu_cancel:
			actionListenerCancelButton();
			return true;
		case R.id.alarmsttings_optmenu_remove:
			actionListenerRemoveButton();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void actionListenerSaveButton() {
		Log.d("BUTTON", "SaveButton clicked");
		
		// set data in the current alarm from objects layout preferences
		Log.d("TESTE-nome", preferences.getString(getString(R.string.alarmsettings_name_key), ""));
		Log.d("TESTE-hora", ""+Integer.toString(tpHour.getCurrentHour())+":"+Integer.toString(tpHour.getCurrentMinute()));
		Log.d("TESTE-som", ""+preferences.getString(getString(R.string.alarmsettings_sound_key), ""));
		Log.d("TESTE-volume", ""+preferences.getInt(getString(R.string.alarmsettings_volume_key), 0));
		Log.d("TESTE-soneca", ""+preferences.getString(getString(R.string.alarmsettings_snooze_key), ""));
		Log.d("TESTE-sdmode", ""+preferences.getString(getString(R.string.alarmsettings_shutdown_mode_key), ""));
		Log.d("TESTE-sunday", ""+tbSunday.isChecked());
		Log.d("TESTE-monday", ""+tbMonday.isChecked());
		Log.d("TESTE-tuesday", ""+tbTuesday.isChecked());
		Log.d("TESTE-wedn", ""+tbWednesday.isChecked());
		Log.d("TESTE-thursday", ""+tbThursday.isChecked());
		Log.d("TESTE-friday", ""+tbFriday.isChecked());
		Log.d("TESTE-saturday", ""+tbSaturday.isChecked()); // remover
		
		List<Integer>list = new ArrayList<Integer>();
		list.add( tbSunday.isChecked() ? 1 : 0 );
		list.add( tbMonday.isChecked() ? 1 : 0 );
		list.add( tbTuesday.isChecked() ? 1 : 0 );
		list.add( tbWednesday.isChecked() ? 1 : 0 );
		list.add( tbThursday.isChecked() ? 1 : 0 );
		list.add( tbFriday.isChecked() ? 1 : 0 );
		list.add( tbSaturday.isChecked() ? 1 : 0 );
		
		for (int i = 0; i < list.size(); i++) {
			Log.d("TESTE-days", ""+list.get(i));
		}
		
		currentAlarm.setTitle(preferences.getString(getString(R.string.alarmsettings_name_key), ""));
		currentAlarm.setHour(Integer.toString(tpHour.getCurrentHour())+":"+Integer.toString(tpHour.getCurrentMinute()));
		currentAlarm.setSound(preferences.getString(getString(R.string.alarmsettings_sound_key), ""));
		currentAlarm.setVolume(preferences.getInt(getString(R.string.alarmsettings_volume_key), 0));
		currentAlarm.setSnoozeTime(Integer.parseInt(preferences.getString(getString(R.string.alarmsettings_snooze_key), "0")));
		currentAlarm.setShutdownMode(Integer.parseInt(preferences.getString(getString(R.string.alarmsettings_shutdown_mode_key), "0")));
		currentAlarm.setDaysWeekList(list);
		currentAlarm.setSnooze(Integer.parseInt(preferences.getString(getString(R.string.alarmsettings_snooze_key), "0")) != 1 ? true : false);
		
		actionListenerButtons(0);
	}

	private void actionListenerCancelButton() {
		Log.d("BUTTON", "CancelButton clicked");
		actionListenerButtons(1);
	}

	private void actionListenerRemoveButton() {
		Log.d("BUTTON", "RemoveButton clicked");
		actionListenerButtons(2);
	}

	private void actionListenerButtons(int idButton) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putSerializable("currentAlarm", (Serializable) currentAlarm);
		bundle.putInt("actionToDo", idButton);
		intent.putExtras(bundle);
		setResult(1, intent);
		finish();
	}
	
	public void showNotification(){
	        String ns = Context.NOTIFICATION_SERVICE;
	        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);

//	        int icon = R.drawable.feedback;        // icon from resources
//	        CharSequence tickerText = mContext.getString(R.string.statusbar_notification); // ticker-text
//	        long when = System.currentTimeMillis();         // notification time
//	        Context context = getApplicationContext();      // application Context
//	        CharSequence contentTitle = mContext.getString(R.string.statusbar_notification);  // message title
//	        CharSequence contentText = mContext.getString(R.string.statusbar_notificatione_detailed);      // message text

//	        Intent notificationIntent = new Intent(mContext, Main.class);
//	        PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);

	        // the next two lines initialize the Notification, using the configurations above
	        Notification notification = new Notification(/*icon, tickerText, when*/);

	        //notification.defaults |= Notification.DEFAULT_SOUND;
//	        notification.defaults |= Notification.DEFAULT_VIBRATE;
//	        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/R.raw.notificationsound");
	        notification.sound = Uri.parse("content://media/internal/audio/media/43");

//	        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	        mNotificationManager.notify(1, notification);
	}


	@SuppressWarnings("deprecation")
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