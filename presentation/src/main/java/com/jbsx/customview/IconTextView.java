package com.jbsx.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 使用iconfont.ttf的textView
 */
public class IconTextView extends TextView {

    public IconTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(context);
    }

    private void setTypeface(Context context){
        this.setTypeface(Typeface.createFromAsset(context.getAssets(), "iconfont.ttf"));//设置图标字体文件
    }
}
