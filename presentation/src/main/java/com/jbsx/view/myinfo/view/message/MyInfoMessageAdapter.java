package com.jbsx.view.myinfo.view.message;

import android.content.Context;
import android.view.View;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.view.myinfo.view.video.MyInfoVideoViewHolder;

/**
 * 我的消息列表
 */

public class MyInfoMessageAdapter extends CommonListFragmentAdapter {

    public MyInfoMessageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.my_comment_item_header_view;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        MyInfoMessageViewHolder holder =  new MyInfoMessageViewHolder(getContext(), rootView);
        holder.setAdapter(this);

        return holder;
    }
}
