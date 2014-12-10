package org.liushui.mycommons.android.base;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import org.liushui.mycommons.android.log.McLog;

/**
 * Title: BaseActivity.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013-1-22<br>
 * Version:v1.0
 */
public abstract class BaseActivity extends Activity {

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        McLog.md(this, "onCreate");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onStart()
     */
    protected void onStart() {
        super.onStart();
        McLog.md(this, "onStart");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onResume()
     */
    protected void onResume() {
        super.onResume();
        McLog.md(this, "onResume");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onPause()
     */
    protected void onPause() {
        super.onPause();
        McLog.md(this, "onPause");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onStop()
     */
    protected void onStop() {
        super.onStop();
        McLog.md(this, "onStop");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onDestroy()
     */
    protected void onDestroy() {
        super.onDestroy();
        McLog.md(this, "onDestroy");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onRestart()
     */
    protected void onRestart() {
        super.onRestart();
        McLog.md(this, "onRestart");
    }

    /**
     * (non-Javadoc)
     *
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        McLog.md(this, "onConfigurationChanged");
    }
}