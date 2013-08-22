package org.liushui.mycommons.android.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.liushui.mycommons.android.McApplication;

import android.content.Context;
import android.text.format.DateFormat;

/**
 * Title: McDateUtil.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013-1-28<br>
 * Version:v1.0
 */
public class McDateUtil {

	public static String format(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatDateString(Date date) {
		Context context = McApplication.getMcAppInstance();
		java.text.DateFormat dateFormat = DateFormat.getDateFormat(context);
		java.text.DateFormat timeFormat = DateFormat.getTimeFormat(context);
		return dateFormat.format(date) + " " + timeFormat.format(date);
	}

	public static String formatDateString(long time) {
		return formatDateString(new Date(time));
	}
}