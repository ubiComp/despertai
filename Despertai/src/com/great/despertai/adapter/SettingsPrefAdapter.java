package com.great.despertai.adapter;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.great.despertai.R;


public class SettingsPrefAdapter extends PreferenceFragment  {

	
	
 @Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.layout.alarm_settings_preference);
}
    
    
        
  
} 
