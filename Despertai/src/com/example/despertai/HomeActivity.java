package com.example.despertai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DigitalClock;
import android.widget.ImageButton;

@SuppressWarnings("deprecation")
public class HomeActivity extends Activity implements OnClickListener {

	private ImageButton alarmListButton, weatherButton, settingsButton;
	private DigitalClock digitalClock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home_layout);

		
		DateFormat format = new SimpleDateFormat("HH:mm a");
		String formatted = format.format(new Date());
		
//		digitalClock = (DigitalClock)findViewById(R.id.digitalClock1);
//		digitalClock.setTypeface(Typeface.createFromAsset(getAssets(), "MonoSpatial.ttf"));
//		digitalClock.setText("Cris");
//		digitalClock.setText(formatted);
		
		Log.d("TESTE","date: "+formatted);

		alarmListButton = (ImageButton) findViewById(R.id.button_alarm_list);
		weatherButton = (ImageButton) findViewById(R.id.button_weather);
		settingsButton = (ImageButton) findViewById(R.id.button_settings);

		alarmListButton.setOnClickListener(this);
		weatherButton.setOnClickListener(this);
		settingsButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_alarm_list:
			actionListenerAlarmListButton();
			break;
		case R.id.button_weather:
			actionListenerWeatherButton();
			break;
		case R.id.button_settings:
			actionListenerSettingsButton();
			break;

		default:
			break;
		}
	}
	private void actionListenerAlarmListButton() {
//		Intent myItent = new Intent(HomeActivity.this, AlarmListActivity.class);
//		startActivity(myItent);
	}
	
	private void actionListenerWeatherButton() {
	}
	
	private void actionListenerSettingsButton() {
//		Intent myItent = new Intent(HomeActivity.this, SettingsActivity.class);
//		startActivity(myItent);
	}

}