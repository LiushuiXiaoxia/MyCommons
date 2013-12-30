package org.liushui.mycommons.android.util;

import org.liushui.mycommons.android.McApplication;

/**
 * Title: McDimenUtil.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-9-17<br>
 * Version:v1.0
 */
public class McDimenUtil {

	public static int px2Dp(int px) {
		return (int) (px * McApplication.DIMEN_RATE + 0.5F);
	}

	public static int dp2Px(int dp) {
		return (int) (dp / McApplication.DIMEN_RATE  + 0.5F);
	}
}