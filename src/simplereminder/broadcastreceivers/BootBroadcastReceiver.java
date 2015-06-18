package simplereminder.broadcastreceivers;

import simplereminder.dal.SQLSelector;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		SQLSelector selector = new SQLSelector(context);
		selector.setAlarms(context);
		
	}
	
	

}
