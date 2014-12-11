package org.liushui.mycommons.android.demo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import org.liushui.mycommons.android.annotation.OnClick;
import org.liushui.mycommons.android.annotation.OnCompoundButtonCheckedChange;
import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.demo.R;
import org.liushui.mycommons.android.demo.common.CommonFragment;
import org.liushui.mycommons.android.util.McToastUtil;

/**
 */
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