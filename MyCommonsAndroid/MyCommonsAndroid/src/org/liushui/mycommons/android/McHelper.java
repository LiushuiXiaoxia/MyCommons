package org.liushui.mycommons.android;

import java.lang.annotation.Annotation;

import org.liushui.mycommons.android.annotation.OnMsg;

public class McHelper {

	public static OnMsg makeOnMsg(final int[] msg, final boolean ui, final boolean useLast) {
		return new OnMsg() {

			public Class<? extends Annotation> annotationType() {
				return OnMsg.class;
			}

			public boolean useLastMsg() {
				return useLast;
			}

			public boolean ui() {
				return ui;
			}

			public int[] msg() {
				return msg;
			}
		};
	}
}