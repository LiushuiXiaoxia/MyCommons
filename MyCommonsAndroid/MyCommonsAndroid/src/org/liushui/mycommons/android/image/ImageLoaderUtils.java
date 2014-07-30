package org.liushui.mycommons.android.image;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.log.McLog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class ImageLoaderUtils {

	static final int DEF_LOAD_FAIL_IMAGE = -1;

	static String sdcard = "";
	static ImageLoaderConfig config;
	static String imageStorePath;

	static {
		sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
		config = new ImageLoaderConfig();
		check();
	}

	static void check() {
		config.imageStorePath = sdcard + "/" + McApplication.getMcAppInstance().getPackageName() + "/image/";
		File f = new File(config.imageStorePath);
		if (!f.exists()) {
			f.mkdirs();
		}
		imageStorePath = f.getAbsolutePath();
	}

	static boolean intercept() {
		boolean ret = false;
		do {
			if (config == null) {
				break;
			}
			if (config.interceptListener == null) {
				break;
			}
			ret = config.interceptListener.intercept();
		} while (false);
		return ret;
	}

	/**
	 * 初始化
	 * 
	 * @param con
	 */
	public static void doImageLoadInit(ImageLoaderConfig con) {
		config = con;
		check();
	}

	public static String urlToFileName(String url) {
		if (TextUtils.isEmpty(url)) {
			return "";
		}
		try {
			return URLEncoder.encode(url, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlToFilePath(String url) {
		String name = urlToFileName(url);
		File f = new File(imageStorePath, name);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}
		return f.getAbsolutePath();
	}

	public static Bitmap getBitmapFromUrl(String url) {
		String path = urlToFilePath(url);
		return getBitmapFromFile(path);
	}

	public static Bitmap getBitmapFromFile(String file) {
		Options opt = new Options();
		opt.inTargetDensity = McApplication.DIMEN_DPI;
		opt.inTargetDensity = DisplayMetrics.DENSITY_XHIGH;
		Bitmap b = BitmapFactory.decodeFile(file, opt);
		return b;
	}

	public static boolean isImageExists(String url) {
		String path = urlToFilePath(url);
		return new File(path).exists();
	}

	/**
	 * 是否存在sdcard
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

	public static void loadImage(String url, ImageView iv) {
		loadImage(url, iv, DEF_LOAD_FAIL_IMAGE);
	}

	public static void loadImage(String url, ImageView iv, int loadFialImage) {
		McLog.m(ImageLoaderUtils.class, "loadImage");
		ImageLoadItem item = new ImageLoadItem(url);
		iv.setTag(item);
		ImageLoader.getInstance().loadImage(item, new SimpleImageLoadCallback(iv, DEF_LOAD_FAIL_IMAGE));
	}
}