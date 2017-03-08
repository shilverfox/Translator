package com.translatmaster.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class LogTools {
    public static void e(String tag, String message) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.isEmpty(message)) {
            Log.e(tag, message);
        }
    }

    public static void e(String message) {
        if ( !TextUtils.isEmpty(message)) {
            Log.e("LogTools", message);
        }
    }
}
