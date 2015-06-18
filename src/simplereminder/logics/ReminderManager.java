package simplereminder.logics;

import java.util.ArrayList;
import java.util.Random;

import com.example.simplereminder2.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import simplereminder.dal.SQLCreater;
import simplereminder.dal.SQLDeleter;
import simplereminder.dal.SQLInserter;
import simplereminder.dal.SQLSelector;
import simplereminder.dal.SQLUpdater;
import simplereminder.objects.Reminder;
import simplereminder.services.NotifyService;
import simplereminder.ui.FullReminderActivity;
import simplereminder.ui.InsertReminderActivity;
import simplereminder.ui.RemindersActivity;
import simplereminder.util.MyAdapter;

public class ReminderManager {
	
	AlarmManager am;
	
	
	
	public ReminderManager()
	{
		
	}
	
	public void addReminder(Context context, Reminder reminder)
	{
		long insertId;
		//Insert the new reminder to database.
		SQLInserter inserter = new SQLInserter(context );
		insertId = inserter.insertReminder(reminder);
		Intent i = new Intent("simplereminder.broadcastreceivers.UI_CHANGE");
		context.sendBroadcast(i);
		addNotification(context, insertId);			
	}

	public void updateReminder(Context context, long id, Reminder reminder)
	{
		SQLUpdater updater = new SQLUpdater(context);
		deleteNotification(context,id);
		updater.updateReminder(id, reminder);
		Intent i = new Intent("simplereminder.broadcastreceivers.UI_CHANGE");
		context.sendBroadcast(i);
		addNotification(context,id);
	}
	
	
	public void deleteReminder(Context context, long id)
	{
		SQLDeleter deleter = new SQLDeleter(context);
		deleter.deleteReminder(id);
		Intent i = new Intent("simplereminder.broadcastreceivers.UI_CHANGE");
		context.sendBroadcast(i);
		deleteNotification(context, id);
	}
	
	
	public void updateNotification(Context context, long reminderId, Reminder reminder)
	{
		Intent notifyIntent = new Intent(context,NotifyService.class);
		PendingIntent notifyPintent = PendingIntent.getService(context, (int)reminderId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		//am.cancel(notifyPintent);
		am.set(AlarmManager.RTC_WAKEUP, reminder.getDate().getTimeInMillis(), notifyPintent);
	}
	
	public void deleteNotification(Context context, long reminderId)
	{
		Intent notifyIntent = new Intent(context,NotifyService.class);
		PendingIntent notifyPintent = PendingIntent.getService(context, (int)reminderId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		am.cancel(notifyPintent);
	}
	
	public void addNotification(Context context, long reminderId)
	{
		//Sets a notification according to the reminder properties through reminderId.
		Intent notifyIntent = new Intent(context,NotifyService.class);
		Reminder reminder = new Reminder();
		SQLSelector selector = new SQLSelector(context);
		reminder = selector.ReminderById(reminderId);
		notifyIntent.putExtra("simplereminder.services.ID", reminderId);
		PendingIntent notifyPintent = PendingIntent.getService(context, (int)reminderId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		Log.d("reqCode pending intent:",""+ reminderId);
		
		AlarmManager am = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, reminder.getDate().getTimeInMillis(), notifyPintent);
	}
	
	
	public void setListOnScreen(final Context context)
	{
		SQLSelector selector = new SQLSelector(context);
		Cursor c = selector.SelectAll();
		while(!c.isAfterLast())
		{
			Log.d("Reminders: " ,"ID: " + c.getLong(0) + " HeadLine: "+ c.getString(1) + 
					" Date: " + c.getString(2) + " IsDaily: " + c.getInt(3) + 
					" Details: " + c.getString(4));
			c.moveToNext();
		}
		c = selector.SelectAll();
		final ArrayList<Reminder> reminders = selector.dbToList(c);
		Intent intent = new Intent(context, FullReminderActivity.class);
		final SQLSelector selector2 = new SQLSelector(context);
		
		//When an item in the list is pressed
		RemindersActivity.lvReminders.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View item, int position,
					long id) {   
				Reminder reminder = reminders.get(position);
				Intent intent = new Intent(context,FullReminderActivity.class);
				intent.putExtra("simplereminder.ui.POSITION",position);
				long reminderId = selector2.SelectOffset(1, position).getLong(0);
				intent.putExtra("simplereminder.ui.ID", reminderId);	
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Log.d("ID of item clicked:", ""+reminderId);
				context.startActivity(intent);
			}
		});
		
		
		
		MyAdapter adapter = new MyAdapter(context, reminders);
		RemindersActivity.lvReminders.setAdapter(adapter);
	}


	

	
	

}
