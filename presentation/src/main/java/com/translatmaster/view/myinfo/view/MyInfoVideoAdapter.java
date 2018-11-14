package com.translatmaster.view.myinfo.view;

import android.content.Context;
import android.view.View;

import com.translatmaster.R;
import com.translatmaster.customview.listFragment.CommonListFragmentAdapter;
import com.translatmaster.customview.listFragment.CommonListFragmentViewHolder;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class MyInfoVideoAdapter extends CommonListFragmentAdapter {

    public MyInfoVideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.my_info_videl_item;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        MyInfoVideoViewHolder holder =  new MyInfoVideoViewHolder(getContext(), rootView);
        holder.setAdapter(this);

        return holder;
    }
}
