package com.jbsx.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DeviceUtil {
    public static String getDeviceInfo(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        // 屏幕宽度（像素）
        int width = metric.widthPixels;
        // 屏幕高度（像素）
        int height = metric.heightPixels;
        // 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
        // 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;

        String info = "机顶盒型号: " + android.os.Build.MODEL
                + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                + ",\n系统版本:" + android.os.Build.VERSION.RELEASE
                + "\n屏幕宽度（像素）: " + width
                + "\n屏幕高度（像素）: " + height
                + "\n屏幕密度:  " + density
                +"\n屏幕密度DPI: "+ densityDpi;
        return info;
    }
}
