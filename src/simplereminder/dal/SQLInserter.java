package simplereminder.dal;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import simplereminder.objects.Reminder;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLInserter {
	
	SQLiteDatabase database;
	SQLCreater creater;
	
	
	public SQLInserter(Context context)
	{
		creater = new SQLCreater(context);
	}
	
	public long insertReminder(Reminder reminder)
	{
		database = creater.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		//Insert to a ContentValues a headline, date, isDaily and details in this current order
		values.put(SQLCreater.COLUMN_HEADLINE, reminder.getHeadLine());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(reminder.getDate().getTime());
		values.put(SQLCreater.COLUMN_DATE, dateString);
		values.put(SQLCreater.COLUMN_ISDAILY, reminder.getIsDaily());
		values.put(SQLCreater.COLUMN_DETAILS, reminder.getDetails());
		
		//Insert the ContentValues into table
		long id = database.insert(SQLCreater.TABLE_NAME, null, values);
		creater.close();
		return id;
	}

}
