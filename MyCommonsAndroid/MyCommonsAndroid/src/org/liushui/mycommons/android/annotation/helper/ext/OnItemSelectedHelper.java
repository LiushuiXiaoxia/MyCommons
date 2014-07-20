package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnItemSelected;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;
import org.liushui.mycommons.android.log.McLog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

public class OnItemSelectedHelper extends BaseHelper<OnItemSelected>
{

	public OnItemSelectedHelper(Object obj, View container)
	{
		super(obj, container);
	}

	public void doHelp(OnItemSelected t, Field field, String fieldName, Object fieldValue)
	{
		int[] values = t.value();
		int parentId = t.parentId();

		for (int id : values)
		{
			View view = findView(id, parentId, fieldName);
			if (view != null)
			{
				if (view instanceof AdapterView)
				{
					AdapterView<?> cb = (AdapterView<?>) view;
					cb.setOnItemSelectedListener((OnItemSelectedListener) fieldValue);
				} else
				{
					McLog.w("view(" + view + ") is not instance of AdapterView.");
				}
			}
		}
	}
}