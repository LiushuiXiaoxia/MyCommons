package org.liushui.mycommons.android.annotation.helper;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnClick;
import org.liushui.mycommons.android.annotation.OnMsg;
import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.exception.McException;
import org.liushui.mycommons.android.log.McLog;
import org.liushui.mycommons.android.msg.MsgCallback;

import android.view.View;
import android.view.View.OnClickListener;

class Helper {
	Object obj;
	View container;
	Field[] fields;

	Helper(Object obj, View container) {
		super();
		this.obj = obj;
		this.container = container;
		if (container == null) {
			throw new McException("container is null.");
		}
	}

	void init() {
		fields = obj.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null) {
					doViewInject(viewInject, field);
				}

				OnClick onClick = field.getAnnotation(OnClick.class);
				if (onClick != null) {
					doOnClick(onClick, field);
				}

				OnMsg onMsg = field.getAnnotation(OnMsg.class);
				if (onMsg != null) {
					doMsg(onMsg, field);
				}
			}
		}
	}

	void doViewInject(ViewInject viewInject, Field field) {
		int viewId = viewInject.value();
		int parentId = viewInject.parentId();
		if (viewId == 0) {
			viewId = viewInject.id();
		}
		String msg = String.format("%s can't find %s (pId = %s, vId = %s)", container, field.getName(), parentId, viewId);
		try {
			View view = findView(viewId, parentId, field.getName());
			field.setAccessible(true);
			field.set(obj, view);
		} catch (Exception e) {
			McLog.e(msg);
		}
	}

	// 设置click回调
	void doOnClick(OnClick click, Field field) {
		int[] values = click.value();
		int parentId = click.parentId();

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
			View view = findView(id, parentId, field.getName());
			if (view != null) {
				view.setOnClickListener((OnClickListener) fieldValue);
			}
		}
	}

	View findView(int id, int pid, String name) {
		View temp = container;
		if (pid != 0) {
			temp = container.findViewById(pid);
			if (temp == null) {
				String msg = String.format("field(name = %s ,id = %s) can't find parent (pId = %s)", name, id, pid);
				McLog.e(msg);
				return null;
			}
		}
		View v = temp.findViewById(id);
		if (v == null) {
			String msg = String.format("%s can't find %s (pId = %s, vId = %s)", container, name, pid, id);
			McLog.e(msg);
		}
		return v;
	}

	void doMsg(OnMsg onMsg, Field field) {
		int[] msgs = onMsg.msg();

		field.setAccessible(true);
		try {
			Object value = field.get(obj);
			if (value instanceof MsgCallback) {
				
			} else {
				McLog.e(field.getName() + " in " + obj + " is not MsgCallback instance.");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
