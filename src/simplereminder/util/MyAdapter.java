package simplereminder.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import simplereminder.objects.Reminder;

import com.example.simplereminder2.*;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter<Reminder>{

	Context context;
	ArrayList<Reminder> reminders;
	
	
	public MyAdapter(Context context, ArrayList<Reminder> reminders) {
		super(context, R.layout.reminder_list_item , reminders);
		this.context = context;
		this.reminders = reminders;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = 
				(LayoutInflater)context.getSystemService
				(context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.reminder_list_item, parent, false);
		
		TextView headLine = (TextView)rowView.findViewById(R.id.headline_tv);
		headLine.setText(reminders.get(position).getHeadLine());
		
		TextView date = (TextView)rowView.findViewById(R.id.date_tv);
		date.setText(""+ new CalendarWriter().displayDate(reminders.get(position)));
		
		String sDetails = reminders.get(position).getDetails();
		if(sDetails.length() > 0)
		{
			TextView details = (TextView)rowView.findViewById(R.id.details_tv);
			sDetails = reminders.get(position).getDetails();
			if(sDetails.length() > 5)
				sDetails = "" + sDetails.substring(0, 5) + "...";
			details.setText(sDetails);
		} 
		
		return rowView;
	}

}
