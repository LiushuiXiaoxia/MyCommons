package org.liushui.mycommons.android.annotation.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.annotation.OnClick;
import org.liushui.mycommons.android.annotation.OnCompoundButtonCheckedChange;
import org.liushui.mycommons.android.annotation.OnFocusChange;
import org.liushui.mycommons.android.annotation.OnItemClick;
import org.liushui.mycommons.android.annotation.OnItemLongClick;
import org.liushui.mycommons.android.annotation.OnItemSelected;
import org.liushui.mycommons.android.annotation.OnLongClick;
import org.liushui.mycommons.android.annotation.OnPreferenceChange;
import org.liushui.mycommons.android.annotation.OnPreferenceClick;
import org.liushui.mycommons.android.annotation.OnRadioGroupCheckedChange;
import org.liushui.mycommons.android.annotation.OnScroll;
import org.liushui.mycommons.android.annotation.OnTouch;
import org.liushui.mycommons.android.annotation.PreferenceInject;
import org.liushui.mycommons.android.annotation.ResInject;
import org.liushui.mycommons.android.annotation.ViewInject;
import org.liushui.mycommons.android.annotation.helper.ext.OnClickHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnCompoundButtonCheckedChangeHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnItemClickHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnItemLongClickHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnItemSelectedHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnLongClickelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnOnFocusChangeHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnPreferenceChangeHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnPreferenceClickHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnRadioGroupCheckedChangeChangeHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnScrollHelper;
import org.liushui.mycommons.android.annotation.helper.ext.OnTouchHelper;
import org.liushui.mycommons.android.annotation.helper.ext.PreferenceInjectHelper;
import org.liushui.mycommons.android.annotation.helper.ext.ViewInjectHelper;
import org.liushui.mycommons.android.exception.McException;

import android.view.View;

class HelperProxy {
    private Object obj;
    private View container;
    private Field[] fields;
    private Map<Class<? extends Annotation>, BaseHelper<? extends Annotation>> map;

    HelperProxy(Object obj, View container) {
        super();
        this.obj = obj;
        this.container = container;
        if (container == null) {
            throw new McException("container is null.");
        }

        map = new HashMap<Class<? extends Annotation>, BaseHelper<? extends Annotation>>();
        map.put(OnClick.class, new OnClickHelper(obj, container));
        map.put(OnCompoundButtonCheckedChange.class, new OnCompoundButtonCheckedChangeHelper(obj, container));
        map.put(OnFocusChange.class, new OnOnFocusChangeHelper(obj, container));
        map.put(OnItemClick.class, new OnItemClickHelper(obj, container));
        map.put(OnItemLongClick.class, new OnItemLongClickHelper(obj, container));
        map.put(OnItemSelected.class, new OnItemSelectedHelper(obj, container));
        map.put(OnLongClick.class, new OnLongClickelper(obj, container));
        map.put(OnPreferenceChange.class, new OnPreferenceChangeHelper(obj, container));
        map.put(OnPreferenceClick.class, new OnPreferenceClickHelper(obj, container));
        map.put(OnRadioGroupCheckedChange.class, new OnRadioGroupCheckedChangeChangeHelper(obj, container));
        map.put(OnScroll.class, new OnScrollHelper(obj, container));
        map.put(OnTouch.class, new OnTouchHelper(obj, container));
        map.put(PreferenceInject.class, new PreferenceInjectHelper(obj, container));
        map.put(ViewInject.class, new ViewInjectHelper(obj, container));
    }

    void init() {
        fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                // 先取出字段的值和名称
                field.setAccessible(true);
                Object fieldValue = null;
                String fieldName = null;
                try {
                    fieldValue = field.get(obj);
                    fieldName = field.getName();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                // 所有的listener
                for (Entry<Class<? extends Annotation>, BaseHelper<? extends Annotation>> en : map.entrySet()) {
                    Class<? extends Annotation> cls = en.getKey();
                    @SuppressWarnings("unchecked")
                    BaseHelper<Annotation> helper = (BaseHelper<Annotation>) en.getValue();

                    Annotation anno = field.getAnnotation(cls);
                    if (anno != null) {
                        helper.doHelp(anno, field, fieldName, fieldValue);
                    }
                }

                // 所有的资源
                ResInject resInject = field.getAnnotation(ResInject.class);
                if (resInject != null) {
                    Object res = ResLoader.loadRes(resInject.type(), McApplication.getMcAppInstance(), resInject.id());
                    try {
                        field.set(obj, res);
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}