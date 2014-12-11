package org.liushui.mycommons.android.demo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import org.liushui.mycommons.android.log.McLog;
import org.liushui.mycommons.android.msg.McMsg;
import org.liushui.mycommons.android.msg.MsgHelper;
import org.liushui.mycommons.android.thread.ThreadWorker;

public class MainService extends Service {

    public static final String ACTION_TEST1 = "ACTION_TEST1";
    public static final String ACTION_TEST2 = "ACTION_TEST2";
    public static final String ACTION_TEST3 = "ACTION_TEST3";


    public static void startService(Context context, String action) {
        Intent it = new Intent(context, MainService.class);
        it.setAction(action);
        context.startService(it);
    }

    public MainService() {
    }

    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            McLog.i("action = " + action);

            if (action != null) {
                doHandlAction(action);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void doHandlAction(String action) {
        if (ACTION_TEST1.equals(action)) {

            MsgHelper.getInstance().sendMsg(IMsgs.MSG_TEST1);

        } else if (ACTION_TEST2.equals(action)) {

            ThreadWorker.execute(new Runnable() {
                public void run() {
                    McMsg mm = McMsg.newInstance(IMsgs.MSG_TEST2, "test msg");
                    MsgHelper.getInstance().sendMsg(mm);
                }
            });

        } else if (ACTION_TEST3.equals(action)) {

            McMsg mm = McMsg.newInstance(IMsgs.MSG_TEST3, "regist msg in code.");
            MsgHelper.getInstance().sendMsg(mm);

        }
    }
}
