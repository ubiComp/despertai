package com.great.despertai.model.vo;

import java.io.Serializable;
import java.util.List;

public class Alarm implements Serializable {
	
	private static final long serialVersionUID = 6657019522759605163L;
	protected int id;
	protected String title;
	protected String hour;
//	protected String reminderHour;
	protected String sound;
	protected int volume;
	protected int snoozeTime;
	protected int shutdownMode;
	protected boolean isSelected;
//	protected boolean hasReminder;
	protected boolean hasSnooze;
	protected List<Integer> daysWeekList;
	
	public Alarm(int id, String title, String hour) {
		this.id = id;
		this.title = title;
		this.hour = hour;
	}
	
	public Alarm(int id, String title, String hour, /*String reminderHour,*/
			String sound, int volume, int snoozeTime, int shutdownMode,
			boolean isSelected, /*boolean hasReminder,*/ boolean hasSnooze,
			List<Integer> daysWeekList) {
		
		this.id = id;
		this.title = title;
		this.hour = hour;
//		this.reminderHour = reminderHour;
		this.sound = sound;
		this.volume = volume;
		this.snoozeTime = snoozeTime;
		this.shutdownMode = shutdownMode;
		this.hasSnooze = hasSnooze;
//		this.hasReminder = hasReminder;
		this.isSelected = isSelected;
		// inserir lista
//		this.daysWeekList = daysWeekList;
		
	}
	
	public void setId(int value) {
		this.id = value;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setHour(String value) {
		this.hour = value;
	}
	
	public String getHour() {
		return this.hour;
	}
	
	/*public void setReminderHour(String value) {
		this.reminderHour = value;
	}
	
	public String getReminderHour() {
		return this.reminderHour;
	}*/
	
	public void setSound(String value) {
		this.sound = value;
	}
	
	public String getSound() {
		return this.sound;
	}
	
	public void setVolume(int value) {
		this.volume = value;
	}
	
	public int getVolume() {
		return this.volume;
	}
	
	public void setSnoozeTime(int value) {
		this.snoozeTime = value;
	}
	
	public int getSnoozeTime() {
		return this.snoozeTime;
	}
	
	public void setShutdownMode(int value) {
		this.shutdownMode = value;
	}
	
	public int getShutdownMode() {
		return this.shutdownMode;
	}
	
	public void setSnooze(boolean value) {
		this.hasSnooze = value;
	}
	
	public boolean hasSnooze() {
		return this.hasSnooze;
	}
	
	/*public void setReminder(boolean value) {
		this.hasReminder = value;
	}
	
	public boolean hasReminder() {
		return this.hasReminder;
	}*/
	
	public void setSelected(boolean value) {
		this.isSelected = value;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void setDaysWeekList(List<Integer> list) {
		this.daysWeekList = list;
	}
	
	public List<Integer> getDaysWeekList() {
		return this.daysWeekList;
	}
	
	public String getDaysWeekListStr() { // testar
		String resultStr = null;
		for (int i = 0; i < daysWeekList.size(); i++) {
			resultStr = resultStr + daysWeekList.get(i);
		}
		return resultStr;
	}
	
}
