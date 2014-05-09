package com.great.despertai.adapter;

import java.util.List;

import com.great.despertai.R;
import com.great.despertai.R.id;
import com.great.despertai.model.vo.Alarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

@SuppressWarnings("unused")
public class ListViewAdapter extends ArrayAdapter<Alarm> implements OnCheckedChangeListener{
	
	private int resourceId;
	private LayoutInflater inflater;
	private Context context;
	
	private Alarm currentItem;
	
	public ListViewAdapter(Context context, int resource, List<Alarm> objects) {
		super(context, resource, objects);
		this.resourceId = resource;
		this.inflater = LayoutInflater.from(context);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = (LinearLayout) inflater.inflate(resourceId, null);
		}
		
		currentItem = getItem(position);
		
		TextView textview_hour = (TextView) convertView.findViewById(R.id.hour_item);
		TextView textview_title = (TextView) convertView.findViewById(R.id.title_item);
		final Switch switchButton = (Switch) convertView.findViewById(R.id.switch_item);
		
		textview_hour.setText(currentItem.getHour());
		textview_title.setText(currentItem.getTitle());
		switchButton.setChecked(currentItem.isSelected());
		switchButton.setTag(position);
		switchButton.setOnCheckedChangeListener(this);
		
		currentItem = null;
		
		return convertView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		if (currentItem == null){
			int position = (Integer) buttonView.getTag();
			Alarm currentItem = getItem(position);
		
			if (isChecked) {
				currentItem.setSelected(true);
			} else {
				currentItem.setSelected(false);
			}
		}
		
		currentItem = null;
	}
	
}
