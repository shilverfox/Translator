package com.jbsx.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;

public class TitleStatusHeightLayout extends LinearLayout {
    private View statusHeight;

    public TitleStatusHeightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleStatusHeightLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TitleStatusHeightLayout(Context context) {
        super(context);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        statusHeight = this.findViewById(R.id.statusheight);
        if (MainApplicationLike.statusBarHeight > 0) {
            statusHeight.getLayoutParams().height = MainApplicationLike.statusBarHeight;
        }
    }
}
