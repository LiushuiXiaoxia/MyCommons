package org.liushui.mycommons.android.event;

public @interface OnEvent
{
	/**
	 * 在UI线程上
	 * 
	 * @return
	 */
	public boolean ui() default true;

	/**
	 * 监听的action
	 * 
	 * @return
	 * 
	 */
	public String action();

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