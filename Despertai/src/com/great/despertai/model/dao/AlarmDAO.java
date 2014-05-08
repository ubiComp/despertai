package com.great.despertai.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.great.despertai.model.vo.Alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AlarmDAO {
	
	private SQLiteDatabase db;
	private BaseDAO baseDao;
	
	private String[] columns = {
		BaseDAO.ALARM_ID,
		BaseDAO.ALARM_TITLE,
		BaseDAO.ALARM_HOUR,
		BaseDAO.ALARM_SOUND,
		BaseDAO.ALARM_VOLUME,
		BaseDAO.ALARM_SNOOZETIME,
		BaseDAO.ALARM_SHUTDMODE,
		BaseDAO.ALARM_REMINDERHOUR,
		BaseDAO.ALARM_ISSELECTED,
		BaseDAO.ALARM_HASREMINDER,
		BaseDAO.ALARM_HASSNOOZE
	};
	
	public AlarmDAO(Context context) {
		baseDao = new BaseDAO(context);
	}
	
	public void open() throws SQLException {
		baseDao.openDatabase();
		db = baseDao.getWritableDatabase();
	}
	
	public void close() {
		baseDao.close();
	}
	
	public void insert(Alarm alarm) {
		ContentValues values = new ContentValues();
		values.put(BaseDAO.ALARM_TITLE, alarm.getTitle());
		values.put(BaseDAO.ALARM_HOUR, alarm.getHour());
		values.put(BaseDAO.ALARM_REMINDERHOUR, alarm.getReminderHour());
		values.put(BaseDAO.ALARM_SOUND, alarm.getSound());
		values.put(BaseDAO.ALARM_VOLUME, alarm.getVolume());
		values.put(BaseDAO.ALARM_SNOOZETIME, alarm.getSnoozeTime());
		values.put(BaseDAO.ALARM_SHUTDMODE, alarm.getShutdownMode());
		values.put(BaseDAO.ALARM_ISSELECTED, alarm.isSelected() ? 1 : 0);
		values.put(BaseDAO.ALARM_HASREMINDER, alarm.hasReminder() ? 1 : 0);
		values.put(BaseDAO.ALARM_HASSNOOZE, alarm.hasSnooze() ? 1 : 0);
		
		open();
		
		long id = db.insert(BaseDAO.ALARM_TABLE_NAME, null, values);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + id + "] record was inserted.");
	}
	
	public void search(int id) {
		String where = BaseDAO.ALARM_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(id) };
		
		open();
		
		Cursor c = db.query(BaseDAO.ALARM_TABLE_NAME, columns, where,
				whereArgs, null, null, null);
		
		c.moveToFirst();
		
		int idFound = c.getInt(c.getColumnIndex(BaseDAO.ALARM_ID));
		
		c.close();
		close();
		
		Log.i(BaseDAO.DB_LOG, "" + idFound);
	}
	
	public List<Alarm> searchAlarmList() {

		List<Alarm> alarmList = new ArrayList<Alarm>();
		Alarm alarm;

		open();
		
		Cursor c = db.query(BaseDAO.ALARM_TABLE_NAME, columns, null, null,
				null, null, null);

		if (c.moveToFirst()) {
			do {
				Log.d(BaseDAO.DB_LOG,
						"id: " + c.getString(c.getColumnIndex("alarm_id")));

				alarm = new Alarm(
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_ID)),
						c.getString(c.getColumnIndex(BaseDAO.ALARM_TITLE)),
						c.getString(c.getColumnIndex(BaseDAO.ALARM_HOUR)),
						c.getString(c.getColumnIndex(BaseDAO.ALARM_REMINDERHOUR)),
						c.getString(c.getColumnIndex(BaseDAO.ALARM_SOUND)),
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_VOLUME)),
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_SNOOZETIME)),
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_SHUTDMODE)),
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_ISSELECTED)) == 1 ? true : false,
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_HASREMINDER)) == 1 ? true : false,
						c.getInt(c.getColumnIndex(BaseDAO.ALARM_HASSNOOZE)) == 1 ? true : false);

				alarmList.add(alarm);
			} while (c.moveToNext());
		}

		c.close();
		
		close();
		
		return alarmList;
	}
	
	public void update(Alarm alarm) {
		String where = BaseDAO.ALARM_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(alarm.getId()) };
		
		ContentValues values = new ContentValues();
		values.put(BaseDAO.ALARM_TITLE, alarm.getTitle());
		values.put(BaseDAO.ALARM_HOUR, alarm.getHour());
		values.put(BaseDAO.ALARM_REMINDERHOUR, alarm.getReminderHour());
		values.put(BaseDAO.ALARM_SOUND, alarm.getSound());
		values.put(BaseDAO.ALARM_VOLUME, alarm.getVolume());
		values.put(BaseDAO.ALARM_SNOOZETIME, alarm.getSnoozeTime());
		values.put(BaseDAO.ALARM_SHUTDMODE, alarm.getShutdownMode());
		values.put(BaseDAO.ALARM_ISSELECTED, alarm.isSelected());
		values.put(BaseDAO.ALARM_HASREMINDER, alarm.hasReminder());
		values.put(BaseDAO.ALARM_HASSNOOZE, alarm.hasSnooze());
		
		open();
		
		int count = db.update(BaseDAO.ALARM_TABLE_NAME, values, where, whereArgs);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + count + "] records have been updated.");
	}
	
	public void delete(Alarm alarm) {
		String where = BaseDAO.ALARM_ID + "=?";
		String[] whereArgs = new String[] { String.valueOf(alarm.getId()) };
		
		open();
		
		int count = db.delete(BaseDAO.ALARM_TABLE_NAME, where, whereArgs);
		
		close();
		
		Log.i(BaseDAO.DB_LOG, "[" + count + "] records have been deleted.");
	}
	
	public void list() {
		open();
		
		Cursor c = db.query(BaseDAO.ALARM_TABLE_NAME, columns, null, null,
				null, null, null, null);
		
		if(c.moveToFirst()){
			do{
				Log.i(BaseDAO.DB_LOG,
						"alarm_id: "
						+ c.getString(c.getColumnIndex(BaseDAO.ALARM_ID))
						+ " alarm_title: "
						+ c.getString(c.getColumnIndex(BaseDAO.ALARM_TITLE))
						+ " alarm_hour: "
						+ c.getString(c.getColumnIndex(BaseDAO.ALARM_HOUR)));
				
			} while(c.moveToNext());
		}
		
		c.close();
		close();
	}

}
