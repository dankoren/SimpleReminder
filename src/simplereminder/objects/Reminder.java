package simplereminder.objects;


import java.util.GregorianCalendar;
public class Reminder {
	
	//int id;
	private String headLine;
	private GregorianCalendar date;
	private int isDaily;
	private String details;

	public Reminder()
	{
		headLine = "";
		date = new GregorianCalendar();
		isDaily = 0;
		details = "";
	}
	
	public Reminder(String hLine, GregorianCalendar da, int isDay, String det)
	{
		this.headLine = hLine;
		this.date = da;
		this.isDaily = isDay;
		this.details = det;
	}
	/*public Reminder(int id, String hLine, GregorianCalendar da, int isDay, String det)
	{
		this.id = id;
		this.headLine = hLine;
		this.date = da;
		this.isDaily = isDay;
		this.details = det;
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}*/
	
	public String getHeadLine() {
		return headLine;
	}


	public void setHeadLine(String headLine) {
		this.headLine = headLine;
	}


	public GregorianCalendar getDate() {
		return date;
	}


	public void setDate(GregorianCalendar date) {
		this.date = date;
	}


	public int getIsDaily() {
		return isDaily;
	}
	
	public void setIsDaily(int isDaily) {
		this.isDaily = isDaily;
	}

	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}



}
