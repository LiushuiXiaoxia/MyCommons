package org.liushui.mycommons.android.image;

import android.graphics.Bitmap;

/**
 * Title: ImageLoadItem.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-7-31<br>
 * Version:v1.0
 * 图片加载单例
 */
public class ImageLoadItem {

	/**
	 * 需要下载的图片url
	 */
	public String imageUrl;
	/**
	 * 下载后的bitmap
	 */
	public Bitmap bitmap;
	/**
	 * 回调方法
	 */
	public ImageLoadCallback callback;

	/**
	 * Title: ImageLoadCallback.java<br>
	 * author:xiaqiulei@gmail.com <br>
	 * Date: 2012-7-31<br>
	 * Version:v1.0
	 * 加载回调函数
	 */
	public interface ImageLoadCallback {

		/**
		 * 图片获取成功
		 * 
		 * @param bitmap
		 * @param imageUrl
		 */
		public void onLoadSuccess(Bitmap bitmap, String imageUrl);

		/**
		 * 图片获取失败
		 * 
		 * @param msg
		 */
		public void onLoadFail(String msg);
	}
}