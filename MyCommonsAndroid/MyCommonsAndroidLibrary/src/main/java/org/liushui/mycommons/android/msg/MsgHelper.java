package org.liushui.mycommons.android.msg;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnMsg;
import org.liushui.mycommons.android.log.McLog;
import org.liushui.mycommons.android.thread.ThreadWorker;

import android.util.SparseArray;

public class MsgHelper {

    private static MsgHelper instance = new MsgHelper();

    public static MsgHelper getInstance() {
        return instance;
    }

    private SparseArray<McMsgQueuesHandle> msgQueueHandleMap;

    private MsgHelper() {
        msgQueueHandleMap = new SparseArray<McMsgQueuesHandle>();
    }

    public void sendMsg(int cmd) {
        sendMsg(McMsg.newInstance(cmd));
    }

    McMsgQueuesHandle getListener(int cmd) {
        McMsgQueuesHandle msgQueueHandle = msgQueueHandleMap.get(cmd);
        if (msgQueueHandle == null) {
            msgQueueHandle = new McMsgQueuesHandle(cmd);
            msgQueueHandleMap.put(cmd, msgQueueHandle);
        }
        return msgQueueHandle;
    }

    public void sendMsg(McMsg mm) {
        final McMsg temp = mm;
        if (mm != null) {
            ThreadWorker.execute(new Runnable() {
                public void run() {
                    McMsgQueuesHandle msgQueueHandle = getListener(temp.msgCmd);
                    msgQueueHandle.handleMsg(temp);
                }
            });
        }
    }

    public void registMsg(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                OnMsg onMsg = field.getAnnotation(OnMsg.class);
                if (onMsg != null) {

                    int[] msgs = onMsg.msg();

                    field.setAccessible(true);
                    try {
                        Object value = field.get(obj);
                        if (value instanceof OnMsgCallback) {
                            OnMsgCallback callback = (OnMsgCallback) value;
                            registMsg(msgs, callback, onMsg);
                        } else {
                            McLog.e(field.getName() + " in " + obj + " is not MsgCallback instance.");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void registMsg(int[] cmds, OnMsgCallback callback, OnMsg onMsg) {
        for (int cmd : cmds) {
            McMsgQueuesHandle msgQueueHandle = getListener(cmd);
            MsgCallbackImpl wrap = new MsgCallbackImpl(cmd, onMsg, callback);
            msgQueueHandle.addCallback(wrap);

            if (onMsg.useLastMsg()) {
                msgQueueHandle.doLastMsg();
            }
        }
    }

    public void unRegistMsg(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                OnMsg onMsg = field.getAnnotation(OnMsg.class);
                if (onMsg != null) {

                    int[] msgs = onMsg.msg();

                    field.setAccessible(true);
                    try {
                        Object value = field.get(obj);
                        if (value instanceof OnMsgCallback) {
                            OnMsgCallback callback = (OnMsgCallback) value;
                            unRegistMsg(msgs, callback);
                        } else {
                            McLog.e(field.getName() + " in " + obj + " is not MsgCallback instance.");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void unRegistMsg(int[] cmds, OnMsgCallback callback) {
        for (int cmd : cmds) {
            McMsgQueuesHandle msgQueueHandle = getListener(cmd);
            msgQueueHandle.remove(callback);
        }
    }

    public void clearMsgs() {
        for (int i = 0; i < msgQueueHandleMap.size(); i++) {
            int key = msgQueueHandleMap.keyAt(i);
            McMsgQueuesHandle hanlde = msgQueueHandleMap.get(key);
            if (hanlde != null) {
                hanlde.clearLastMsg();
            }
        }
    }

    public void clearCallbacks() {
        msgQueueHandleMap.clear();
    }
}