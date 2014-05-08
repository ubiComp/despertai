package com.example.despertai;

public class ListViewItem {
	protected String hour;
	protected String title;
	protected boolean isSelected;
	
	public ListViewItem(String hour, String title) {
		this.hour = hour;
		this.title = title;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getHour() {
		return this.hour;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setSelected(boolean value) {
		this.isSelected = value;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
}
