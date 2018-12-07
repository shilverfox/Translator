package com.jbsx.view.myinfo.view.detail;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.CommentsUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.CommentInputDialog;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.UpdateListEvent;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.util.CommentDetailHelper;

import org.greenrobot.eventbus.EventBus;

import org.greenrobot.eventbus.EventBus;

/**
 * 评论详情中的评论列表
 */

public class CommentDetailListHolder extends CommonListFragmentViewHolder<UserComments> {
    private Context mContext;

    private View mRootView;
    private ImageView mIvUserHead;
    private TextView mTvUserName;
    private TextView mTvComment;
    private TextView mTvNbCount;
    private TextView mTvResponseTime;
    private TextView mTvMessageContent;
    private TextView mTvResponseContent;
    private UserComments mData;
    private int mCurrentPosition;

    private CommentsUserCase mUserCase;

    /** 评论输入框 */
    private Dialog mInputCommentDialog;

    private CommonListFragmentAdapter mAdapter;

    private OnMyItemClickListener onMyItemClickListener;
    private OnSelectIconClickListener mOnSelectIconClickListener;

    public CommentDetailListHolder(Context context, View view) {
        super(view);
        mContext = context;
        mUserCase = new CommentsUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    @Override
    public void findViews(View rootView) {
        if (rootView != null) {
            mRootView = rootView;

            mTvResponseTime = mRootView.findViewById(R.id.img_sd_civ_time);
            mTvUserName = mRootView.findViewById(R.id.img_sd_civ_name);
            mTvMessageContent = mRootView.findViewById(R.id.img_sd_civ_content);
            mIvUserHead = mRootView.findViewById(R.id.img_sd_civ_icon);
            mTvResponseContent = mRootView.findViewById(R.id.tv_message_response);
            mTvComment = mRootView.findViewById(R.id.tv_comment_count);
            mTvNbCount = mRootView.findViewById(R.id.tv_nb_count);
        }
    }

    @Override
    public void registerEvent() {
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRootViewClick(mCurrentPosition);
            }
        });
    }

    @Override
    public void drawViews(UserComments data, final int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null) {
            ImageLoader.displayImage(data.getUserImage(), mIvUserHead, R.drawable.default_head, true);
            mTvUserName.setText(data.getAccount());
            mTvMessageContent.setText(data.getComment());
            mTvResponseTime.setText(data.getCreatedAt());
            mTvComment.setText("评论" + data.getCommentReply());
            mTvNbCount.setText(data.getCommentLove() + "");

            // 是否点赞
            boolean isLove = !(TextUtils.isEmpty(data.getIsLove()));
            mTvNbCount.setCompoundDrawables(UiTools.getDrawable(getThumbIcon(isLove), 16), null, null, null);

            mTvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    postComment();
                }
            });

            mTvNbCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    giveThumbToComment();
                }
            });

            // 不显示评论回复
            mTvResponseContent.setVisibility(View.GONE);
        }
    }

    /**
     * 点赞图标
     *
     * @param isLove
     * @return
     */
    private int getThumbIcon(boolean isLove) {
        return isLove ? R.drawable.thumb_small_red : R.drawable.thumb_small;
    }

    /**
     * 发表评论
     */
    private void postComment() {
        mInputCommentDialog = CommentInputDialog.showInputComment((Activity)mContext, "",
                new CommentInputDialog.CommentDialogListener() {
            @Override
            public void onClickPublish(Dialog dialog, EditText input, TextView btn) {
                mUserCase.requestPostComment(LoginHelper.getInstance().getUserToken(),
                        LoginHelper.getInstance().getUserId(), mData.getSpecialAlbumId(),
                        mData.getSpecialSingleId(), input.getText().toString(), mData.getId(),
                        new BaseRequestCallback() {
                            @Override
                            public void onRequestFailed(BaseDomainData data) {
                                ShowTools.toast("评论失败，请重试");
                            }

                            @Override
                            public void onRequestSuccessful(String data) {
                                // 评论成功，刷新adapter
                                ShowTools.toast("评论成功");
                                notifyDataUpdate();
                            }

                            @Override
                            public void onNetError() {

                            }
                        });
                mInputCommentDialog.dismiss();
            }

            @Override
            public void onShow(int[] inputViewCoordinatesOnScreen) {
            }

            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     * 点赞
     */
    private void giveThumbToComment() {
        mUserCase.requestGiveThumb(LoginHelper.getInstance().getUserToken(),
                LoginHelper.getInstance().getUserId(), mData.getSpecialAlbumId(),
                mData.getSpecialSingleId(), mData.getId(),
                new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        ShowTools.toast("点赞，请重试");
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        // 点赞成功，刷新adapter
                        ShowTools.toast("点赞成功");
                        notifyDataUpdate();
                    }

                    @Override
                    public void onNetError() {

                    }
                });
    }

    /**
     * 隐藏评论和赞
     * 主要是共外部调用，这个holder是公共使用的
     */
    public void hideCommentAndNb() {
        mTvComment.setVisibility(View.INVISIBLE);
        mTvNbCount.setVisibility(View.INVISIBLE);
    }

    private boolean isCommentType(String type) {
        return ConstData.MESSAGE_TYPE_COMMENT.equals(type);
    }

    private boolean isNbType(String type) {
        return ConstData.MESSAGE_TYPE_NB.equals(type);
    }

    public void setAdapter(CommonListFragmentAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    public void isTheLastLine(boolean theLastLine) {
    }

    private void handleRootViewClick(int position) {
        if (onMyItemClickListener != null) {
            onMyItemClickListener.onClick(position);
        }

        // 跳转到评论详情
        CommentDetailHelper.goToCommentDetailActivity((Activity)mContext, mData);
    }

    private void notifyDataUpdate() {
        EventBus.getDefault().post(new UpdateListEvent());
    }

    public interface OnMyItemClickListener {
        void onClick(int position);
    }

    public interface OnSelectIconClickListener {
        void onClick(int position);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }

    public void setOnSelectIconClickListener(OnSelectIconClickListener onMyItemClickListener) {
        mOnSelectIconClickListener = onMyItemClickListener;
    }
}
