package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnRadioGroupCheckedChange;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;
import org.liushui.mycommons.android.log.McLog;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OnRadioGroupCheckedChangeChangeHelper extends BaseHelper<OnRadioGroupCheckedChange> {

    public OnRadioGroupCheckedChangeChangeHelper(Object obj, View container) {
        super(obj, container);
    }

    public void doHelp(OnRadioGroupCheckedChange t, Field field, String fieldName, Object fieldValue) {
        int[] values = t.value();
        int parentId = t.parentId();

        for (int id : values) {
            View view = findView(id, parentId, fieldName);
            if (view != null) {
                if (view instanceof RadioGroup) {
                    RadioGroup cb = (RadioGroup) view;
                    cb.setOnCheckedChangeListener((OnCheckedChangeListener) fieldValue);
                } else {
                    McLog.w("view(" + view + ") is not instance of RadioGroup.");
                }
            }
        }
    }
}