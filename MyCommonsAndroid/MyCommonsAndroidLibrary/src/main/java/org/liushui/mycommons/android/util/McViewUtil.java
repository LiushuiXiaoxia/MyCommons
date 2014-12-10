package org.liushui.mycommons.android.util;

import android.view.View;

import org.liushui.mycommons.android.log.McLog;

public class McViewUtil {

    private McViewUtil() {

    }

    public static void show(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        } else {
            McLog.i("view is null.");
        }
    }

    public static boolean isShow(View view) {
        if (view != null) {
            return view.getVisibility() == View.VISIBLE;
        } else {
            return false;
        }
    }

    public static void hiden(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        } else {
            McLog.i("view is null.");

        }
    }

    public static void showORHiden(View view, boolean b) {
        if (view != null) {
            int i = b ? View.VISIBLE : View.GONE;
            view.setVisibility(i);
        } else {
            McLog.i("view is null.");
        }
    }

    public static void showOrInvisiable(View view, boolean b) {
        if (view != null) {
            int i = b ? View.VISIBLE : View.INVISIBLE;
            view.setVisibility(i);
        } else {
            McLog.i("view is null.");
        }
    }

    public static void invisible(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        } else {
            McLog.i("view is null.");
        }
    }

    public static void toggle(View view) {
        showORHiden(view, !isShow(view));
    }
}