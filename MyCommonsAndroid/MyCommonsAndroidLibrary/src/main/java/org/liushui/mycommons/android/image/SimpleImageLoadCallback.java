package org.liushui.mycommons.android.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class SimpleImageLoadCallback implements OnImageLoadCallback {

    private ImageView icon;
    private int loadFailImage;

    public SimpleImageLoadCallback(ImageView iv, int image) {
        icon = iv;
        loadFailImage = image;
    }

    public void onCallback(ImageLoadItem item, Bitmap bitmap) {
        if (icon != null) {
            ImageLoadItem tag = (ImageLoadItem) icon.getTag();
            if (tag != null && tag.equals(item)) {
                if (bitmap != null) {
                    icon.setImageBitmap(bitmap);
                } else {
                    if (loadFailImage != ImageLoaderUtils.DEF_LOAD_FAIL_IMAGE) {
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

    void recycle(Bitmap b) {
        if (b != null) {
            b.recycle();
        }
    }
}