package com.great.despertai.adapter;

import com.great.despertai.R;
import android.os.Bundle;
import android.preference.*;
import android.view.View;


public class SettingsPrefAdapter extends PreferenceFragment  {

	
	
 @Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	addPreferencesFromResource(R.layout.settings_preference);
}
    
    
        
  
} 
