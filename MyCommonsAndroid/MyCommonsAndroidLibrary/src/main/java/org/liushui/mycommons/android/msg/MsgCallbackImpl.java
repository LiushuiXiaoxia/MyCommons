package org.liushui.mycommons.android.msg;

import org.liushui.mycommons.android.annotation.OnMsg;

import android.os.Message;

public class MsgCallbackImpl implements OnMsgCallback {

    int msgCmd;
    OnMsg onMsg;
    OnMsgCallback callback;

    public MsgCallbackImpl(int msgCmd, OnMsg onMsg, OnMsgCallback callback) {
        super();
        this.msgCmd = msgCmd;
        this.onMsg = onMsg;
        this.callback = callback;
    }

    public boolean isRunInUI() {
        return onMsg.ui();
    }

    public boolean handleMsg(Message msg) {
        return callback.handleMsg(msg);
    }
}