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
		Field[] fields = obj.getClass().getDeclaredFields();
		if (container == null)
		{
			throw new McException("container is null.");
		}
		SparseArray<View> views = new SparseArray<View>();
		List<Field> clicks = new ArrayList<Field>();
		if (fields != null && fields.length > 0)
		{
			for (Field field : fields)
			{
				ViewInject viewInject = field.getAnnotation(ViewInject.class);
				if (viewInject != null)
				{
					int viewId = viewInject.value();
					int parentId = viewInject.parentId();
					if (viewId == 0)
					{
						viewId = viewInject.id();
					}
					try
					{
						field.setAccessible(true);
						View temp = container;
						if (parentId != 0)
						{
							temp = container.findViewById(parentId);
							if (temp == null)
							{
								String msg = String.format("%s's can't find %s's parentView(pId = %s)", container, field.getName(), parentId);
								McLog.e(msg);
							}
						}
						View view = temp.findViewById(viewId);
						field.set(obj, view);
						views.put(viewId, view);
						if (view == null)
						{
							String msg = String.format("%s can't find %s (pId = %s, vId = %s)", container, field.getName(), parentId, viewId);
							McLog.e(msg);
						}
					} catch (Exception e)
					{
						String msg = String.format("%s can't find %s (pId = %s, vId = %s)", container, field.getName(), parentId, viewId);
						McLog.e(msg);
					}
				}

				OnClick click = field.getAnnotation(OnClick.class);
				if (click != null)
				{
					clicks.add(field);
				}
			}
		}
		// 设置click回调
		for (Field field : clicks)
		{
			OnClick click = field.getAnnotation(OnClick.class);
			if (click != null)
			{
				int[] values = click.value();
				field.setAccessible(true);
				Object fieldValue = null;
				try
				{
					fieldValue = field.get(obj);
				} catch (IllegalArgumentException e)
				{
					e.printStackTrace();
				} catch (IllegalAccessException e)
				{
					e.printStackTrace();
				}
				for (int id : values)
				{
					View view = views.get(id);
					if (view != null)
					{
						view.setOnClickListener((OnClickListener) fieldValue);
					}
				}
			}
		}
	}
}