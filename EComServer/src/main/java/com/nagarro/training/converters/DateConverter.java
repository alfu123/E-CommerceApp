package com.nagarro.training.converters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateConverter {
	public static String covertDaysIntoDate(int d) {
		// get a calendar Instance
		Calendar c = Calendar.getInstance();
		// Set calendar time to today's date
		c.setTime(new Date());
		// add dates in todays days
		c.add(Calendar.DATE, d);
		// create a SimpleDateFormat object
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		// format the date into String
		return sdf.format(c.getTime());
	}
}
