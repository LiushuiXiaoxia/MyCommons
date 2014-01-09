package org.liushui.mycommons.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Title: ViewInject.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013年8月8日<br>
 * Version:v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {

	public int value() default 0;

	/**
	 * 使用value代替
	 * @return
	 */
	public int id() default 0;

	//	public String click() default "";
	//
	//	public String longClick() default "";
	//
	//	public String itemClick() default "";
	//
	//	public String itemLongClick() default "";
}