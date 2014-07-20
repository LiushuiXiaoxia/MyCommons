package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnScroll;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;
import org.liushui.mycommons.android.log.McLog;

import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class OnScrollHelper extends BaseHelper<OnScroll>
{

	public OnScrollHelper(Object obj, View container)
	{
		super(obj, container);
	}

	public void doHelp(OnScroll t, Field field, String fieldName, Object fieldValue)
	{
		int[] values = t.value();
		int parentId = t.parentId();

		for (int id : values)
		{
			View view = findView(id, parentId, fieldName);
			if (view != null)
			{
				if (view instanceof AbsListView)
				{
					AbsListView cb = (AbsListView) view;
					cb.setOnScrollListener((OnScrollListener) fieldValue);
				} else
				{
					McLog.w("view(" + view + ") is not instance of AbsListView.");
				}
			}
		}
	}
}