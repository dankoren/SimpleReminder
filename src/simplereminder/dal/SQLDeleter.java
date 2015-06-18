package simplereminder.dal;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLDeleter{
	SQLiteDatabase database;
	SQLCreater creater;
	
	public SQLDeleter(Context context)
	{
		creater = new SQLCreater(context);
	}
	public long deleteReminder(long id)
	{
		long deleteId;
		database = creater.getWritableDatabase();
		deleteId = database.delete(SQLCreater.TABLE_NAME, "id = " + id, null);	
		creater.close();
		return deleteId;
	}
	
	
}
