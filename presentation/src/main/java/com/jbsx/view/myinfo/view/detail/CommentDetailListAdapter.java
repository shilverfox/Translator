package com.jbsx.view.myinfo.view.detail;

import android.content.Context;
import android.view.View;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.view.myinfo.view.comment.MyCommentViewHolder;

/**
 * 评论详情列表
 */

public class CommentDetailListAdapter extends CommonListFragmentAdapter {

    public CommentDetailListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.my_comment_item_header_view;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        CommentDetailListHolder holder =  new CommentDetailListHolder(getContext(), rootView);
        holder.setAdapter(this);

        return holder;
    }
}
