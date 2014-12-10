package org.liushui.mycommons.android.annotation.helper;

import org.liushui.mycommons.android.annotation.ResType;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ResLoader {
    public static Object loadRes(ResType type, Context context, int id) {
        if (context == null || id < 1)
            return null;
        switch (type) {
            case Animation: {
                return getAnimation(context, id);
            }
            case Boolean: {
                return getBoolean(context, id);
            }
            case Color: {
                return getColor(context, id);
            }
            case ColorStateList: {
                return getColorStateList(context, id);
            }
            case Dimension: {
                return getDimension(context, id);
            }
            case DimensionPixelOffset: {
                return getDimensionPixelOffset(context, id);
            }
            case DimensionPixelSize: {
                return getDimensionPixelSize(context, id);
            }
            case Drawable: {
                return getDrawable(context, id);
            }
            case Integer: {
                return getInteger(context, id);
            }
            case IntArray: {
                return getIntArray(context, id);
            }
            case Movie: {
                return getMovie(context, id);
            }
            case String:
                return getString(context, id);
            case StringArray: {
                return getStringArray(context, id);
            }
            case Text: {
                return getText(context, id);
            }
            case TextArray: {
                return getTextArray(context, id);
            }
            case Xml: {
                return getXml(context, id);
            }
        }
        return null;
    }

    public static Animation getAnimation(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    public static boolean getBoolean(Context context, int id) {
        return context.getResources().getBoolean(id);
    }

    public static int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    public static float getDimension(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static int getDimensionPixelOffset(Context context, int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

    public static int getDimensionPixelSize(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static int getInteger(Context context, int id) {
        return context.getResources().getInteger(id);
    }

    public static int[] getIntArray(Context context, int id) {
        return context.getResources().getIntArray(id);
    }

    public static Movie getMovie(Context context, int id) {
        return context.getResources().getMovie(id);
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static CharSequence getText(Context context, int id) {
        return context.getResources().getText(id);
    }

    public static CharSequence[] getTextArray(Context context, int id) {
        return context.getResources().getTextArray(id);
    }

    public static XmlResourceParser getXml(Context context, int id) {
        return context.getResources().getXml(id);
    }
}