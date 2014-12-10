package org.liushui.mycommons.android.annotation.helper.ext;

import java.lang.reflect.Field;

import org.liushui.mycommons.android.annotation.OnClick;
import org.liushui.mycommons.android.annotation.helper.BaseHelper;

import android.view.View;
import android.view.View.OnClickListener;

public class OnClickHelper extends BaseHelper<OnClick> {

    public OnClickHelper(Object obj, View container) {
        super(obj, container);
    }

    public void doHelp(OnClick click, Field field, String fieldName, Object fieldValue) {
        int[] values = click.value();
        int parentId = click.parentId();

        for (int id : values) {
            View view = findView(id, parentId, fieldName);
            if (view != null) {
                view.setOnClickListener((OnClickListener) fieldValue);
            }
        }
    }
}