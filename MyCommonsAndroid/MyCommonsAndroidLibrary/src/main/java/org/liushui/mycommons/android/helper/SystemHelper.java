package org.liushui.mycommons.android.helper;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.McApplication;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SystemHelper {

    private SystemHelper() {

    }

    public static int getTitleBarHeight() {
        int id = 0;
        try {
            Class<?> cls = Class.forName("com.android.internal.R$dimen");
            Field field = cls.getField("status_bar_height");
            id = field.getInt(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (int) McApplication.getMcAppInstance().getResources().getDimension(id);
    }

    public static boolean hideSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) McApplication.getMcAppInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static boolean showSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) McApplication.getMcAppInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.showSoftInput(v, 0);
    }
}