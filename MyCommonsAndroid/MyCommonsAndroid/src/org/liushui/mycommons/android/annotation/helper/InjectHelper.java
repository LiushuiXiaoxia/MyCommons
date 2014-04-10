package org.liushui.mycommons.android.annotation.helper;

import android.app.Activity;
import android.view.View;

/**
 * Title: InjectHelper.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013年8月8日<br>
 * Version:v1.0
 */
public class InjectHelper
{

	/**
	 * 初始化
	 * 
	 * @param obj 标注所在的对象
	 * @param container view所在的容器
	 */
	public static void init(Object obj, Activity container)
	{
		init(obj, container.getWindow().getDecorView());
	}

	/**
	 * 初始化
	 * 
	 * @param obj 标注所在的对象
	 * @param container view所在的容器
	 */
	public static void init(Object obj, View container)
	{
		new Helper(obj, container).init();
	}
}