package org.liushui.mycommons.android.msg;

import android.os.Message;

public class MsgHelper {

	private static MsgHelper instance = new MsgHelper();

	public static MsgHelper getInstance() {
		return instance;
	}

	private MsgHelper() {

	}

	public void sendMsg(int cmd) {
		McMsg mm = new McMsg();
		mm.message = new Message();
		mm.message.what = mm.msgCmd = cmd;
		mm.time = System.currentTimeMillis();
		sendMsg(mm);
	}

	public void sendMsg(McMsg mm) {

	}

	public void registMsg(Object obj) {

	}

	public void unRegistMsg(Object obj) {

	}

	public void clearMsgs() {

	}
}