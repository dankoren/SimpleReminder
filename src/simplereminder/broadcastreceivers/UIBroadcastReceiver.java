package simplereminder.broadcastreceivers;

import com.example.simplereminder2.R;

import simplereminder.logics.ReminderManager;
import simplereminder.ui.RemindersActivity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

public class UIBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
			Log.d("UIBroadcast Received", "Refresh List");
			new ReminderManager().setListOnScreen(context);
	}
	

}
