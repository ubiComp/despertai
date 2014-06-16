package com.great.despertai.view;

import java.io.Serializable;

import android.content.Intent;
import android.os.Bundle;
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
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alarm_settings_layout);
		SettingsPrefAdapter fragment2 = new SettingsPrefAdapter();
		getFragmentManager().beginTransaction()
				.replace(R.id.content, fragment2).commit();

		currentAlarm = (Alarm) getIntent().getExtras().getSerializable(
				"currentAlarm");
		
		tpHour = (TimePicker) findViewById(R.id.alarmsettings_time_picker);
		tbSunday = (ToggleButton) findViewById(R.id.alarmsettings_tb_sunday);
		tbMonday = (ToggleButton) findViewById(R.id.alarmsettings_tb_monday);
		tbTuesday = (ToggleButton) findViewById(R.id.alarmsettings_tb_tuesday);
		tbWednesday = (ToggleButton) findViewById(R.id.alarmsettings_tb_wednesday);
		tbThursday = (ToggleButton) findViewById(R.id.alarmsettings_tb_thursday);
		tbFriday = (ToggleButton) findViewById(R.id.alarmsettings_tb_friday);
		tbSaturday = (ToggleButton) findViewById(R.id.alarmsettings_tb_saturday);
		
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
		// obter dados dos componentes e inserir em currentAlarm
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