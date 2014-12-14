MyCommons
=========
MyCommons是移动开发库，可以简单和快速的在此基础上完成新项目的开发。

QQ群：153516800

[网址地址](http://www.mycommons.cn/)

[GitHub地址](https://github.com/LiushuiXiaoxia/MyCommons)

[Csdn Blog地址](http://blog.csdn.net/guijiaoba/article/details/10241715)

#MyCommons分类：
* MyCommonsAndroid
* MyCommonsIOS
* MyCommonsWindow

#MyCommonsAndroid 功能
* McApplication
* View Inject
* Msg Helper
* 其他实用工具类

#MyCommonsAndroid 文档

##MyCommonsAndroid代码结构
```
org.liushui.mycommons.android
|+ annotation // 标注工具
|+ base // 常用类的基类
|+ data // 数据库工具类
|+ exception // 异常类
|+ image // 图片工具类
|+ log // 日志工具类
|+ net // 网络工具类
|+ util // 其他工具类
McApplication.java // MyCommons的全局上下文
McHelper.java // Mc全局帮助类
McInterface.java // Mc接口介绍
```

##MyCommonsAndroid集成

Gradle集成
```
maven {
    url "https://raw.githubusercontent.com/LiushuiXiaoxia/MyCommons/master/MyCommonsAndroid/repository/"
}

compile 'org.liushui:mycommons.android:1.0.1'
```
### McApplication
McApplication在MyCommonsAndroid中算全局上下文，使用是需要在App上下文中初始化,可选择如下2种方式集成:   
* 直接在AndroidManifest.xml中定义
```
<application
   ...
   android:name="org.liushui.mycommons.android.McApplication"
   ....
    />
    
调用方式:
McApplication mcApplication = McApplication.getMcAppInstance();
```
* 继承McApplication
```
public class AppContext extends McApplication<AppContext> {
}

调用方式:
AppContext appContext = AppContext.getMcAppInstance();
```

### View Inject 功能介绍
参照MyCommonsAndroidDemo中代码MsgHelperFragment.java
```
public class ViewInjectFragment extends CommonFragment {

    @ViewInject(R.id.button)
    Button button;

    @ViewInject(R.id.checkBox)
    CheckBox checkBox;

    protected int getLayoutId() {
        return R.layout.fm_view_inject;
    }

    protected void afterViewCreated(Bundle savedInstanceState) {

    }

    @OnClick(R.id.button)
    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v == button) {
                McToastUtil.show(button.getText().toString());
            }
        }
    };

    @OnCompoundButtonCheckedChange(R.id.checkBox)
    CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView == checkBox) {
                McToastUtil.show(isChecked ? "选中" : "未选中");
            }
        }
    };
}
```
fm_view_inject.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:id="@+id/button"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Button Click" />

    <CheckBox
        android:id="@+id/checkBox"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="CheckBox Check" />
</LinearLayout>
```
### MsgHelper功能介绍
MsgHelperFragment.java
```
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
```
MainService.java中做代码转发
```
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
```
fm_msg_helper.xml 文件

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:id="@+id/uiButton"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Msg Callback in UI Thread" />

    <Button
        android:id="@+id/notUiButton"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Msg Callback not in UI Thread" />

    <Button
        android:id="@+id/registInCode"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:text="Regist In Code" />
</LinearLayout>
```
