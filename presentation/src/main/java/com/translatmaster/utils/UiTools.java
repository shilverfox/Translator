package com.translatmaster.utils;

import com.translatmaster.app.MainApplicationLike;

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
}
