package org.liushui.mycommons.android.msg;

import java.util.ArrayList;
import java.util.List;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.log.McLog;

public class McMsgQueuesHandle {

    private int msgCmd;
    private List<MsgCallbackImpl> callbacks;
    private McMsg lastMsg;

    public McMsgQueuesHandle(int cmd) {
        this.msgCmd = cmd;
        callbacks = new ArrayList<MsgCallbackImpl>();
    }

    public boolean doLastMsg() {
        if (lastMsg != null) {
            handleMsg(lastMsg);
            return true;
        }
        return false;
    }

    public boolean handleMsg(final McMsg msg) {
        if (msg == null) {
            return false;
        }

        for (final MsgCallbackImpl callback : callbacks) {
            if (callback.isRunInUI()) {
                McApplication.getMcAppInstance().post(new Runnable() {
                    public void run() {
                        callback.handleMsg(msg.message);
                    }
                });
            } else {
                callback.handleMsg(msg.message);
            }
        }
        if (callbacks.isEmpty()) {
            McLog.i("Msg " + msg + " do not have handle.");
        }
        lastMsg = msg;
        return false;
    }

    public int getMsgCmd() {
        return msgCmd;
    }

    /**
     * 添加回调
     *
     * @param callback
     * @return 添加成功返回true，如果存在，则添加失败，返回false
     */
    public boolean addCallback(MsgCallbackImpl callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
            return true;
        }
        return false;
    }

    public void remove(OnMsgCallback callback) {
        if (callback == null) {
            return;
        }
        for (int i = callbacks.size() - 1; i >= 0; i--) {
            MsgCallbackImpl impl = callbacks.get(i);
            if (impl.getCallback().equals(callback)) {
                callbacks.remove(i);
            }
        }
    }

    public void clearCallback() {
        callbacks.clear();
    }

    public void clearLastMsg() {
        lastMsg = null;
    }

    public boolean contains(OnMsgCallback callback) {
        return callbacks.contains(callback);
    }

    public List<MsgCallbackImpl> getCallbacks() {
        return callbacks;
    }
}