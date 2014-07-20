package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnFocusChange;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;

import android.view.View;
import android.view.View.OnFocusChangeListener;

public class OnOnFocusChangeHelper extends BaseHelper<OnFocusChange>
{

	public OnOnFocusChangeHelper(Object obj, View container)
	{
		super(obj, container);
	}

	public void doHelp(OnFocusChange a, Field field, String fieldName, Object fieldValue)
	{
		int[] values = a.value();
		int parentId = a.parentId();

		for (int id : values)
		{
			View view = findView(id, parentId, fieldName);
			if (view != null)
			{
				view.setOnFocusChangeListener((OnFocusChangeListener) fieldValue);
			}
		}
	}
}