package com.jbsx.view.myinfo.view.message;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.myinfo.data.MyMessageData;
import com.jbsx.view.myinfo.fragment.MyViewHistoryFragment;

/**
 * 我的消息列表
 */

public class MyInfoMessageViewHolder extends CommonListFragmentViewHolder<MyMessageData.MessageList> {
    private Context mContext;

    private View mRootView;
    private ImageView mIvUserHead;
    private TextView mTvUserName;
    private TextView mTvResponseTime;
    private TextView mTvMessageContent;
    private TextView mTvResponseContent;
    private MyMessageData.MessageList mData;
    private int mCurrentPosition;

    private CommonListFragmentAdapter mAdapter;

    private OnMyItemClickListener onMyItemClickListener;
    private OnSelectIconClickListener mOnSelectIconClickListener;

    public MyInfoMessageViewHolder(Context context, View view) {
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
    public void drawViews(MyMessageData.MessageList data, final int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null) {
            ImageLoader.displayImage("http://pic1.win4000.com/wallpaper/2018-01-09/5a54724e365b9.jpg", mIvUserHead, true);
            mTvUserName.setText(data.getReUserName() + getTip(data));
            mTvMessageContent.setText(data.getMessage());
            mTvResponseContent.setText(data.getReMessage());
            mTvResponseTime.setText(data.getReDate());
        }
    }

    private boolean isCommentType(String type) {
        return ConstData.MESSAGE_TYPE_COMMENT.equals(type);
    }

    private boolean isNbType(String type) {
        return ConstData.MESSAGE_TYPE_NB.equals(type);
    }

    private String getTip(MyMessageData.MessageList data) {
        String tip = "";
        String type = data.getMessageType();

        if (isCommentType(type)) {
            tip = "回复了你的评论";
        } else if (isNbType(type)) {
            tip = "赞了你的评论";
        }

        return tip;
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
