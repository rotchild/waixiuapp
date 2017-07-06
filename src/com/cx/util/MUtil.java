package com.cx.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MUtil {
public static String getStrTime(String timeStamp){
	String timeStr=null;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	long timeStampLong=Long.valueOf(timeStamp);
	timeStr=sdf.format(new Date(timeStampLong));
	return timeStr;
}

public static String getDetailTime(String timeStamp){
	String timeStr=null;
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd  HH:mm");
	long timeStampLong=Long.valueOf(timeStamp);
	timeStr=sdf.format(new Date(timeStampLong));
	return timeStr;
}

}
