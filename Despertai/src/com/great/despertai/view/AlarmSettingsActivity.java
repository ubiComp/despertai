package com.great.despertai.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
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
	private ToggleButton tbSunday, tbMonday, tbTuesday, tbWednesday,
			tbThursday, tbFriday, tbSaturday;

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
		tbSunday = (ToggleButton) findViewById(R.id.alarmsettings_tb_sunday);
		tbMonday = (ToggleButton) findViewById(R.id.alarmsettings_tb_monday);
		tbTuesday = (ToggleButton) findViewById(R.id.alarmsettings_tb_tuesday);
		tbWednesday = (ToggleButton) findViewById(R.id.alarmsettings_tb_wednesday);
		tbThursday = (ToggleButton) findViewById(R.id.alarmsettings_tb_thursday);
		tbFriday = (ToggleButton) findViewById(R.id.alarmsettings_tb_friday);
		tbSaturday = (ToggleButton) findViewById(R.id.alarmsettings_tb_saturday);

		// set values in the preferences from current alarm (item clicked)
		editor.putString(getString(R.string.alarmsettings_name_key),
				currentAlarm.getTitle());
		editor.putString(getString(R.string.alarmsettings_sound_key),
				currentAlarm.getSound());
		editor.putInt(getString(R.string.alarmsettings_volume_key),
				currentAlarm.getVolume());
		editor.putString(getString(R.string.alarmsettings_snooze_key), ""
				+ currentAlarm.getSnoozeTime());
		editor.putString(getString(R.string.alarmsettings_shutdown_mode_key),
				"" + currentAlarm.getShutdownMode());
		editor.commit();

		tpHour.setCurrentHour(currentAlarm.getHours());
		tpHour.setCurrentMinute(currentAlarm.getMinutes());

		// tratar daysweeklist
		// tbSunday.setChecked(currentAlarm.getDaysWeekList().get(0)==1 ? true :
		// false);
		// tbMonday.setChecked(currentAlarm.getDaysWeekList().get(1)==1 ? true :
		// false);
		// tbTuesday.setChecked(currentAlarm.getDaysWeekList().get(2)==1 ? true
		// : false);
		// tbWednesday.setChecked(currentAlarm.getDaysWeekList().get(3)==1 ?
		// true : false);
		// tbThursday.setChecked(currentAlarm.getDaysWeekList().get(4)==1 ? true
		// : false);
		// tbFriday.setChecked(currentAlarm.getDaysWeekList().get(5)==1 ? true :
		// false);
		// tbSaturday.setChecked(currentAlarm.getDaysWeekList().get(6)==1 ? true
		// : false);

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
		List<Integer> list = new ArrayList<Integer>();
		list.add(tbSunday.isChecked() ? 1 : 0);
		list.add(tbMonday.isChecked() ? 1 : 0);
		list.add(tbTuesday.isChecked() ? 1 : 0);
		list.add(tbWednesday.isChecked() ? 1 : 0);
		list.add(tbThursday.isChecked() ? 1 : 0);
		list.add(tbFriday.isChecked() ? 1 : 0);
		list.add(tbSaturday.isChecked() ? 1 : 0);

		// String hour = TextUtils.leftPad( , 2, "0" );Integer.toString()
		String hour = tpHour.getCurrentHour() < 10 ? "0"
				+ tpHour.getCurrentHour().toString() : tpHour.getCurrentHour()
				.toString();
		String minute = tpHour.getCurrentMinute() < 10 ? "0"
				+ tpHour.getCurrentMinute().toString() : tpHour.getCurrentMinute()
				.toString();

		currentAlarm.setTitle(preferences.getString(
				getString(R.string.alarmsettings_name_key), ""));
		currentAlarm.setHour(hour + ":" + minute);
		currentAlarm.setSound(preferences.getString(
				getString(R.string.alarmsettings_sound_key), ""));
		currentAlarm.setVolume(preferences.getInt(
				getString(R.string.alarmsettings_volume_key), 0));
		currentAlarm.setSnoozeTime(Integer.parseInt(preferences.getString(
				getString(R.string.alarmsettings_snooze_key), "0")));
		currentAlarm.setShutdownMode(Integer.parseInt(preferences.getString(
				getString(R.string.alarmsettings_shutdown_mode_key), "0")));
		currentAlarm.setDaysWeekList(list);
		currentAlarm.setSnooze(Integer.parseInt(preferences.getString(
				getString(R.string.alarmsettings_snooze_key), "0")) != 1 ? true
				: false);

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

}