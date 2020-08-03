package com.jbsx.player.view;

import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.detail.CommentDetailListAdapter;
import com.jbsx.view.myinfo.view.detail.CommentDetailListView;

/**
 * 视频的评论列表，结构和评论详情中的评论列表一样，只是请求参数不同
 */
public class VideoCommentsView extends CommentDetailListView {
    public static final String ARGUMENT = "argument";

    private UserComments mRequestData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestData = bundle.getParcelable(ARGUMENT);
        }
    }

    public static VideoCommentsView newInstance(UserComments argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        VideoCommentsView contentFragment = new VideoCommentsView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        BaseRequestEntity entity = HttpRequestPool.getVideoCommentsEntity(
                LoginHelper.getInstance().getUserToken(), mRequestData.getSpecialAlbumId(),
                mRequestData.getSpecialSingleId(), pageIndex);
        return entity;
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new VideoCommentslAdapter(mContext);
        return mAdapter;
    }
}
