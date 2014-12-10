package org.liushui.mycommons.android.image;

import android.graphics.Bitmap;

public interface OnImageLoadCallback {

	public void onCallback(ImageLoadItem item, Bitmap bitmap);
}