package com.jbsx.view.myinfo.view.comment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.utils.Router;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.myinfo.data.CommentEvent;
import com.jbsx.view.myinfo.data.MyCommentData;
import com.jbsx.view.myinfo.data.MyInfoConst;
import com.jbsx.view.myinfo.data.MyMessageData;
import com.jbsx.view.myinfo.data.UserComments;

import org.greenrobot.eventbus.EventBus;

/**
 * 我的评论列表
 */

public class MyCommentViewHolder extends CommonListFragmentViewHolder<UserComments> {
    private Context mContext;

    private View mRootView;
    private TextView mTvCommentContent;
    private TextView mTvCommentCount;
    private TextView mTvNbCount;
    private TextView mTvCommentTime;
    private TextView mTvDelete;
    private ImageView mIvVideoImage;
    private TextView mTvVideoTitle;
    private TextView mTvVideoCount;
    private TextView mTvVideoCelebrity;

    private UserComments mData;
    private int mCurrentPosition;

    private CommonListFragmentAdapter mAdapter;

    private OnMyItemClickListener onMyItemClickListener;
    private OnSelectIconClickListener mOnSelectIconClickListener;

    public MyCommentViewHolder(Context context, View view) {
        super(view);
        mContext = context;
    }

    @Override
    public void findViews(View rootView) {
        if (rootView != null) {
            mRootView = rootView;

            mTvCommentContent = mRootView.findViewById(R.id.tv_comment_content);
            mTvCommentCount = mRootView.findViewById(R.id.tv_comment_count);
            mTvNbCount = mRootView.findViewById(R.id.tv_nb_count);
            mTvCommentTime = mRootView.findViewById(R.id.tv_comment_time);
            mTvDelete = mRootView.findViewById(R.id.tv_comment_delete);
            mIvVideoImage = mRootView.findViewById(R.id.iv_video_item_image);
            mTvVideoTitle = mRootView.findViewById(R.id.iv_video_item_description);
            mTvVideoCount = mRootView.findViewById(R.id.iv_video_item_amount);
            mTvVideoCelebrity = mRootView.findViewById(R.id.iv_video_item_host);
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

        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDeleteClick(mCurrentPosition);
            }
        });

        mTvCommentCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddCommentClick(mCurrentPosition);
            }
        });
    }

    /**
     * 删除评论
     *
     * @param position
     */
    private void handleDeleteClick(int position) {
        EventBus.getDefault().post(new CommentEvent(MyInfoConst.EVENT_BUS_DELETE_COMMENT, mData));
    }

    /**
     * 添加评论
     *
     * @param position
     */
    private void handleAddCommentClick(int position) {
        EventBus.getDefault().post(new CommentEvent(position, MyInfoConst.EVENT_BUS_ADD_COMMENT, mData));
    }

    @Override
    public void drawViews(UserComments data, final int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null) {
            mTvCommentContent.setText(data.getComment());
            mTvCommentCount.setText(data.getCommentReply() + "");
            mTvNbCount.setText(data.getCommentLove() + "");
            mTvCommentTime.setText(data.getCreatedAt());
            mTvVideoTitle.setText(data.getSpecialSingleTitle());
            mTvVideoCount.setVisibility(View.GONE);
            mTvVideoCelebrity.setVisibility(View.GONE);
        }
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

        // 查看详情
        EventBus.getDefault().post(new CommentEvent(MyInfoConst.EVENT_BUS_VIEW_DETAIL, mData));
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
