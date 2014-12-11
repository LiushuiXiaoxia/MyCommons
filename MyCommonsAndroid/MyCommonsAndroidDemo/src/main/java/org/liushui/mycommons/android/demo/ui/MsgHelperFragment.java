package org.liushui.mycommons.android.demo.ui;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import org.liushui.mycommons.android.McHelper;
import org.liushui.mycommons.android.annotation.OnClick;
import org.liushui.mycommons.android.annotation.OnMsg;
import org.liushui.mycommons.android.demo.IMsgs;
import org.liushui.mycommons.android.demo.MainService;
import org.liushui.mycommons.android.demo.R;
import org.liushui.mycommons.android.demo.common.CommonFragment;
import org.liushui.mycommons.android.log.McLog;
import org.liushui.mycommons.android.msg.MsgHelper;
import org.liushui.mycommons.android.msg.OnMsgCallback;
import org.liushui.mycommons.android.util.McToastUtil;

/**
 */
public class MsgHelperFragment extends CommonFragment {

    int cmds[] = {IMsgs.MSG_TEST3};

    protected int getLayoutId() {
        return R.layout.fm_msg_helper;
    }

    protected void afterViewCreated(Bundle savedInstanceState) {

    }

    public void onResume() {
        super.onResume();

        // regist use Annotation
        MsgHelper.getInstance().registMsg(this);

        // regist in Code
        MsgHelper.getInstance().registMsg(cmds, msgCallback3, McHelper.makeOnMsg(cmds, true, true));
    }

    public void onPause() {
        super.onPause();

        // unregist use Annotation
        MsgHelper.getInstance().unRegistMsg(this);

        // un regist in code
        MsgHelper.getInstance().unRegistMsg(cmds, msgCallback3);
    }


    @OnClick({R.id.uiButton, R.id.notUiButton, R.id.registInCode})
    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.uiButton: {
                    MainService.startService(getActivity(), MainService.ACTION_TEST1);
                    break;
                }
                case R.id.notUiButton: {
                    MainService.startService(getActivity(), MainService.ACTION_TEST2);
                    break;
                }
                case R.id.registInCode: {
                    MainService.startService(getActivity(), MainService.ACTION_TEST3);
                    break;
                }
                default:
                    break;
            }
        }
    };


    @OnMsg(msg = {IMsgs.MSG_TEST1}, ui = true)
    OnMsgCallback msgCallback1 = new OnMsgCallback() {
        public boolean handleMsg(Message msg) {
            String log = "current thread name = " + Thread.currentThread().getName();
            McLog.i(log);

            McToastUtil.show(log);
            return false;
        }
    };

    @OnMsg(msg = {IMsgs.MSG_TEST2}, ui = false, useLastMsg = false)
    OnMsgCallback msgCallback2 = new OnMsgCallback() {
        public boolean handleMsg(Message msg) {
            String log = "current thread name = " + Thread.currentThread().getName();
            McLog.i(log);

            McLog.i("msg data = " + msg.obj);

            McToastUtil.show(log + "\n msg data = " + msg.obj);
            return false;
        }
    };

    OnMsgCallback msgCallback3 = new OnMsgCallback() {
        public boolean handleMsg(Message msg) {
            McLog.i("current thread name = " + Thread.currentThread().getName());
            McLog.i("msg data = " + msg.obj);

            McToastUtil.show(msg.obj.toString());
            return false;
        }
    };
}