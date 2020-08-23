package com.jbsx.utils.image;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;

import com.jbsx.utils.UiTools;

/**
 * 制作倒影图片
 */
public class ReflectionBitmapUtil {
    /**
     * 制作倒影图片
     *
     * @return Bitmap
     */
    public static Bitmap getReverseBitmap(Bitmap srcBitmap) {

        if (null == srcBitmap) return null;

        // 原图和倒影之间的间距
        final int GAP = UiTools.dip2px(8);

        int W1 = srcBitmap.getWidth(), H1 = srcBitmap.getHeight();
        int W2 = W1, H2 = H1 / 2;
        if (W1 == 0 || H1 == 0) return null;

        try {
            // 创建图像倒影，倒影为原图的下半部分Y轴翻转
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);
            Bitmap reflectionBitmap = Bitmap.createBitmap(srcBitmap, 0, H1 / 2, W1, H1 / 2, matrix, false);
            if (null == reflectionBitmap) return null;

            // 创建原图和倒影的合图
            Bitmap bitmapWithReflection = Bitmap.createBitmap(W2, H1 + H2 + GAP, Bitmap.Config.ARGB_8888);
            if (null == bitmapWithReflection) return null;

            Canvas canvas = new Canvas(bitmapWithReflection);        // 从Bitmap创建Canvas
            canvas.drawBitmap(srcBitmap, 0, 0, null);                // 绘制原图
            canvas.drawBitmap(reflectionBitmap, 0, H1 + GAP, null);    // 绘制倒影

            // 为倒影图添加颜色线性渐变
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            LinearGradient shader = new LinearGradient(0, H1, 0, bitmapWithReflection.getHeight() + GAP, 0x70FFFFFF, 0x00FFFFFF,
                    Shader.TileMode.MIRROR);
            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));

            // 绘制线性渐变
            canvas.drawRect(0, H1, W1, bitmapWithReflection.getHeight() + GAP, paint);

            return bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 仅仅制作倒影图片，不带原图
     *
     * @param srcBitmap
     * @return
     */
    public static Bitmap getReverseBitmapOnly(Bitmap srcBitmap, int height) {

        if (null == srcBitmap) return null;

        int W1 = srcBitmap.getWidth(), H1 = srcBitmap.getHeight();
        if (W1 == 0 || H1 == 0) return null;

        try {
            // 创建图像倒影，倒影为原图的下半部分Y轴翻转
            Matrix matrix = new Matrix();
            matrix.preScale(1, -1);
            Bitmap reflectionBitmap = Bitmap.createBitmap(srcBitmap, 0, H1 - height, W1, height, matrix, false);
            if (null == reflectionBitmap) return null;

            // 创建原图的倒影图
            Bitmap bitmapWithReflection = Bitmap.createBitmap(W1, height, Bitmap.Config.ARGB_8888);
            if (null == bitmapWithReflection) return null;

            Canvas canvas = new Canvas(bitmapWithReflection);
            canvas.drawBitmap(reflectionBitmap, 0, 0, null);

            // 为倒影图添加颜色线性渐变
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            LinearGradient shader = new LinearGradient(0, 0, 0, bitmapWithReflection.getHeight(),
                    0x70FFFFFF, 0x00FFFFFF, Shader.TileMode.MIRROR);
            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));

            // 绘制线性渐变
            canvas.drawRect(0, 0, W1, bitmapWithReflection.getHeight(), paint);
            return bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 复制图片
     *
     * @param width
     * @param height
     * @param sourceBitmap
     * @return
     */
    private Bitmap copyBitmap(int width , int height, Bitmap sourceBitmap) {
        Bitmap target = Bitmap.createBitmap(width, height, sourceBitmap.getConfig());
        Canvas temp_canvas = new Canvas(target);
        temp_canvas.drawBitmap(sourceBitmap, null, new Rect(0, 0, target.getWidth(), target.getHeight()), null);
        return target;
    }
}
