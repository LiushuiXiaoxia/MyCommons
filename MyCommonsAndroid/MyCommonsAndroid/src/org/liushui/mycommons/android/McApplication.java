package org.liushui.mycommons.android;

import java.lang.Thread.UncaughtExceptionHandler;

import org.liushui.mycommons.android.exception.McException;
import org.liushui.mycommons.android.log.McLog;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * MyCommons全局上下文，使用是需要在App上下文中初始化<br>
 * 比如如下所示：
 * 
 * <pre>
 * public class AppContext extends McApplication {
 * *	private static AppContext instance;
 *
 *	public static synchronized AppContext getInstance() {
 *		return instance;
 *	}
 *
 *	public void onCreate() {
 *		super.onCreate();
 *		instance = this
 *	}
 *}
 * </pre>
 * 
 * Title: McApplication.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013-5-8<br>
 * Version:v1.0
 */
public class McApplication extends Application {
	private static McApplication instance;

	/**
	 * 获取实例化对象
	 * 
	 * @return
	 */
	public static McApplication getMcAppInstance() {
		if (instance == null) {
			throw new McException("McApplication is null.");
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see android.content.ContextWrapper#attachBaseContext(android.content.Context)
	 */
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		instance = this;
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	public void onCreate() {
		super.onCreate();
		McLog.m(this, "onCreate");
		Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable th) {
				McApplication.this.uncaughtException(thread, th);
			}
		});
	}

	/**
	 * 处理全局未经处理异常
	 * 
	 * @param thread
	 * @param th
	 */
	protected void uncaughtException(Thread thread, Throwable th) {
		Log.i(getPackageName(), this + "..............uncaughtException");
		Log.e(getPackageName(), thread.toString());
		Log.e(getPackageName(), "Throwable", th);
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onTerminate()
	 */
	public void onTerminate() {
		super.onTerminate();
		McLog.m(this, "onTerminate");
	}

	/* (non-Javadoc)
	 * @see android.app.Application#onLowMemory()
	 */
	public void onLowMemory() {
		super.onLowMemory();
		McLog.m(this, "onLowMemory");
	}
}