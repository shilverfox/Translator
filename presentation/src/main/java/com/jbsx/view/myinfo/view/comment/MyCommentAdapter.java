package com.jbsx.view.myinfo.view.comment;

import android.content.Context;
import android.view.View;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.view.myinfo.view.message.MyInfoMessageViewHolder;

/**
 * 我的评论列表
 */

public class MyCommentAdapter extends CommonListFragmentAdapter {

    public MyCommentAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.my_comment_item_view;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        MyCommentViewHolder holder =  new MyCommentViewHolder(getContext(), rootView);
        holder.setAdapter(this);

        return holder;
    }
}
