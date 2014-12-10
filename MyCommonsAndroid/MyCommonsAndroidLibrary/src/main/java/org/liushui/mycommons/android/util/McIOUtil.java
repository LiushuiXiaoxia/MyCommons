package org.liushui.mycommons.android.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Title: McIOUtil.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-8-6<br>
 * Version:v1.0
 */
public class McIOUtil {
    /**
     * 复制
     *
     * @param from   源
     * @param to     目标
     * @param delete 目标文件存在，是否删除替换
     * @throws Exception
     */
    public static void copy(File inFile, File outFile, boolean delete) throws Exception {
        copy(new FileInputStream(inFile), outFile, delete);
    }

    /**
     * 复制
     *
     * @param from   源
     * @param to     目标
     * @param delete 目标文件存在，是否删除替换
     * @throws Exception
     */
    public static void copy(InputStream in, File outFile, boolean delete) throws Exception {
        if (outFile.exists() && delete == false) {
            // 存在且不删除，则不复制
            return;
        }
        OutputStream out = new FileOutputStream(outFile);
        copy(in, out);
    }

    /**
     * 重命名
     *
     * @param from   原文件
     * @param to     目标文件
     * @param delete 如果目标文件存在，是否删除
     */
    public static void rename(String from, String to, boolean delete) {
        rename(new File(from), new File(to), delete);
    }

    /**
     * 重命名
     *
     * @param from   原文件
     * @param to     目标文件
     * @param delete 如果目标文件存在，是否删除
     */
    public static void rename(File from, File to, boolean delete) {
        if (!from.exists()) {
            return;
        }
        boolean isRename = false;
        if (to.exists()) {
            if (delete) {
                to.delete();
                isRename = true;
            } else {
                isRename = false;
            }
        } else {
            isRename = true;
        }
        if (isRename) {
            File parent = to.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            from.renameTo(to);
        }
    }

    /**
     * 复制
     *
     * @param in  源
     * @param out 目标
     * @throws Exception
     */
    private static void copy(InputStream in, OutputStream out) throws Exception {
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] buffer = new byte[10 * 1024];
        while (bis.read(buffer) != -1) {
            bos.write(buffer, 0, buffer.length);
        }
        bis.close();
        bos.close();
    }

    public static void deleteFile(String file) {
        File f = new File(file);
        if (!f.isDirectory()) {
            f.deleteOnExit();
        }
    }

    public static void deleteDir(String file) {
        File f = new File(file);
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File ff : files) {
                    if (ff.isDirectory()) {
                        deleteDir(ff.getAbsolutePath());
                    } else {
                        deleteFile(ff.getAbsolutePath());
                    }
                }
            }
        } else {
            deleteFile(f.getAbsolutePath());
        }
    }
}