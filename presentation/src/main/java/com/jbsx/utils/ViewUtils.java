package com.jbsx.utils;

import android.widget.TextView;

public class ViewUtils {
    public static void setTextToView(TextView view, String text) {
        if (view != null && text != null) {
            view.setText(text);
        }
    }
}
