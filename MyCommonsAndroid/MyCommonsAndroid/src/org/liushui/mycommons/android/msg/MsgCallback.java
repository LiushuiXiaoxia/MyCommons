package org.liushui.mycommons.android.msg;

import android.os.Message;

public interface MsgCallback {

	boolean handleMsg(Message msg);
}