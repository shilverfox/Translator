package com.jbsx.player.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.model.BaseDomainData;
import com.jbsx.R;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.detail.CommentDetailListHolder;

/**
 * 视频中的评论列表
 */

public class VideoCommentsHolder extends CommentDetailListHolder {
    private ImageView mIvBadComment;

    public VideoCommentsHolder(Context context, View view) {
        super(context, view);
    }

    @Override
    public void findViews(View rootView) {
        super.findViews(rootView);

        if (rootView != null) {
            mIvBadComment = rootView.findViewById(R.id.iv_video_bad_comment);
        }
    }

    @Override
    public void registerEvent() {
        super.registerEvent();

        mIvBadComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUserCase.requestBadComment(LoginHelper.getInstance().getUserToken(),
                        LoginHelper.getInstance().getUserId(), mData.getSpecialAlbumId(),
                        mData.getSpecialSingleId(), mData.getId(),
                        new BaseRequestCallback() {
                            @Override
                            public void onRequestFailed(BaseDomainData data) {
                                ShowTools.toast("举报失败，请重试");
                            }

                            @Override
                            public void onRequestSuccessful(String data) {
                                ShowTools.toast("举报成功");
                            }

                            @Override
                            public void onNetError() {

                            }
                        });
            }
        });
    }

    @Override
    public void drawViews(UserComments data, final int position) {
        super.drawViews(data, position);
    }
}
