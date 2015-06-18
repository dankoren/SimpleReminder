package simplereminder.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import simplereminder.objects.Reminder;



public class CalendarWriter {
	//An aid class which helps with displaying dates and times for the ui
	
	public CalendarWriter()
	{
		
	}
	
	public String writeDate(Calendar cal)
	{
		//Gets a calendar and returns a proper string format for the date
		String date;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		date = "" + formatter.format(cal.getTime());
		return date;
		
	}

	
	
	
	
	public String writeTime(Calendar cal)
	{
		//Gets a calendar and returns a proper string format for the time
		String time;
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		time = "" + formatter.format(cal.getTime());
		return time;
	}
	
	public String displayDate(Reminder reminder) {
		//Gets a reminder and returns a proper string format for the date & time
		//for a list item
		GregorianCalendar cal = reminder.getDate();
		int isDaily = reminder.getIsDaily();
		String sDate;
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
		if(isDaily == 1)
		{
			return "Every Day at " + timeFormatter.format(cal.getTime());
		}
		else
		{
		GregorianCalendar now = new GregorianCalendar();
		if(cal.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
				cal.get(Calendar.MONTH) == now.get(Calendar.MONTH))
		{
			if (cal.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH))
				return "Today at " + timeFormatter.format(cal.getTime());
			else if(cal.get(Calendar.DAY_OF_MONTH) == now.get(Calendar.DAY_OF_MONTH) + 1)
				return "Tommorow at " + timeFormatter.format(cal.getTime());
		}
		sDate = "" + dateFormatter.format(cal.getTime()) + " at " + timeFormatter.format(cal.getTime());
		return sDate;
		}
	}
	
	public class DayHelper {
		public DayHelper()
		{
			
		}
		public String translateDay(int day_in_week)
		{
			String dayWord;
			switch(day_in_week)
			{
			case 1: dayWord="Sunday";break;
			case 2: dayWord="Monday";break;
			case 3: dayWord="Tuesday";break;
			case 4: dayWord="Wednesday";break;
			case 5: dayWord="Thursday";break;
			case 6: dayWord="Friday";break;
			case 7: dayWord="Saturday";break;
			default: dayWord="null";
			}
			return dayWord;
		}
		
	}



}
