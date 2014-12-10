package org.liushui.mycommons.android.util;

import org.liushui.mycommons.android.McApplication;

import android.widget.Toast;

/**
 * McToastUtil.java<br/>
 * Date:2011-9-14 <br/>
 * Author:XiaQiulei <br/>
 * Version:v1.0 <br/>
 * Title:
 */
public class McToastUtil {

    private McToastUtil() {

    }

    private static Toast toast;

    static {
        toast = Toast.makeText(McApplication.getMcAppInstance(), "", Toast.LENGTH_SHORT);
    }

    public static void show(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void show(int textRes) {
        show(textRes, Toast.LENGTH_SHORT);
    }

    /**
     * @param text 显示内容
     * @param t    显示时间
     */
    public static void show(String text, int t) {
        //toast.cancel();
        toast.setDuration(t);
        toast.setText(text);
        toast.show();
    }

    public static void show(int textRes, int t) {
        // toast.cancel();
        toast.setDuration(t);
        toast.setText(textRes);
        toast.show();
    }
}