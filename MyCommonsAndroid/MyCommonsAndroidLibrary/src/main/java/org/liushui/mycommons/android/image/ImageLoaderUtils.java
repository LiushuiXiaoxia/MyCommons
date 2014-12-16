package org.liushui.mycommons.android.image;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.log.McLog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class ImageLoaderUtils {

    static final int DEF_LOAD_FAIL_IMAGE = 0;

    private static String sdcard = "";
    private static ImageLoaderConfig config;
    private static String imageStorePath;

    static {
        sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        config = new ImageLoaderConfig();
        check();
    }

    static void check() {
        config.setImageStorePath(sdcard + "/" + McApplication.getMcAppInstance().getPackageName() + "/image/");
        File f = new File(config.getImageStorePath());
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
            if (config.getInterceptListener() == null) {
                break;
            }
            ret = config.getInterceptListener().intercept();
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
        return getBitmapFromFile(file, 0, 0);
    }

    public static Bitmap getBitmapFromFile(String file, int w, int h) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (w > 0 && h > 0) {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file, options);

            int inSampleSize = calculateInSampleSize(options, w, h);
            options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
        }

        options.inTargetDensity = McApplication.DIMEN_DPI;
        options.inTargetDensity = DisplayMetrics.DENSITY_XHIGH;

        return BitmapFactory.decodeFile(file, options);
    }

    static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 图像原始高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round(1.0F * height / reqHeight);
            } else {
                inSampleSize = Math.round(1.0F * width / reqWidth);
            }
        }
        return inSampleSize;
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

    public static void loadImage(String url, ImageView iv, int loadFialImage, int w, int h) {
        McLog.m(ImageLoaderUtils.class, "loadImage");
        ImageLoadItem item = new ImageLoadItem(url);
        item.setWidth(w);
        item.setHeight(h);
        iv.setTag(item);
        ImageLoader.getInstance().loadImage(item, new SimpleImageLoadCallback(iv, DEF_LOAD_FAIL_IMAGE));
    }
}