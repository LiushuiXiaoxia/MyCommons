package org.liushui.mycommons.android.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.liushui.mycommons.android.log.McLog;

/**
 * Title: UtilDownLoad.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-7-31<br>
 * Version:v1.0 下载工具类
 */
public class McDownLoad {

	/**
	 * 根据url，进行http下载
	 * 
	 * @param url
	 *            下载地址
	 * @param file
	 *            保存的路径
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static void download(String url, File file) throws MalformedURLException, IOException {
		McLog.m(McDownLoad.class, "download");
		HttpURLConnection urlConnection = (HttpURLConnection) (new URL(url)).openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setDoOutput(true);
		urlConnection.connect();
		File parent = file.getParentFile();
		if (!parent.exists()) {
			parent.mkdirs();
		}
		if (file.exists()) {
			file.delete();
		}
		FileOutputStream fileOutput = new FileOutputStream(file);

		InputStream inputStream = urlConnection.getInputStream();
		byte[] buffer = new byte[1024 * 10];
		int bufferLength = 0;
		while ((bufferLength = inputStream.read(buffer)) > 0) {
			fileOutput.write(buffer, 0, bufferLength);
		}
		fileOutput.close();
	}
}