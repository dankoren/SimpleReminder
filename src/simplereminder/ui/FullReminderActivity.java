package simplereminder.ui;


import java.text.SimpleDateFormat;

import simplereminder.dal.SQLDeleter;
import simplereminder.dal.SQLSelector;
import simplereminder.logics.ReminderManager;
import simplereminder.objects.Reminder;
import simplereminder.util.CalendarWriter;

import com.example.simplereminder2.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

@SuppressLint("NewApi")
public class FullReminderActivity extends Activity {
	//Activity that displays a reminder and grants the user
	//an option to delete or edit the reminder
	
	Cursor c;
	Bundle extras;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_reminder);
		
		//Gets the reminder position on the list view
		extras = this.getIntent().getExtras();
		int position = extras.getInt("simplereminder.ui.POSITION");
		
		
		//Retrieve the pressed reminder from the database
		SQLSelector selector = new SQLSelector(this);
		c = selector.SelectOffset(1, position);
		Reminder reminder = selector.dbToReminder(c);
		
		Log.d("Reminder pos in FullReminder:", "" + position);	
		//Shows the retrieved reminder to the UI
		this.setTitle(reminder.getHeadLine());
		TextView date = (TextView)findViewById(R.id.date_tv);
		TextView details = (TextView)findViewById(R.id.details_tv);
		details.setText(reminder.getDetails());
		date.setText(new CalendarWriter().displayDate(reminder));
	}
	
	public void edit_btn_click(View v)
	{
		Intent intent = new Intent(this, InsertReminderActivity.class);
		int position = this.getIntent().getIntExtra("simplereminder.ui.POSITION", -1);
		Log.d("Position of edit pressed item", "" + position);
		int id = extras.getInt("simplereminder.ui.ID");	
		intent.putExtra("simplereminder.ui.POSITION2", id);
		this.finish();
		this.startActivity(intent);
		
	}
	public void delete_btn_click(View v)
	{
		//Deletes the reminder when pressing 'Delete' button
		ReminderManager rm = new ReminderManager();
		rm.deleteReminder(this, c.getLong(0));
		this.finish();
	}
	
	public void cancel_btn_click(View v)
	{
		this.finish();
	}
	

}
