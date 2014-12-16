package org.liushui.mycommons.android.image;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

class ImageLoadWrap {

	ImageLoadItem item;
	OnImageLoadCallback callback;
	SoftReference<Bitmap> bitamp;

	void doCallback() {
		if (callback != null) {
			callback.onCallback(item, bitamp);
		} else {
			if (bitamp != null) {
				Bitmap b = bitamp.get();
				if (b != null) {
					b.recycle();
					b = null;
				}
				bitamp = null;
			}
		}
	}
}