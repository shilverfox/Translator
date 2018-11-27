package com.jbsx.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

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
}
