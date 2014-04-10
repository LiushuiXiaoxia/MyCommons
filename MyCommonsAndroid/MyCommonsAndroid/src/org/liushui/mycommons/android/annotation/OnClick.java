package org.liushui.mycommons.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title: OnClick.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2014年1月8日<br>
 * Version:v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick
{

	public int[] value();

	public int parentId() default 0;
}