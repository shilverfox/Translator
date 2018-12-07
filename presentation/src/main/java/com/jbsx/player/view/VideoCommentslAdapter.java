package com.jbsx.player.view;

import android.content.Context;
import android.view.View;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.view.myinfo.view.detail.CommentDetailListAdapter;

/**
 * 视频评论详情列表
 */

public class VideoCommentslAdapter extends CommentDetailListAdapter {

    public VideoCommentslAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.video_comments_item_view;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        VideoCommentsHolder holder =  new VideoCommentsHolder(getContext(), rootView);
        holder.setAdapter(this);

        return holder;
    }
}
