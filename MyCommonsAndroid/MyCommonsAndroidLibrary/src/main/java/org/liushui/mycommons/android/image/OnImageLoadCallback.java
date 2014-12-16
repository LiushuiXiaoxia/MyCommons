package org.liushui.mycommons.android.image;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

public interface OnImageLoadCallback {

    void onCallback(ImageLoadItem item, SoftReference<Bitmap> bitmap);
}