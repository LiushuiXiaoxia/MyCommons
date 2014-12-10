package org.liushui.mycommons.android.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

/**
 * UtilDes.java<br/>
 * Date:2011-12-23 <br/>
 * Author:XiaoXia <br/>
 * Version:v1.0 <br/>
 * Title: Des加密解密工具类
 */
public class McDesUtil {

    private McDesUtil() {

    }

    /**
     * 加密
     *
     * @param key 密钥
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encryptDesString(String key, String str) {
        return byte2hex(getEncCode(key, str.getBytes()));
    }

    /**
     * 解密
     *
     * @param key 密钥
     * @param str des加密的字符串
     * @return 解密后的字符串
     */
    public static String decryptDesString(String key, String str) {
        return new String(getDesCode(key, hex2byte(str.getBytes())));
    }

    private static Key newDesInstance(String strKey) {
        Key key = null;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            keyGenerator.init(new SecureRandom(strKey.getBytes()));
            key = keyGenerator.generateKey();
            keyGenerator = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 加密以byte[]明文输入,byte[]密文输出
     */
    private static byte[] getEncCode(String key, byte[] byteS) {
        byte[] byteFina = null;
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, newDesInstance(key));
            byteFina = cipher.doFinal(byteS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 解密以byte[]密文输入,以byte[]明文输出
     *
     * @param byteD
     * @return
     */
    private static byte[] getDesCode(String key, byte[] byteD) {
        Cipher cipher;
        byte[] byteFina = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, newDesInstance(key));
            byteFina = cipher.doFinal(byteD);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cipher = null;
        }
        return byteFina;
    }

    /**
     * 二行制转字符串
     */
    private static String byte2hex(byte[] b) { // 一个字节的数，
        // 转成16进制字符串
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase(Locale.getDefault()); // 转成大写
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }
}