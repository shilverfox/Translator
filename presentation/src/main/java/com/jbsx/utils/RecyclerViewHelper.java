package com.jbsx.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;

import com.jbsx.R;
import com.jbsx.customview.recyclerview.NoBottomLineDividerItemDecoration;

public class RecyclerViewHelper {
    public static DividerItemDecoration getDivider(Context context) {
        return getDivider(context, false);
    }

    /**
     *
     * @param context
     * @param needBottomLine 是否需要底部分割线
     * @return
     */
    public static DividerItemDecoration getDivider(Context context, boolean needBottomLine) {
        DividerItemDecoration divider = new NoBottomLineDividerItemDecoration(context, DividerItemDecoration.VERTICAL, needBottomLine);
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.default_list_divider));

        return divider;
    }
}
