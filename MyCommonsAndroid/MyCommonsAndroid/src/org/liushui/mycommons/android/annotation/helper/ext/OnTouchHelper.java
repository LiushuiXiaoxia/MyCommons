package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnTouch;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;

import android.view.View;
import android.view.View.OnTouchListener;

public class OnTouchHelper extends BaseHelper<OnTouch>
{

	public OnTouchHelper(Object obj, View container)
	{
		super(obj, container);
	}

	public void doHelp(OnTouch t, Field field, String fieldName, Object fieldValue)
	{
		int[] values = t.value();
		int parentId = t.parentId();

		for (int id : values)
		{
			View view = findView(id, parentId, fieldName);
			if (view != null)
			{
				view.setOnTouchListener((OnTouchListener) fieldValue);
			}
		}
	}
}