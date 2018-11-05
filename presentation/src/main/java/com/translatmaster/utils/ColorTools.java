package com.translatmaster.utils;

import android.graphics.Color;
import android.text.TextUtils;

/**
 * Created by lijian15 on 2017/11/10.
 */

public class ColorTools {
    /**
     * 默认颜色：黑
     */
    private final static int DEFAULT_COLOR = 0xFF000000;

    public static int parseColor(String color) {
        return parseColor(color, DEFAULT_COLOR);
    }

    public static int parseColor(String color, int defaultColor) {
        int colorValue = defaultColor;

        if (!TextUtils.isEmpty(color)) {
            try {
                if (color.startsWith("#")) {//防止没有加#,容错处理
                    colorValue = Color.parseColor(color);
                } else {
                    colorValue = Color.parseColor("#" + color);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }

        return colorValue;
    }
}
