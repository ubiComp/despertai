package com.great.despertai.view;

import java.io.Serializable;

import com.example.despertai.R;
import com.example.despertai.R.id;
import com.example.despertai.R.layout;
import com.great.despertai.model.vo.Alarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class AlarmSettingsActivity extends Activity implements OnClickListener {

	private ImageButton saveButton, cancelButton, removeButton;
	private Alarm currentAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.alarm_settings_layout);

		currentAlarm = (Alarm) getIntent().getExtras().getSerializable("currentAlarm");
		
		saveButton = (ImageButton) findViewById(R.id.button_save);
		cancelButton = (ImageButton) findViewById(R.id.button_cancel);
		removeButton = (ImageButton) findViewById(R.id.button_remove);

		saveButton.setOnClickListener(this);
		cancelButton.setOnClickListener(this);
		removeButton.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_save:
			actionListenerSaveButton();
			break;
		case R.id.button_cancel:
			actionListenerCancelButton();
			break;
		case R.id.button_remove:
			actionListenerRemoveButton();
			break;

		default:
			break;
		}
	}
	
	private void actionListenerSaveButton() {
		Log.d("BUTTON", "SaveButton clicked");
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