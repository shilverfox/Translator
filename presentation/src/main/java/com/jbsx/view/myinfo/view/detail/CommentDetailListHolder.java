package com.jbsx.view.myinfo.view.detail;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.myinfo.data.MyCommentData;
import com.jbsx.view.myinfo.data.MyMessageData;
import com.jbsx.view.myinfo.data.UserComments;

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

    private CommonListFragmentAdapter mAdapter;

    private OnMyItemClickListener onMyItemClickListener;
    private OnSelectIconClickListener mOnSelectIconClickListener;

    public CommentDetailListHolder(Context context, View view) {
        super(view);
        mContext = context;
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
            ImageLoader.displayImage("http://pic1.win4000.com/wallpaper/2018-01-09/5a54724e365b9.jpg", mIvUserHead, true);
            mTvUserName.setText(data.getAccount());
            mTvMessageContent.setText(data.getComment());
            mTvResponseTime.setText(data.getCreatedAt());
            mTvComment.setText("评论" + data.getCommentReply());
            mTvNbCount.setText(data.getCommentLove() + "");

            // 不显示评论回复
            mTvResponseContent.setVisibility(View.GONE);
        }
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
