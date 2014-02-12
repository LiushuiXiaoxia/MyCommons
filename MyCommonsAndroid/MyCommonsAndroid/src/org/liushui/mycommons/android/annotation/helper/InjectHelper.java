package org.liushui.mycommons.android.annotation.helper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.liushui.mycommons.android.annotation.OnClick;
import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.exception.McException;
import org.liushui.mycommons.android.log.McLog;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Title: InjectHelper.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013年8月8日<br>
 * Version:v1.0
 */
public class InjectHelper {
	//	static Class<?> appFragmentCls;
	//	static Class<?> v4AppFragmentCls;
	//
	//	static Method appFragmentGetViewMethod;
	//	static Method v4FragmentGetViewMethod;
	//	static {
	//		try {
	//			appFragmentCls = Class.forName("android.app.Fragment");
	//			appFragmentGetViewMethod = appFragmentCls.getDeclaredMethod("getView");
	//		} catch (Exception e) {
	//			McLog.w(e);
	//		}
	//		try {
	//			v4AppFragmentCls = Class.forName("android.support.v4.app.Fragment");
	//			v4FragmentGetViewMethod = v4AppFragmentCls.getDeclaredMethod("getView");
	//		} catch (Exception e) {
	//			McLog.w(e);
	//		}
	//	}

	/**
	 * 初始化
	 * 
	 * @param obj
	 *            标注所在的对象
	 * @param container
	 *            view所在的容器
	 */
	public static void init(Object obj, Activity container) {
		init(obj, container.getWindow().getDecorView());
	}

	/**
	 * 初始化
	 * 
	 * @param obj
	 *            标注所在的对象
	 * @param container
	 *            view所在的容器
	 */
	public static void init(Object obj, View container) {
		Field[] fields = obj.getClass().getDeclaredFields();
		View v = container;
		if (v == null) {
			throw new McException("container is null.");
		}
		SparseArray<View> views = new SparseArray<View>();
		List<Field> clicks = new ArrayList<Field>();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					int viewId = viewInject.value();
					if (viewId == 0) {
						viewId = viewInject.id();
					}
					try {
						field.setAccessible(true);
						View view = v.findViewById(viewId);
						field.set(obj, view);
						views.put(viewId, view);
						if (view == null) {
							McLog.e(container + " can't find " + field.getName() + "(" + viewId + ")");
						}
					} catch (Exception e) {
						McLog.e(container + " can't find " + field.getName() + "(" + viewId + ")", e);
					}
				} else {
					OnClick click = field.getAnnotation(OnClick.class);
					if (click != null) {
						clicks.add(field);
					}
				}
			}
		}
		// 设置click回调
		for (Field field : clicks) {
			OnClick click = field.getAnnotation(OnClick.class);
			if (click != null) {
				int[] values = click.value();
				field.setAccessible(true);
				Object fieldValue = null;
				try {
					fieldValue = field.get(obj);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				for (int id : values) {
					View view = views.get(id);
					if (view != null) {
						view.setOnClickListener((OnClickListener) fieldValue);
					}
				}
			}
		}
	}

	//	static View getView(Object container) {
	//		View v = null;
	//		if (container instanceof View) {
	//			v = (View) container;
	//		} else if (container instanceof Activity) {
	//			Activity a = (Activity) container;
	//			v = a.getWindow().getDecorView();
	//		} else if (appFragmentCls != null && appFragmentCls.isInstance(container)) {
	//			if (appFragmentGetViewMethod != null) {
	//				try {
	//					v = (View) appFragmentGetViewMethod.invoke(container);
	//				} catch (Exception e) {
	//					McLog.e("", e);
	//				}
	//			}
	//		} else if (v4AppFragmentCls != null && v4AppFragmentCls.isInstance(container)) {
	//			if (v4FragmentGetViewMethod != null) {
	//				try {
	//					v = (View) v4FragmentGetViewMethod.invoke(container);
	//				} catch (Exception e) {
	//					McLog.e("", e);
	//				}
	//			}
	//		}
	//		return v;
	//	}
}