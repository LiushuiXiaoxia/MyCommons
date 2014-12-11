package org.liushui.mycommons.android.demo.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.liushui.mycommons.android.annotation.helper.InjectHelper;
import org.liushui.mycommons.android.demo.BaseV4Fragment;
import org.liushui.mycommons.android.log.McLog;

public abstract class CommonFragment extends BaseV4Fragment implements ICommonFragment {

    protected Context context;
    protected CommonExtraParam extraReqParam;
    protected CommonFragment commonFragment;
    protected CommonFragmentActivity commonFragmentActivity;
    protected View fragmentView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intent it = getActivity().getIntent();
        extraReqParam = (CommonExtraParam) it.getSerializableExtra(EXTRA_REQ);

        McLog.i("extraReqParam = " + extraReqParam);

        context = getActivity();
        commonFragmentActivity = (CommonFragmentActivity) getActivity();
        commonFragment = this;

        ActionBar actionBar = commonFragmentActivity.getSupportActionBar();
        actionBar.setTitle(extraReqParam.fragmentTitle);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int lid = getLayoutId();
        if (lid == 0) {
            McLog.i("getLayoutId is 0,can't inflater.");
        }

        fragmentView = View.inflate(context, lid, null);
        return fragmentView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InjectHelper.init(this, view);
        afterViewCreated(savedInstanceState);
    }


    public <T extends CommonExtraParam> T getReqExtraParam() {
        return (T) extraReqParam;
    }

    public void setSuccessResult(CommonExtraParam extraParam) {
        Intent it = new Intent();
        it.putExtra(EXTRA_RESP, extraParam);
        commonFragmentActivity.setResult(Activity.RESULT_OK, it);
        commonFragmentActivity.finish();
    }

    protected abstract int getLayoutId();

    protected abstract void afterViewCreated(Bundle savedInstanceState);

    public static <R extends CommonExtraParam> R getRespExtraParam(Intent data) {
        if (data != null) {
            CommonExtraParam param = (CommonExtraParam) data.getSerializableExtra(EXTRA_RESP);
            R p = (R) param;
            return p;
        }
        return null;
    }
}