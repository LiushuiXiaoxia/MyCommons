package org.liushui.mycommons.android.annotation.helper;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.exception.McException;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

/**
 * Title: InjectHelper.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013年8月8日<br>
 * Version:v1.0
 */
public class InjectHelper {

	/**
	 * 初始化
	 * 
	 * @param obj 标注所在的对象
	 * @param container view所在的容器
	 */
	public static void init(Object obj, Object container) {
		Field[] fields = obj.getClass().getDeclaredFields();
		View v = getView(container);
		if (v == null) {
			throw new McException("container can't find view.");
		}
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					int viewId = viewInject.id();
					try {
						field.setAccessible(true);
						field.set(obj, v.findViewById(viewId));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	static View getView(Object container) {
		View v = null;
		if (container instanceof View) {
			v = (View) container;
		} else if (container instanceof Activity) {
			Activity a = (Activity) container;
			v = a.getWindow().getDecorView();
		} else if (container instanceof Fragment) {
			Fragment f = (Fragment) container;
			v = f.getView();
		} else if (container instanceof android.support.v4.app.Fragment) {
			android.support.v4.app.Fragment f = (android.support.v4.app.Fragment) container;
			v = f.getView();
		}
		return v;
	}
}