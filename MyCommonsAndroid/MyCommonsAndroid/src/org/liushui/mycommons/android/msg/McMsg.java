package org.liushui.mycommons.android.msg;

import android.os.Message;

public class McMsg {
	public long time;
	public final int msgCmd;
	public Message message;

	private McMsg(int cmd) {
		msgCmd = cmd;
	}

	public String toString() {
		return "McMsg [time=" + time + ", msgCmd=" + msgCmd + ", message=" + message + "]";
	}

	public static McMsg newInstance(int cmd) {
		return newInstance(cmd, null);
	}

	public static McMsg newInstance(int cmd, Object data) {
		McMsg mm = new McMsg(cmd);
		mm.time = System.currentTimeMillis();
		Message msg = new Message();
		msg.obj = data;
		msg.what = cmd;
		mm.message = msg;
		return mm;
	}
}