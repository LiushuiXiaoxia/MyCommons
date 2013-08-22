package org.liushui.mycommons.android.util;

import org.liushui.mycommons.android.McApplication;

import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Title: UtilDimen.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-9-17<br>
 * Version:v1.0
 */
public class McDimenUtil {

	public int SCREEN_WIDTH = -1;

	public int SCREEN_HEIGHT = -1;

	public float DIMEN_RATE = -1;
	private Context context;
	private Point point;
	private boolean isInit = false;

	public McDimenUtil() {
		this.context = McApplication.getMcAppInstance();
		init();

		SCREEN_WIDTH = point.x;
		SCREEN_HEIGHT = point.y;
	}

	void init() {
		if (!isInit) {
			WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			point = new Point();
			display.getSize(point);

			DisplayMetrics dm = new DisplayMetrics();
			windowManager.getDefaultDisplay().getMetrics(dm);
			DIMEN_RATE = dm.density / 1.0F;
			isInit = true;
		}
	}

	public boolean isLandspace() {
		init();
		int w = point.x;
		int h = point.y;
		return h < w;
	}

	public int getWidth() {
		init();
		int w = point.x;
		return w;
	}

	public int getHeight() {
		init();
		int h = point.y;
		return h;
	}
}