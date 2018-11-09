package com.translatmaster.utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;

import com.translatmaster.R;

public class RecyclerViewHelper {
    public static DividerItemDecoration getDivider(Context context) {
        DividerItemDecoration divider = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.default_list_divider));

        return divider;
    }
}
