package com.jbsx.customview.dialog;

import android.graphics.Color;
import android.view.View;

/**
 * 按钮字体大小颜色等设置
 */
public class DialogBtnParams {
    public final static int firstBtnDefaultColor = Color.parseColor("#999999");
    public final static int secondBtnDefaultColor = Color.parseColor("#47B34F");

    public int colorForText = Color.parseColor("#47B34F");
    public int bgColor = Color.parseColor("#ffffff");
    public View.OnClickListener listener;
    public String btnTitle;
    public boolean isClose = true;
    public boolean hasSetTextColor;


    public DialogBtnParams(String btnTitle, View.OnClickListener listener, int colorForText, int bgColor, boolean isClose) {
        this.colorForText = colorForText;
        this.bgColor = bgColor;
        this.listener = listener;
        this.btnTitle = btnTitle;
        this.isClose = isClose;
    }

    public DialogBtnParams(String btnTitle, View.OnClickListener listener, int colorForText, boolean isClose) {
        this.colorForText = colorForText;
        this.listener = listener;
        this.btnTitle = btnTitle;
        this.isClose = isClose;
    }

    public void setHasSetTextColor(boolean hasSetTextColor) {
        this.hasSetTextColor = hasSetTextColor;
    }
}
