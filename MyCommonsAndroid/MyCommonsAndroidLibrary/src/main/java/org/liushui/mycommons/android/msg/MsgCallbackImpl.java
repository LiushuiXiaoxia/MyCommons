package org.liushui.mycommons.android.msg;

import org.liushui.mycommons.android.annotation.OnMsg;

import android.os.Message;

public class MsgCallbackImpl implements OnMsgCallback {

    private int msgCmd;
    private OnMsg onMsg;
    private OnMsgCallback callback;

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

    public int getMsgCmd() {
        return msgCmd;
    }

    public void setMsgCmd(int msgCmd) {
        this.msgCmd = msgCmd;
    }

    public OnMsg getOnMsg() {
        return onMsg;
    }

    public void setOnMsg(OnMsg onMsg) {
        this.onMsg = onMsg;
    }

    public OnMsgCallback getCallback() {
        return callback;
    }

    public void setCallback(OnMsgCallback callback) {
        this.callback = callback;
    }
}