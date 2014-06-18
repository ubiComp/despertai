package com.great.despertai.view;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.great.despertai.R;

public class SettingsActivity extends PreferenceActivity    {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.layout.settings_layout);

			 
			
			
		} 
}
