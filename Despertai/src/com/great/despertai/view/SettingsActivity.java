package com.great.despertai.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.great.despertai.adapter.SettingsPrefAdapter;
import com.great.despertai.R;

public class SettingsActivity extends FragmentActivity    {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.settings_layout);
			//SettigsDate fragment1 = new SettigsDate();		
			SettingsPrefAdapter fragment2 = new SettingsPrefAdapter();
			//FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			//transaction.add(R.id.content, fragment1).commit();
			getFragmentManager().beginTransaction().replace(R.id.content, fragment2).commit();
			 
			
			
		} 
}
