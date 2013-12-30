package org.liushui.mycommons.android.annotation.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.exception.McException;
import org.liushui.mycommons.android.log.McLog;

import android.app.Activity;
import android.view.View;

/**
 * Title: InjectHelper.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013年8月8日<br>
 * Version:v1.0
 */
public class InjectHelper {
	static Class<?> appFragmentCls;
	static Class<?> v4AppFragmentCls;

	static Method appFragmentGetViewMethod;
	static Method v4FragmentGetViewMethod;
	static {
		try {
			appFragmentCls = Class.forName("android.app.Fragment");
			appFragmentGetViewMethod = appFragmentCls.getDeclaredMethod("getView");
		} catch (Exception e) {
			McLog.w(e);
		}
		try {
			v4AppFragmentCls = Class.forName("android.support.v4.app.Fragment");
			v4FragmentGetViewMethod = v4AppFragmentCls.getDeclaredMethod("getView");
		} catch (Exception e) {
			McLog.w(e);
		}
	}

	/**
	 * 初始化
	 * 
	 * @param obj
	 *            标注所在的对象
	 * @param container
	 *            view所在的容器
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
						McLog.e(container + " can't find " + field.getName() + "(" + viewId + ")", e);
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
		} else if (appFragmentCls != null && appFragmentCls.isInstance(container)) {
			if (appFragmentGetViewMethod != null) {
				try {
					v = (View) appFragmentGetViewMethod.invoke(container);
				} catch (Exception e) {
					McLog.e("", e);
				}
			}
		} else if (v4AppFragmentCls != null && v4AppFragmentCls.isInstance(container)) {
			if (v4FragmentGetViewMethod != null) {
				try {
					v = (View) v4FragmentGetViewMethod.invoke(container);
				} catch (Exception e) {
					McLog.e("", e);
				}
			}
		}
		return v;
	}
}