package com.jbsx.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jbsx.app.MainApplicationLike;

public class UiTools {
    public UiTools() {
    }

    public static int dip2px(float dpValue) {
        float scale = MainApplicationLike.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2dip(float pxValue) {
        float scale = MainApplicationLike.getAppContext().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     *
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = MainApplicationLike.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(float spValue) {
        final float fontScale = MainApplicationLike.getAppContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    public static Drawable getDrawable(int resId, int size) {
        Drawable drawable = null;
        try {
            drawable = MainApplicationLike.getAppContext().getResources().getDrawable(resId);
            drawable.setBounds(0, 0, UiTools.dip2px(size), UiTools.dip2px(size));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return drawable;
    }

    /**
     * 动态测量wrap模式下控件大小
     *
     * @param view
     */
    public static void measureForWrap(View view) {
        if (view != null) {
            int width = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);
            int height = View.MeasureSpec.makeMeasureSpec((1 << 30) - 1, View.MeasureSpec.AT_MOST);

            view.measure(width, height);
        }
    }

    /**
     * 动态测量给定大小的控件的实际尺寸
     *
     * @param view
     */
    public static void measureForExactly(View view) {
        if (view != null) {
            int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);
            int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.EXACTLY);

            view.measure(width, height);
        }
    }
}
