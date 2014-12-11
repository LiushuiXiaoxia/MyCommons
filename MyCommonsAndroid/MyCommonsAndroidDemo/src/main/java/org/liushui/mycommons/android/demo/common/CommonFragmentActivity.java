package org.liushui.mycommons.android.demo.common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.liushui.mycommons.android.demo.BaseActivity;
import org.liushui.mycommons.android.demo.R;
import org.liushui.mycommons.android.util.McToastUtil;


/**
 */
public class CommonFragmentActivity extends BaseActivity {

    static final int FRAGMENT_CONTAINER = R.id.fmFragmentContainer;

    private CommonExtraParam extraParam;
    private CommonFragment commonFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_fm);

        init(savedInstanceState);
    }

    void init(Bundle savedInstanceState) {
        Intent it = getIntent();
        Object obj = it.getSerializableExtra(ICommonFragment.EXTRA_REQ);
        if (validate(obj)) {
            extraParam = (CommonExtraParam) obj;
            if (savedInstanceState == null) {
                try {
                    commonFragment = extraParam.fragmentClass.newInstance();
                    getSupportFragmentManager().beginTransaction().add(FRAGMENT_CONTAINER, commonFragment).commit();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        } else {
            McToastUtil.show("参数不合法");
            finish();
        }
    }

    public CommonExtraParam getExtraParam() {
        return extraParam;
    }

    public CommonFragment getCommonFragment() {
        return commonFragment;
    }

    boolean validate(Object obj) {
        boolean ret = false;
        do {
            if (obj == null) {
                break;
            }
            if (!(obj instanceof CommonExtraParam)) {
                break;
            }
            CommonExtraParam param = (CommonExtraParam) obj;
            if (!param.validate()) {
                break;
            }
            ret = true;
        } while (false);
        return ret;
    }


    public static void launch(Activity activity, CommonExtraParam param, int req) {
        Intent it = new Intent(activity, CommonFragmentActivity.class);
        it.putExtra(ICommonFragment.EXTRA_REQ, param);
        if (req != 0) {
            activity.startActivityForResult(it, req);
        } else {
            activity.startActivity(it);
        }
    }

    public static void launch(Fragment fragment, CommonExtraParam param, int req) {
        Intent it = new Intent(fragment.getActivity(), CommonFragmentActivity.class);
        it.putExtra(ICommonFragment.EXTRA_REQ, param);
        if (req != 0) {
            fragment.startActivityForResult(it, req);
        } else {
            fragment.startActivity(it);
        }
    }
}