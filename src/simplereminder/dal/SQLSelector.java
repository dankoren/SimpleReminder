package simplereminder.dal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import simplereminder.logics.ReminderManager;
import simplereminder.objects.Reminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLSelector {
	SQLiteDatabase database;
	SQLCreater creater;
	
	static String SELECT = "Select * from " + SQLCreater.TABLE_NAME + "\n" +
			"Order by " + SQLCreater.COLUMN_DATE;

	public SQLSelector(Context context)
	{
		creater = new SQLCreater(context);
	}
	
	public Cursor SelectAll()
	{
		database = creater.getReadableDatabase();	
		Cursor c = database.rawQuery(SELECT,null);
		c.moveToFirst();
		creater.close();
		return c;
	}
	

	
	public Cursor SelectOffset(int limit, int offset)
	{
		//Select data, limit - number of rows to limit
		//			   offset - the initial row to start from
		String queryString;
		queryString =  "" + SELECT + " LIMIT " + limit + " OFFSET " + offset;
		database = creater.getReadableDatabase();
		Cursor c = database.rawQuery(queryString, null);
		c.moveToFirst();
		creater.close();
		return c;
	}
	

	
	public Cursor SelectById(long id)
	{
		//Select a row from database with id
		database = creater.getReadableDatabase();
		Cursor c = database.rawQuery("Select * from " + SQLCreater.TABLE_NAME + " Where id = " + id, null);
		c.moveToFirst();
		creater.close();
		return c;
	}
	

	public Reminder ReminderById(long id)
	{
		//Gets an id and returns reminder with id.
		Cursor c = SelectById(id);
		Reminder reminder = dbToReminder(c);
		return reminder;
	}
	
	public Reminder dbToReminder(Cursor c)
	{
		
		Reminder reminder = new Reminder();
		//Setting the reminder from the table in the following order:
		//		headline, date&time, frequency, details.
		reminder.setHeadLine(c.getString(1));
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		GregorianCalendar date = new GregorianCalendar();
		try {
			date.setTime(formatter.parse(c.getString(2)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		reminder.setDate(date);
		
		reminder.setIsDaily(c.getInt(3));
		
		reminder.setDetails(c.getString(4));
		return reminder;
	}
	
	
	public ArrayList<Reminder> dbToList(Cursor c)
	{
		c = SelectAll();
		ArrayList<Reminder> reminders = new ArrayList<Reminder>();
		while(!c.isAfterLast())
		{
			Reminder reminder = dbToReminder(c);
			reminders.add(reminder);
			c.moveToNext();
		}
		return reminders;
		
	}
	
	
	public void setAlarms(Context context)
	{
		Cursor c = SelectAll();
		ReminderManager rm = new ReminderManager();
		while(!c.isAfterLast())
		{
			rm.addNotification(context, c.getLong(0));
			c.moveToNext();
		}
	}
	
	
		
}
