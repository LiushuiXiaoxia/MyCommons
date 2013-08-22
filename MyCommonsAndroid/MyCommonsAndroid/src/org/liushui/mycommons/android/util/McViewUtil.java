package org.liushui.mycommons.android.util;

import android.view.View;

public class McViewUtil {

	public static void show(View view) {
		if (view != null) {
			view.setVisibility(View.VISIBLE);
		} else {

		}
	}

	public static boolean isShow(View view) {
		if (view != null) {
			return view.getVisibility() == View.INVISIBLE;
		} else {
			return false;
		}
	}

	public static void hiden(View view) {
		if (view != null) {
			view.setVisibility(View.GONE);
		} else {

		}
	}

	public static void showORHiden(View view, boolean b) {
		if (view != null) {
			int i = b ? View.VISIBLE : View.GONE;
			view.setVisibility(i);
		} else {

		}
	}

	public static void showOrInvisiable(View view, boolean b) {
		if (view != null) {
			int i = b ? View.VISIBLE : View.INVISIBLE;
			view.setVisibility(i);
		} else {

		}
	}

	public static void invisible(View view) {
		if (view != null) {
			view.setVisibility(View.INVISIBLE);
		} else {

		}
	}
}