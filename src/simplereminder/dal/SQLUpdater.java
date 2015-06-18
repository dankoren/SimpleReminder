package simplereminder.dal;

import java.text.SimpleDateFormat;

import simplereminder.objects.Reminder;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLUpdater {
	SQLiteDatabase database;
	SQLCreater creater;
	
	
	public SQLUpdater(Context context)
	{
		creater = new SQLCreater(context);
	}
	public long updateReminder(long id, Reminder reminder)
	{
		long updateId;
		database = creater.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		//Insert to a ContentValues a headline, date&time, frequency and details in this current order
		values.put(SQLCreater.COLUMN_HEADLINE, reminder.getHeadLine());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(reminder.getDate().getTime());
		values.put(SQLCreater.COLUMN_DATE, dateString);	
		values.put(SQLCreater.COLUMN_ISDAILY, reminder.getIsDaily());
		values.put(SQLCreater.COLUMN_DETAILS, reminder.getDetails());
		
		//Updates row 'id' with content values
		updateId = database.update(SQLCreater.TABLE_NAME, values, " id = " + id, null);
		creater.close();
		return updateId;
	}
	
	
	
}
