package org.liushui.mycommons.android.image;

import android.graphics.Bitmap;

class ImageLoadWrap {

    ImageLoadItem item;
    OnImageLoadCallback callback;
    Bitmap bitamp;

    void doCallback() {
        if (callback != null) {
            callback.onCallback(item, bitamp);
        } else {
            if (bitamp != null) {
                bitamp.recycle();
            }
        }
    }
}