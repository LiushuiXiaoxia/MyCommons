package org.liushui.mycommons.android.net;

import org.liushui.mycommons.android.McApplication;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Title: UtilNet.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-7-31<br>
 * Version:v1.0
 * 网络工具类
 */
public class McUtilNet {

	/**
	 * 网络是否已经连接
	 * 
	 * @return
	 */
	public static boolean isConnectInternet() {
		Application app = McApplication.getMcAppInstance();
		ConnectivityManager conManager = (ConnectivityManager) app.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}
		return false;
	}
}