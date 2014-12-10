package org.liushui.mycommons.android.annotation.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.liushui.mycommons.android.log.McLog;

import android.view.View;

public abstract class BaseHelper<T extends Annotation> {
    protected Object obj;
    protected View container;

    public BaseHelper(Object obj, View container) {
        super();
        this.obj = obj;
        this.container = container;
    }

    protected View findView(int id, int pid, String name) {
        View temp = container;
        if (pid != 0) {
            temp = container.findViewById(pid);
            if (temp == null) {
                String msg = String.format("field(name = %s ,id = %s) can't find parent (pId = %s)", name, id, pid);
                McLog.e(msg);
                return null;
            }
        }
        View v = temp.findViewById(id);
        if (v == null) {
            String msg = String.format("%s can't find %s (pId = %s, vId = %s)", container, name, pid, id);
            McLog.e(msg);
        }
        return v;
    }

    public abstract void doHelp(T t, Field field, String fieldName, Object fieldValue);
}