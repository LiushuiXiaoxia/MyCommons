package org.liushui.mycommons.android.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Title: McImageUtil.java<br>
 * author:xiaqiulei@gmail.com <br>
 * Date: 2012-11-14<br>
 * Version:v1.0
 */
public class McImageUtil {

    /**
     * bitmap2Bytes
     *
     * @param bm
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * bytes2Bitmap
     *
     * @param data
     * @return
     */
    public static Bitmap bytes2Bitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    /**
     * 缩放
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }

    /**
     * 根据输入流， 缩小图片
     *
     * @param input
     * @param scale
     * @return
     */
    public static Bitmap getBitmap(InputStream input, int scale) {
        Bitmap bitmap = null;
        Options ops = new Options();
        ops.inSampleSize = scale;
        bitmap = BitmapFactory.decodeStream(input, null, ops);
        return bitmap;
    }

    /**
     * 缩放
     *
     * @param drawable
     * @param w
     * @param h
     * @return
     */

    public static Drawable zoomDrawable(Drawable drawable, int w, int h, Resources res) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable转换成bitmap
        Bitmap oldbmp = drawableToBitmap(drawable);
        // 创建操作图片用的Matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放比例
        float sx = ((float) w / width);
        float sy = ((float) h / height);
        // 设置缩放比例
        matrix.postScale(sx, sy);
        // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(res, newbmp);
    }

    /**
     * Drawable --> Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap --> Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmap2Drawable(Bitmap b, Resources res) {
        // 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可
        BitmapDrawable bd = new BitmapDrawable(res, b);
        return bd;
    }

    /**
     * 获得圆角图片
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap makeRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 把一个图片弄成倒影效果
     *
     * @param bitmap
     * @return
     */
    public static Bitmap makeInvertedBitmap(Bitmap bitmap) {
        final int reflectionGap = 4;
        Bitmap originalImage = bitmap;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        Paint deafaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        int y = bitmapWithReflection.getHeight() + reflectionGap;
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, y, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // 在倒影图上用带阴影的画笔绘制矩形
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
        return bitmapWithReflection;
    }

    /**
     * 将位图保存到指定的路径
     *
     * @param path
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(String path, Bitmap bitmap) throws IOException {
        if (path != null && bitmap != null) {
            File file = new File(path);
            if (!file.exists()) {// 如果文件夹不存在则创建一个新的文件
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            //创建输出流
            OutputStream write = new FileOutputStream(file);
            //获取文件名
            String fileName = file.getName();
            //取出文件的格式名
            String endName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if ("png".equalsIgnoreCase(endName)) {
                //bitmap的压缩格式
                bitmap.compress(CompressFormat.PNG, 100, write);
            } else {
                bitmap.compress(CompressFormat.JPEG, 100, write);
            }
            write.close();
        }
    }

    /**
     * 根据指定的文件路径获取位图对象
     *
     * @param path
     * @return
     */
    public static Bitmap getBitMap(String path) {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(path);
        return bitmap;
    }
}