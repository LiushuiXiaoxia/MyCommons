package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;
import org.liushui.mycommons.android.log.McLog;

import android.view.View;

public class ViewInjectHelper extends BaseHelper<ViewInject> {

	public ViewInjectHelper(Object obj, View container) {
		super(obj, container);
	}

	public void doHelp(ViewInject t, Field field, String fieldName, Object fieldValue) {
		int viewId = t.value();
		int parentId = t.parentId();
		if (viewId == 0) {
			viewId = t.id();
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
}