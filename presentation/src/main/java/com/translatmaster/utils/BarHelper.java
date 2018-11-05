package com.translatmaster.utils;

import android.view.View;

public class BarHelper {
    static void cleanAllBar(View content) {
        ProgressBarHelper.removeProgressBar(content);
        ErroBarHelper.removeErroBar(content);
    }
}
