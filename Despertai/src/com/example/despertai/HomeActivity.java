package com.example.despertai;

import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

//@SuppressWarnings("deprecation")
public class HomeActivity extends Activity implements OnClickListener {

	private ImageButton alarmListButton, weatherButton, settingsButton;
//	private DigitalClock digitalClock;
//	protected int alarmIdCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.home_layout);
		
//		DateFormat format = new SimpleDateFormat("HH:mm a");
//		String formatted = format.format(new Date());
		
//		digitalClock = (DigitalClock)findViewById(R.id.digitalClock1);
//		digitalClock.setTypeface(Typeface.createFromAsset(getAssets(), "MonoSpatial.ttf"));
//		digitalClock.setText("Cris");
//		digitalClock.setText(formatted);
		
//		alarmIdCount = 1;
		
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
		Intent intent = new Intent(HomeActivity.this, AlarmListActivity.class);
		startActivity(intent);
		
//		Intent intent = new Intent(HomeActivity.this, AlarmListActivity.class);
//		Bundle bundle = new Bundle();
//
//		bundle.putInt("alarmIdCount", alarmIdCount);
//
//		intent.putExtras(bundle);
//		startActivityForResult(intent, 1);
	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		
//		this.alarmIdCount = (int) data.getExtras().getInt("alarmIdCount");
//	}
	
	private void actionListenerWeatherButton() {
	}
	
	private void actionListenerSettingsButton() {
//		Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
//		startActivity(intent);
	}

}