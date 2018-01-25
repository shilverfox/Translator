package com.translatmaster.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.translatmaster.app.MainApplicationLike;

/**
 * Created by lijian15 on 2016/12/13.
 */

public class ShowTools {
    public static void toast(String content) {
        if (!TextUtils.isEmpty(content)) {
            Toast.makeText(MainApplicationLike.getAppContext().getApplicationContext(), content,
                    Toast.LENGTH_LONG).show();
        }
    }
}