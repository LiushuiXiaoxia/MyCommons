package org.liushui.mycommons.android.image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Queue;

import org.liushui.mycommons.android.log.McLog;
import org.liushui.mycommons.android.net.McDownLoad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

/**
 * 异步动态加载image<br>
 * Title: AsyncImageLoader.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-7-31<br>
 * Version:v1.0
 */
public class AsyncImageLoader extends Thread {
	private static AsyncImageLoader instance;

	public synchronized static AsyncImageLoader getInstance() {
		if (instance == null) {
			instance = new AsyncImageLoader();
			instance.start();
		}
		return instance;
	}

	private Queue<ImageLoadItem> task;
	private ImageCacheControll controll;
	private MyHandler handler;

	private AsyncImageLoader() {
		task = new LinkedList<ImageLoadItem>();
		controll = ImageCacheControll.getInstance();
		handler = new MyHandler();
	}

	public synchronized void addImageLoadItem(ImageLoadItem item) {
		synchronized (this) {
			McLog.m(this, "addImageLoadItem");
			task.add(item);
			try {
				this.notify();
			} catch (Exception e) {
				e.printStackTrace();
				McLog.e("", e);
			}
		}
	}

	public synchronized void run() {
		while (true) {
			McLog.m(this, "run");
			while (task.size() > 0) {
				ImageLoadItem item = task.poll();
				Bitmap bitmap = getImage(item.imageUrl);
				item.bitmap = bitmap;
				Message msg = handler.obtainMessage();
				msg.obj = item;
				msg.sendToTarget();
			}
			if (task.size() == 0) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
					McLog.e("", e);
				}
			}
		}
	}

	private static class MyHandler extends Handler {
		public void handleMessage(Message msg) {
			McLog.m(this, "handleMessage");
			ImageLoadItem item = (ImageLoadItem) msg.obj;
			item.callback.onLoadSuccess(item.bitmap, item.imageUrl);
		}
	}

	private Bitmap getImage(String url) {
		McLog.m(this, "getImage");
		Bitmap bitmap = null;
		File file = controll.urlToFile(url);
		if (file.exists()) {
			// load from local
		} else {
			// load from net
			try {
				McDownLoad.download(url, file);
			} catch (MalformedURLException e) {
				e.printStackTrace();
				McLog.e("", e);
			} catch (IOException e) {
				e.printStackTrace();
				McLog.e("", e);
			}
		}
		McLog.i(file + " is " + (file.exists() ? "" : "not") + "exists.");
		bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
		return bitmap;
	}
}