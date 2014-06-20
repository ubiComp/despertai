package com.great.despertai.view;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.Toast;

import com.great.despertai.R;
import com.great.despertai.loccam.SysSUClient;
import com.great.despertai.util.Weather;

//@SuppressWarnings("deprecation")
public class HomeActivity extends Activity implements OnClickListener {

	// private static final String TAG = "Home-Despertai";
	private ImageView alarmListButton, weatherButton, settingsButton;
	private TextClock textClock;
	// private DigitalClock digitalClock;
	SysSUClient client;
	SharedPreferences prefs = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(prefTime);

		setContentView(R.layout.home_layout);

		// DateFormat format = new SimpleDateFormat("HH:mm a");
		// String formatted = format.format(new Date());
		//
		// digitalClock = (DigitalClock)findViewById(R.id.digitalCloc);
		// digitalClock.setTypeface(Typeface.createFromAsset(getAssets(),
		// "MonoSpatial.ttf"));
		// digitalClock.setText("");
		// digitalClock.setText(formatted);

		textClock = (TextClock) findViewById(R.id.hk_time);

		alarmListButton = (ImageView) findViewById(R.id.button_alarm_list);
		weatherButton = (ImageView) findViewById(R.id.button_weather);
		settingsButton = (ImageView) findViewById(R.id.button_settings);

		alarmListButton.setOnClickListener(this);
		weatherButton.setOnClickListener(this);
		settingsButton.setOnClickListener(this);

		client = new SysSUClient(this);
		// o Loccam precisa de tempo para iniciar
		// o put é realizado 1segundo após sua inicialização
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				client.put();
			}
		}, 500);
		// Chamando o recptor de alarmes
		Log.i("Weliton", "teste");
//		callReceiver();
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

		client.get();
		String city = client.getCity();
		if (city != null) {
			Weather weather = new Weather(this);
			weather.execute(city);
		} else
			Toast.makeText(this, "Problema na conexão!", Toast.LENGTH_SHORT)
					.show();

	}

	private void actionListenerSettingsButton() {
		Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
		startActivity(intent);

	}

	private void setTimeZone() {
		String timeZone = prefs.getString(
				this.getResources().getString(R.string.settings_time_zone),
				null);
		if (timeZone != null) {
			textClock.setTimeZone(timeZone);
			AppWidgetManager.getInstance(getApplication())
					.notifyAppWidgetViewDataChanged(R.id.hk_time,
							R.layout.home_layout);
		}
		Log.v("Alarm-Despertai", "Mudou Time-Zone para:" + timeZone);

	}

	private void setFormatTime() {
		String timeFormat = prefs.getString(
				this.getResources().getString(R.string.settings_time_format),
				null);
		if (timeFormat != null) {
			if (timeFormat.equals("1"))
				textClock.setFormat12Hour("kk:mm aa");

			else
				textClock.setFormat24Hour("kk:mm");

			AppWidgetManager.getInstance(getApplication())
					.notifyAppWidgetViewDataChanged(R.id.hk_time,
							R.layout.home_layout);
		}
		Log.v("Alarm-Despertai", "Mudou Format para:" + timeFormat);
	}

	private OnSharedPreferenceChangeListener prefTime = new OnSharedPreferenceChangeListener() {

		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {
			if (key.equals(R.string.settings_time_zone)) {
				setTimeZone();
			}
			if (key.equals(R.string.settings_time_format)) {
				setFormatTime();
			}
		}
	};

	@Override
	public void onDestroy() {
		client.stop();
		super.onDestroy();
	}

	// Fun��o para chamar o BroadcastReceiver e ativar os alarmes
	public void callReceiver() {
		Intent intent = new Intent("merdadeteste");
		// Chama o Broadcast
		sendBroadcast(intent);
	}

}
