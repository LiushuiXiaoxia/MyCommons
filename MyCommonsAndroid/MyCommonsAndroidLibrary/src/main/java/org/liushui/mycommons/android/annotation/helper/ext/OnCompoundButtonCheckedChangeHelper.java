package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnCompoundButtonCheckedChange;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;
import org.liushui.mycommons.android.log.McLog;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class OnCompoundButtonCheckedChangeHelper extends BaseHelper<OnCompoundButtonCheckedChange> {

    public OnCompoundButtonCheckedChangeHelper(Object obj, View container) {
        super(obj, container);
    }

    public void doHelp(OnCompoundButtonCheckedChange t, Field field, String fieldName, Object fieldValue) {
        int[] values = t.value();
        int parentId = t.parentId();

        for (int id : values) {
            View view = findView(id, parentId, fieldName);
            if (view != null) {
                if (view instanceof CompoundButton) {
                    CompoundButton cb = (CompoundButton) view;
                    cb.setOnCheckedChangeListener((OnCheckedChangeListener) fieldValue);
                } else {
                    McLog.w("view(" + view + ") is not instance of CompoundButton.");
                }
            }
        }
    }
}