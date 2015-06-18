package simplereminder.ui;


import java.util.Calendar;
import java.util.GregorianCalendar;

import simplereminder.dal.SQLSelector;
import simplereminder.logics.ReminderManager;
import simplereminder.objects.Reminder;
import simplereminder.util.CalendarWriter;

import com.example.simplereminder2.*;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

@SuppressLint("NewApi")
public class InsertReminderActivity extends Activity {
	//Activity that takes in all the new reminder variables and inserts it in the database
	
	Reminder newReminder;
	String newHeadLine, newDetails;
	int newIsDaily;
	CalendarWriter calWriter;
	GregorianCalendar newCal;
	Button dateBtn;
	Button timeButton;
	EditText headLineEt, detailsEt;
	RadioButton daily, once;
	LinearLayout addLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_insert_reminder);
		
		int position = this.getIntent().getIntExtra("simplereminder.ui.POSITION2", -1);
		//if position is -1 then activity meant to add reminder
		//else activity meant to update reminder with 'position' from list
		
		dateBtn = (Button)findViewById(R.id.date_btn);
		timeButton = (Button)findViewById(R.id.time_btn);
		detailsEt = (EditText)findViewById(R.id.details_et);
		headLineEt = (EditText)findViewById(R.id.headline_et);
		daily = (RadioButton)findViewById(R.id.radio_daily);
		once = (RadioButton)findViewById(R.id.radio_once);
		newCal = new GregorianCalendar();
		calWriter = new CalendarWriter();
		updateUI(position);
	}

	
	
	public void updateUI(int position)
	{
		if(position!=-1) //if activity meant to update reminder
		{
			//Gets reminder from database with position and updates the UI accordingly
			SQLSelector selector = new SQLSelector(this);
			long id = selector.SelectOffset(1, position).getLong(0);
			Reminder editReminder = selector.ReminderById(id);
			headLineEt.setText(editReminder.getHeadLine());
			dateBtn.setText(calWriter.writeDate(editReminder.getDate()));
			timeButton.setText(calWriter.writeTime(editReminder.getDate()));	
			newCal = editReminder.getDate();
			detailsEt.setText(editReminder.getDetails());
			int editIsDaily = editReminder.getIsDaily();
			Log.d("isDaily", ""+editIsDaily);
			if(editIsDaily == 1)
			{
				daily.setChecked(true);
				once.setChecked(false);
				radioHandler(R.id.radio_daily);
			}
			
		}
		else //if activity meant to add reminder
		{
			dateBtn.setText(calWriter.writeDate(newCal));
			timeButton.setText(calWriter.writeTime(newCal));
			daily.setChecked(false);
			once.setChecked(true);
		}	
		
	}

	public void radio_btn_click(View v)
	{
		int radioId = v.getId();
		radioHandler(radioId);
	}

	public void radioHandler(int radioId)
	{
		//Updates UI according to selected radio button
		switch(radioId)
		{
			case R.id.radio_daily: 
				{
					dateBtn.setEnabled(false);
					dateBtn.setText("Daily");
					Calendar today = new GregorianCalendar();
					newCal.set(today.get(Calendar.YEAR), 
							today.get(Calendar.MONTH),
							today.get(Calendar.DAY_OF_MONTH));
				}
			break;
			case R.id.radio_once: 
				{
					dateBtn.setEnabled(true);
					dateBtn.setText("" + new CalendarWriter().writeDate(newCal));
				}
		}
	}
	
	
	
	
	public void date_btn_click(View v)
	{
		//Change the date value of newCal - a calendar variable
		new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int month, int day) {
				newCal.set(newCal.YEAR, year);
				newCal.set(newCal.MONTH, month);
				newCal.set(newCal.DAY_OF_MONTH, day);
				dateBtn.setText(calWriter.writeDate(newCal));  
				
			}
		}, newCal.get(newCal.YEAR), newCal.get(newCal.MONTH), newCal.get(newCal.DAY_OF_MONTH)).show();
	}
	
	
	public void time_btn_click(View v)
	{
		//Change the time value of newCal - a calendar variable
		new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				newCal.set(newCal.HOUR_OF_DAY, hourOfDay);
				newCal.set(newCal.MINUTE, minute);
				timeButton.setText(calWriter.writeTime(newCal));
				
			}
		}, newCal.get(newCal.HOUR_OF_DAY), newCal.get(newCal.MINUTE), true).show();
	}
	
	public void confirm_btn_click(View v)
	{
		//if user wrote a headline
		if(headLineEt.getText().length()>0)
		{	
			//if the input calendar is later than now
			if(daily.isChecked() || newCal.after(new GregorianCalendar()))
			{	
				//Change the value of newFrequency
				ReminderManager rm = new ReminderManager();
				
				if(daily.isChecked())
				{
					newIsDaily = 1;
					GregorianCalendar now = new GregorianCalendar();
					if(newCal.before(now))
					{
						newCal.set(Calendar.DAY_OF_MONTH, newCal.get(Calendar.DAY_OF_MONTH) + 1);
					}
				}
				else
					newIsDaily = 0;
	
				//Creates a new reminder with all the inputed variables.
				newReminder = new Reminder(headLineEt.getText().toString(),
						newCal,newIsDaily,detailsEt.getText().toString());
				int position = this.getIntent().getIntExtra("simplereminder.ui.POSITION2", -1);
				Log.d("Position of item:", ""+position);
				if(position!=-1)
				{
				    long id = new SQLSelector(this).SelectOffset(1, position).getLong(0);
					rm.updateReminder(this, id, newReminder);
				}
				else
					rm.addReminder(this, newReminder);
				//Finish activity
				this.finish();
			}
			else
				Toast.makeText(this, "Please fill in a viable date and time", Toast.LENGTH_SHORT).show();
		}
		else
			Toast.makeText(this, "Please fill in a Headline", Toast.LENGTH_SHORT).show();
			
	}
	
	public void cancel_btn_click(View v)
	{
		this.finish();
	}
	
}
