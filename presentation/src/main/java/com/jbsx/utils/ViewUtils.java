package com.jbsx.utils;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class ViewUtils {
    public static void setTextToView(TextView view, String text) {
        if (view != null && text != null) {
            view.setText(text);
        }
    }

    public static void drawText(TextView view, String text) {
        if (view != null) {
            view.setText(TextUtils.isEmpty(text) ? "" : text);
        }
    }

    public static void drawText(TextView view, Spanned text) {
        if (view != null) {
            view.setText(TextUtils.isEmpty(text) ? "" : text);
        }
    }

    public static void drawText(TextView view, SpannableString text) {
        if (view != null) {
            view.setText(TextUtils.isEmpty(text) ? "" : text);
        }
    }

    public static SpannableString getBoldText(String content, int start, int end) {
        boolean dataValie = !TextUtils.isEmpty(content) && start >= 0 && start < content.length()
                && end >= start && end < content.length();
        if (!dataValie) {
            return new SpannableString("");
        }

        SpannableString spannableString = new SpannableString(content);
        StyleSpan colorSpan = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return spannableString;
    }
}
