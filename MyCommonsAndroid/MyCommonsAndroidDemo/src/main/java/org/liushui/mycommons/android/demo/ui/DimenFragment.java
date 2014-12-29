package org.liushui.mycommons.android.demo.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.demo.R;
import org.liushui.mycommons.android.demo.common.CommonFragment;
import org.liushui.mycommons.android.util.McDimenUtil;

/**
 * DimenFragment <br/>
 * Created by xiaqiulei on 2014-12-29.
 */
public class DimenFragment extends CommonFragment {

    @ViewInject(R.id.tvText)
    TextView tvText;

    @Override
    protected int getLayoutId() {
        return R.layout.fm_dimen;
    }

    @Override
    protected void afterViewCreated(Bundle savedInstanceState) {
        tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, McDimenUtil.sp2px(20));
    }
}