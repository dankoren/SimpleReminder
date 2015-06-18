package simplereminder.ui;



import com.example.simplereminder2.*;


import simplereminder.dal.SQLCreater;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Intent;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import android.widget.ListView;

public class RemindersActivity extends Activity {
	//The main Activity:
	//displays all the reminders in a list view
	



	public static ListView lvReminders;
	SQLCreater creater;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//The first time this activity starts
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);
		lvReminders = (ListView)findViewById(R.id.reminder_list);
		Intent i = new Intent("simplereminder.broadcastreceivers.UI_CHANGE");
		this.sendBroadcast(i);
		//Creating the database and the table
	
		this.deleteDatabase(SQLCreater.DATABASE_NAME);
		creater = new SQLCreater(this);
	}
	public void add_btn_click(View v)
	{
		Intent intent = new Intent(this, InsertReminderActivity.class);
		startActivity(intent);
	}  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reminders, menu);
		return true;
	}
	
	
	

}
