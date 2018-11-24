package com.jbsx.player.fragment;

import android.os.Bundle;

import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.detail.CommentDetailListView;

/**
 * 视频的评论列表，结构和评论详情中的评论列表一样，只是请求参数不同
 */
public class VideoCommentsListView extends CommentDetailListView {
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

    public static VideoCommentsListView newInstance(UserComments argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        VideoCommentsListView contentFragment = new VideoCommentsListView();
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
}
