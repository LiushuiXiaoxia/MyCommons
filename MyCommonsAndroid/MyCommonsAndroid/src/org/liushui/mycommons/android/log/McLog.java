package org.liushui.mycommons.android.log;

import java.util.Formatter;
import java.util.Locale;

import org.liushui.mycommons.android.McApplication;

import android.util.Log;

/**
 * McLog.java<br/>
 * Date:2013-5-8 <br/>
 * Author:XiaQiulei <br/>
 * Version:v1.0 <br/>
 * Title:
 */
public class McLog {

	static int LOG_WIDTH_LENGTH = 70;

	static String sTag = "McLog";
	static boolean sIsDebug = true;

	static {
		sTag = McApplication.getMcAppInstance().getPackageName();
	}

	public static void i(String msg) {
		if (sIsDebug) {
			Log.i(sTag, msg);
		}
	}

	public static void i(Object obj) {
		String msg = null;
		if (obj == null) {
			msg = "Obj is null.";
		} else {
			msg = obj.toString();
		}
		i(msg);
	}

	public static void i(String format, Object... args) {
		StringBuffer sb = new StringBuffer();
		Formatter f = new Formatter(sb, Locale.getDefault());
		f.format(format, args);
		f.close();
		i(sb);
	}

	public static void m(Class<?> clazz, String method) {
		m(clazz.getName(), method);
	}

	public static void m(Object obj, String method) {
		if (sIsDebug) {
			int len = obj.toString().length();
			StringBuffer sb = new StringBuffer();
			if (len < LOG_WIDTH_LENGTH) {
				for (int i = 0; i < LOG_WIDTH_LENGTH - len; i++) {
					sb.append(".");
				}
			}
			i(obj + sb.toString() + method);
		}
	}

	public static void logInObj(Object obj, String msg) {
		Class<?> cls = obj.getClass();
		String name = cls.getSimpleName();
		i(name + "--->%s", msg);
	}

	public static void logInObj(Object obj, String format, Object... args) {
		Class<?> cls = obj.getClass();
		String name = cls.getSimpleName();
		format = name + "----->" + format;
		i(format, args);
	}

	public static void e(String msg) {
		if (sIsDebug) {
			Log.e(sTag, msg);
		}
	}

	public static void e(String msg, Throwable t) {
		if (sIsDebug) {
			Log.e(sTag, msg, t);
		}
	}

	public static void setLogTag(String tag) {
		sTag = tag;
	}
}