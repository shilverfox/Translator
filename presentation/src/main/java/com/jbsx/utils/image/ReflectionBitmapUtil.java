package com.jbsx.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

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
        final int GAP = 8;

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
}
