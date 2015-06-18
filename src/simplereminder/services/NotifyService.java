package simplereminder.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import simplereminder.dal.SQLDeleter;
import simplereminder.dal.SQLSelector;
import simplereminder.dal.SQLUpdater;
import simplereminder.logics.ReminderManager;
import simplereminder.objects.Reminder;
import simplereminder.ui.RemindersActivity;

import com.example.simplereminder2.R;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

@SuppressLint("NewApi")
public class NotifyService extends Service {

	@Override
	public void onCreate() {

	}
	
	@SuppressWarnings("deprecation")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		// build notification
		long id = intent.getLongExtra("simplereminder.services.ID", -1);
		SQLSelector selector = new SQLSelector(this);
		Reminder reminder = new Reminder();
		reminder = selector.ReminderById(id);
		if(reminder.getHeadLine()!=null)
		{
			NotificationCompat.Builder n  = new NotificationCompat.Builder(this)
			        .setContentTitle(reminder.getHeadLine())
			        .setContentText(reminder.getDetails())
			        .setSmallIcon(R.drawable.ic_launcher);
			
			//        n.defaults = Notification.DEFAULT_SOUND;	
			//n.defaults = Notification.DEFAULT_LIGHTS;
			NotificationManager notificationManager = 
					  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			notificationManager.notify((int)id, n.build());
			Intent resultIntent = new Intent(this,RemindersActivity.class);
			resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
			n.setContentIntent(resultPendingIntent);
			
			
			//Deletes the reminder 
			if(reminder.getIsDaily() == 0)
			{
				SQLDeleter deleter = new SQLDeleter(this);
				deleter.deleteReminder(id);
			}
			else
			{
				SQLUpdater updater = new SQLUpdater(this);
				Reminder newReminder = reminder;
				GregorianCalendar newDate;
				newDate = reminder.getDate();
				//newDate.set(Calendar.DAY_OF_MONTH, newDate.get(Calendar.DAY_OF_MONTH) + 1);
				newDate.set(Calendar.MINUTE, newDate.get(Calendar.MINUTE)+1);
				newReminder.setDate(newDate);
				updater.updateReminder(id, newReminder);
				new ReminderManager().addNotification(this, id);
				
			}
			
			//Sends a broadcast message that the notification has been created
			Intent i = new Intent("simplereminder.broadcastreceivers.UI_CHANGE");
		    //i.setAction("simplereminder.ui.NOTIFY");
			this.sendBroadcast(i);
			
		}
		this.stopSelf();
		return START_NOT_STICKY;
	}
	
	
	
	

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	//private final IBinder mBinder = new ServiceBinder();

	
	
	
}

