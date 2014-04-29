package org.liushui.mycommons.android.msg;

import android.os.Message;

public class McMsg {
	public long time;
	public int msgCmd;
	public Message message;

	public String toString() {
		return "McMsg [time=" + time + ", msgCmd=" + msgCmd + ", message=" + message + "]";
	}
}