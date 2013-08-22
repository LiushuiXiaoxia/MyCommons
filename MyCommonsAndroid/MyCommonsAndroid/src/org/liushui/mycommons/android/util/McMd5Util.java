package org.liushui.mycommons.android.util;

import java.security.MessageDigest;

/**
 * Title: UtilMd5.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2013-1-22<br>
 * Version:v1.0
 */
public class McMd5Util {
	public final static String encryptMd5String(String s) {
		try {
			byte[] btInput = s.getBytes();
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < md.length; i++) {
				int val = ((int) md[i]) & 0xff;
				if (val < 16){
					sb.append("0");
				}
				sb.append(Integer.toHexString(val));
			}
			return sb.toString();
		} catch (Exception e) {
			return null;
		}
	}
}