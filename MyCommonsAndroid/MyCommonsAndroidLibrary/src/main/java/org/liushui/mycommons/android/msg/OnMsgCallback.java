package org.liushui.mycommons.android.msg;

import android.os.Message;

/**
 * 消息回调
 */
public interface OnMsgCallback {

    /**
     * 处理消息
     *
     * @param msg
     * @return 返回是否消费了消息
     */
    boolean handleMsg(Message msg);
}