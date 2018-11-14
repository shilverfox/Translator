package com.translatmaster.view.myinfo.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.customview.listFragment.CommonListFragmentAdapter;
import com.translatmaster.customview.listFragment.CommonListFragmentViewHolder;
import com.translatmaster.utils.image.ImageLoader;
import com.translatmaster.utils.DataUtil;
import com.translatmaster.view.main.entity.Single;
import com.translatmaster.view.main.entity.ViewHistoryData;
import com.translatmaster.view.myinfo.fragment.MyViewHistoryFragment;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class MyInfoVideoViewHolder extends CommonListFragmentViewHolder<ViewHistoryData.UserSingle> {
    private Context mContext;

    private View mRootView;
    private TextView mTvAmount;
    private TextView mTvTitle;
    private TextView mTvCelebrity;
    private ImageView mIvImageUrl;
    private ImageView mIvCheck;

    private ViewHistoryData.UserSingle mData;
    private int mCurrentPosition;

    private CommonListFragmentAdapter mAdapter;

    private OnMyItemClickListener onMyItemClickListener;
    private OnSelectIconClickListener mOnSelectIconClickListener;

    public MyInfoVideoViewHolder(Context context, View view) {
        super(view);
        mContext = context;
    }

    @Override
    public void findViews(View rootView) {
        if (rootView != null) {
            mRootView = rootView;

            mTvAmount = mRootView.findViewById(R.id.iv_video_item_amount);
            mTvTitle = mRootView.findViewById(R.id.iv_video_item_description);
            mTvCelebrity = mRootView.findViewById(R.id.iv_video_item_host);
            mIvImageUrl = mRootView.findViewById(R.id.iv_video_item_image);
            mIvCheck = mRootView.findViewById(R.id.iv_my_info_video_check);
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
    public void drawViews(ViewHistoryData.UserSingle data, final int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null && data.getSingle() != null) {
            final Single single = data.getSingle();

            // 单一一级不显示数量
            mTvAmount.setVisibility(View.INVISIBLE);

            // 描述、标题
            mTvTitle.setText(single.getTitle());

            // 时长
            if (mTvCelebrity != null) {
                mTvCelebrity.setText(getViewTime());
            }

            // 选中
            mIvCheck.setVisibility(MyViewHistoryFragment.mCanSelectItem ? View.VISIBLE : View.GONE);
            boolean isCheck = single.isCheck();
            mIvCheck.setImageResource(isCheck ? R.drawable.icon_check_green : R.drawable.icon_uncheck_green);

            mIvCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnSelectIconClickListener != null) {
                        mOnSelectIconClickListener.onClick(position);
                    }

                    if (mAdapter != null) {
                        mAdapter.notifyItemChanged(position);
                    }

                    single.setCheck(!single.isCheck());
                }
            });

            // 图片
            String imgUrl = single.getAppImageUrl();
            ImageLoader.displayImage(imgUrl, mIvImageUrl);
        }
    }

    public void setAdapter(CommonListFragmentAdapter adapter) {
        mAdapter = adapter;
    }

    /**
     * 观看时常
     *
     * @return
     */
    private String getViewTime() {
        String time = "";

        if (mData != null) {
            time = mData.getCreatedAt();
        }
        return "观看至：" + time;
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
