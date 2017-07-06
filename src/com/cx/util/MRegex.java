package com.cx.util;

import android.text.TextUtils;

public class MRegex {
public static boolean isIPV4(String ipStr){
	String ipRegex="^(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])$";
	if(TextUtils.isEmpty(ipStr)) return false;
	else return ipStr.matches(ipRegex);
}

public static boolean isPort(String portStr){
	String portRegex="^[0-9]{1,5}$";
	if(TextUtils.isEmpty(portStr)) return false;
	else return portStr.matches(portRegex);
}
}
