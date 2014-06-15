package com.great.despertai.view;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.great.despertai.R;


//@SuppressWarnings("deprecation")
public class HomeActivity extends Activity implements OnClickListener {

	private ImageView alarmListButton, weatherButton, settingsButton;
//	private DigitalClock digitalClock;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home_layout);
		
//		DateFormat format = new SimpleDateFormat("HH:mm a");
//		String formatted = format.format(new Date());
//		
//		digitalClock = (DigitalClock)findViewById(R.id.digitalCloc);
//		digitalClock.setTypeface(Typeface.createFromAsset(getAssets(), "MonoSpatial.ttf"));
//		digitalClock.setText("");
//		digitalClock.setText(formatted);
//		
		alarmListButton = (ImageView) findViewById(R.id.button_alarm_list);
		weatherButton = (ImageView) findViewById(R.id.button_weather);
		settingsButton = (ImageView) findViewById(R.id.button_settings);

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
		Intent intent = new Intent(HomeActivity.this, AlarmListActivity.class);
		startActivity(intent);
	}
	
	private void actionListenerWeatherButton() {
	}
	
	private void actionListenerSettingsButton() {
		Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
		startActivity(intent);
	}

}