package org.liushui.mycommons.android.image;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class SimpleImageLoadCallback implements OnImageLoadCallback {

    private ImageView icon;
    private int loadFailImage;

    public SimpleImageLoadCallback(ImageView iv, int image) {
        icon = iv;
        loadFailImage = image;
    }

    public void onCallback(ImageLoadItem item, SoftReference<Bitmap> bitmap) {
        if (icon != null) {
            Object obj = icon.getTag();
            if (obj != null && obj.equals(item)) {
                if (bitmap != null && bitmap.get() != null) {
                    icon.setImageBitmap(bitmap.get());
                } else {
                    if (loadFailImage > ImageLoaderUtils.DEF_LOAD_FAIL_IMAGE) {
                        icon.setImageResource(loadFailImage);
                    }
                }
            } else {
                recycle(bitmap);
            }
        } else {
            recycle(bitmap);
        }
    }

    void recycle(SoftReference<Bitmap> b) {
        if (b != null) {
            Bitmap bit = b.get();
            if (bit != null) {
                bit.recycle();
                bit = null;
            }
        }
        b = null;
    }
}