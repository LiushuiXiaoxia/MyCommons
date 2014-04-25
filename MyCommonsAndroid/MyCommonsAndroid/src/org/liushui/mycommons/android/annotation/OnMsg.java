package org.liushui.mycommons.android.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnMsg {
	/**
	 * 在UI线程上
	 * 
	 * @return
	 */
	public boolean ui() default true;

	/**
	 * 监听的msg
	 * 
	 * @return
	 * 
	 */
	public int[] msg();

	/**
	 * 监听之前的发布的事件
	 * 
	 * @return
	 */
	public boolean onBefore() default true;

	/**
	 * 如果发布线程不在主线程是否在新的进程上
	 * 
	 * @return
	 */
	public boolean newThread() default true;

	/**
	 * 当为activity时返回时自动取消监听
	 * 
	 * @return
	 */
	public boolean autoUnRegist() default true;
}