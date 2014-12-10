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
     */
    public int[] msg();

    /**
     * 是否使用上一个事件
     *
     * @return
     */
    public boolean useLastMsg() default true;
}