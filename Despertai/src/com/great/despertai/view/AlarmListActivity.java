package com.great.despertai.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.great.despertai.R;
import com.great.despertai.R.id;
import com.great.despertai.R.layout;
import com.great.despertai.adapter.ListViewAdapter;
import com.great.despertai.model.dao.AlarmDAO;
import com.great.despertai.model.vo.Alarm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

@SuppressWarnings("unused")
public class AlarmListActivity extends Activity implements OnClickListener, OnItemClickListener {

	private List<Alarm> alarmList;

	private ListView listview;
	ListViewAdapter adapter;
	private ImageButton addAlarmButton;
	private Switch switchButton;
	
	private AlarmDAO dao;
	
	// Current informations
	private Alarm alarmToConfigure;
	private int alarmPosition;
	private boolean isNewAlarmOption;
	private int alarmIdCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.alarm_list_layout);
		
		isNewAlarmOption = false;
		alarmList = new ArrayList<Alarm>();
		listview = (ListView) findViewById(R.id.list);
		dao = (new AlarmDAO(this));
		
		alarmList = dao.searchAlarmList();
		alarmIdCount = dao.getBiggestAlarmId();
		
		adapter = new ListViewAdapter(this, R.layout.list_item, alarmList);
		listview.setAdapter(adapter);
		listview.setBackgroundColor(Color.TRANSPARENT);
		listview.setClickable(true);
		listview.setOnItemClickListener(this);
		
		addAlarmButton = (ImageButton) findViewById(R.id.button_add_alarm);
		addAlarmButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button_add_alarm:
			actionListenerAddAlarmButton();
			break;
			
		default:
			break;
		}
		
	}
	
	private void actionListenerAddAlarmButton() {
		isNewAlarmOption = true;
		alarmToConfigure = new Alarm(alarmIdCount + 1, "Default", "00:00");
		configureAlarm(alarmToConfigure);
	}
	
	private void configureAlarm(Alarm alarm) {
		Intent intent = new Intent(AlarmListActivity.this, AlarmSettingsActivity.class);
		Bundle bundle = new Bundle();

		bundle.putSerializable("currentAlarm", (Serializable) alarm);

		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		alarmToConfigure = alarmList.get(position);
		configureAlarm(alarmToConfigure);
		alarmPosition = position;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(resultCode == 1){
//			Toast.makeText(this, "Pass", Toast.LENGTH_SHORT).show();
			
			if(data.getExtras().containsKey("currentAlarm")){
				alarmToConfigure = (Alarm) data.getExtras().getSerializable("currentAlarm");
			}
			
			if(data.getExtras().containsKey("actionToDo")){
				switch (data.getExtras().getInt("actionToDo")) {
				case 0:
					saveOption(alarmToConfigure);
					break;
				case 1:
					cancelOption(alarmToConfigure);
					break;
				case 2:
					removeOption(alarmToConfigure, alarmPosition);
					break;
				}
			}
		}
		else{
//			Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
		}
		
		alarmPosition = -1;
//		data.set
	}
	
	private void saveOption(Alarm alarm) {
		if (isNewAlarmOption) {
			alarmList.add(alarm);
			dao.insert(alarm);
			isNewAlarmOption = false;
		}
		else {
			alarm.setTitle("Modified!");
			dao.update(alarm);
			alarmList.set(alarmPosition, alarm);
		}
		adapter.notifyDataSetChanged();
	}

	private void cancelOption(Alarm alarm) {
		// do nothing
	}
	
	private void removeOption(Alarm alarm, int position) {
		if (!isNewAlarmOption) {
			alarmList.remove(position);
			dao.delete(alarm);
			adapter.notifyDataSetChanged();
		}
	}
}