package simplereminder.dal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLCreater extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "reminders.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME = "reminders";
	public static final String COLUMN_ID = "ID";
	public static final String COLUMN_HEADLINE = "HEADLINE";
	public static final String COLUMN_DATE = "DATE";
	public static final String COLUMN_TIME = "TIME";
	public static final String COLUMN_ISDAILY = "ISDAILY";
	public static final String COLUMN_DETAILS = "DETAILS";
	public static final String COMMA = ",";
	public static final String CREATE_TABLE = "create table " + 
			TABLE_NAME + "(" + COLUMN_ID +" integer primary key autoincrement" + 
			COMMA + COLUMN_HEADLINE + COMMA + COLUMN_DATE + COMMA + 
			COLUMN_ISDAILY + COMMA + COLUMN_DETAILS + ");";
	
	//create table reminders(ID primary key autoincrement,HEADLINE,DATE,FREQUENCY,DETAILS);
	
	public SQLCreater(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
