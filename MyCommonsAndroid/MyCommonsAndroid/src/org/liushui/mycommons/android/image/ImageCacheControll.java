package org.liushui.mycommons.android.image;

import java.io.File;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.log.McLog;

import android.content.Context;
import android.os.Environment;

/**
 * Title: CacheControll.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-7-31<br>
 * Version:v1.0
 * 缓存控制器
 */
public class ImageCacheControll {

	private static ImageCacheControll instance = new ImageCacheControll();

	public static ImageCacheControll getInstance() {
		return instance;
	}

	private ImageCacheControll() {
		context = McApplication.getMcAppInstance();
		baseSdcardPath = context.getPackageName() + "/image";
	}

	private String baseSdcardPath;
	private Context context;

	/**
	 * 根据url，得到相应应该保存的文件路径
	 * 
	 * @param url
	 * @return
	 */
	public File urlToFile(String url) {
		McLog.m(this, "urlToFile");
		String name = url.substring(url.lastIndexOf('/') + 1);
		File file = null;
		if (isSdcardExists()) {
			file = new File(Environment.getExternalStorageDirectory(), baseSdcardPath + name);
		} else {
			file = new File(context.getFilesDir(), name);
		}
		return file;
	}

	/**
	 * 是否存在sdcard
	 * 
	 * @return
	 * 
	 */
	public boolean isSdcardExists() {
		boolean b = false;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			b = true;
		} else {
			b = false;
		}
		return b;
	}
}