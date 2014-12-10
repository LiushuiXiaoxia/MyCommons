package org.liushui.mycommons.android.util;

import java.io.IOException;
import java.io.Serializable;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.log.McLog;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

/**
 * Title: McDimenUtil.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-9-17<br>
 * Version:v1.0
 */
public class McDimenUtil {

    public static class DimenSize implements Serializable {

        private static final long serialVersionUID = 1L;

        public static final int MATCH = ViewGroup.LayoutParams.MATCH_PARENT;
        public static final int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;

        public int width;

        public int height;

        public DimenSize() {
            super();
        }

        public DimenSize(int width, int height) {
            super();
            this.width = width;
            this.height = height;
        }

        public void scale(float s) {
            width = (int) (width * s);
            height = (int) (height * s);
        }

        public String toString() {
            return "DimenSize [width=" + width + ", height=" + height + "]";
        }
    }

    public static DimenSize getSizeByWrap(View view) {
        if (view != null) {
            int w = MeasureSpec.makeMeasureSpec(10000, MeasureSpec.AT_MOST);
            int h = MeasureSpec.makeMeasureSpec(10000, MeasureSpec.AT_MOST);
            view.measure(w, h);
            DimenSize size = new DimenSize(view.getMeasuredWidth(), view.getMeasuredHeight());
            return size;
        }
        return null;
    }

    public static void setViewSize(View view, DimenSize size) {
        if (view != null) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(size.width, size.height);
            } else {
                lp.width = size.width;
                lp.height = size.height;
            }
            view.setLayoutParams(lp);
        } else {
            McLog.w("view is null.");
        }
    }

    public static DimenSize getBitmapSize(Context context, int drawableId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), drawableId);
        DimenSize size = new DimenSize(opt.outWidth, opt.outHeight);
        return size;
    }

    public static DimenSize getBitmapSizeFromAsetts(Context context, String path) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(context.getAssets().open(path), null, opt);
            DimenSize size = new DimenSize(opt.outWidth, opt.outHeight);
            return size;
        } catch (IOException e) {
        }
        return null;
    }

    public static DimenSize getBitmapSizeFromFile(String path) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opt);
        DimenSize size = new DimenSize(opt.outWidth, opt.outHeight);
        return size;
    }

    public static int px2Dp(int px) {
        return (int) (px / McApplication.DIMEN_RATE + 0.5F);
    }

    public static int dp2Px(int dp) {
        return (int) (dp * McApplication.DIMEN_RATE + 0.5F);
    }
}