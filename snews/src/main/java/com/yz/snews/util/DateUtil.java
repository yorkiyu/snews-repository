package com.yz.snews.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date timeStamp2Date(String timeStamp) {
		SimpleDateFormat format =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Long time=Long.parseLong(timeStamp);  
		String resultFormat = format.format(time*1000);  
		try {
			Date date = format.parse(resultFormat);
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	public static String getTimeStamp() {
		return Long.toString(System.currentTimeMillis());   
	}
}
