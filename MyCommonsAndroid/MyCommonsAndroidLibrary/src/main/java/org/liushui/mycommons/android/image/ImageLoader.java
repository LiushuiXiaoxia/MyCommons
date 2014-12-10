package org.liushui.mycommons.android.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Future;

import org.liushui.mycommons.android.McApplication;
import org.liushui.mycommons.android.log.McLog;
import org.liushui.mycommons.android.thread.ThreadWorker;

import android.graphics.Bitmap;

public class ImageLoader {
    private static ImageLoader instance = new ImageLoader();

    public static ImageLoader getInstance() {
        return instance;
    }

    private ImageLoader() {
    }

    public void loadImage(ImageLoadItem item, OnImageLoadCallback callback) {
        Future<?> f = ThreadWorker.submit(new LoadImageRunnable(item, callback));
        McLog.i("f = " + f);
    }

    class LoadImageRunnable implements Runnable {
        ImageLoadItem item;
        OnImageLoadCallback callback;
        String url;

        public LoadImageRunnable(ImageLoadItem item, OnImageLoadCallback callback) {
            super();
            this.item = item;
            this.callback = callback;
            url = item.url;
        }

        public void run() {
            String path = ImageLoaderUtils.urlToFilePath(url);
            File f = new File(path);
            if (f.exists() && f.length() != 0) {
                try {
                    Bitmap b = ImageLoaderUtils.getBitmapFromFile(path);
                    sendToTarget(b);
                } catch (Exception e) {
                    sendToTarget(null);
                }
            } else {
                downloadBitmap();
            }
        }

        void sendToTarget(Bitmap b) {
            McLog.i("url:%s-->path:%s-->Bitmap:%s", url, ImageLoaderUtils.urlToFilePath(url), b);
            final ImageLoadWrap wrap = new ImageLoadWrap();
            wrap.callback = callback;
            wrap.item = item;
            wrap.bitamp = b;
            McApplication.getMcAppInstance().post(new Runnable() {
                public void run() {
                    wrap.doCallback();
                }
            });
        }

        void downloadBitmap() {
            String path = ImageLoaderUtils.urlToFilePath(url);
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }

            download(url, f);

            if (f.exists()) {
                Bitmap b = ImageLoaderUtils.getBitmapFromFile(path);
                sendToTarget(b);
            } else {
                sendToTarget(null);
            }
        }

        void download(String url, File file) {
            FileOutputStream fos = null;
            try {
                if (ImageLoaderUtils.intercept()) {
                    McLog.w("download image is intercept, do not download image.");
                } else {
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
                    File parent = file.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    if (file.exists()) {
                        file.delete();
                    }
                    fos = new FileOutputStream(file);

                    InputStream is = urlConnection.getInputStream();
                    byte[] buff = new byte[10 * 1024];
                    int len = -1;
                    while ((len = is.read(buff)) != -1) {
                        fos.write(buff, 0, len);
                    }
                    fos.flush();
                }
            } catch (Exception e) {
                String msg = "donwload image " + url + " fail.";
                McLog.w(msg);
                if (file.exists()) {
                    file.delete();
                }
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (file.length() == 0) {
                        McLog.i("file's length is 0,so delete this file.");
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                }
            }
        }
    }
}