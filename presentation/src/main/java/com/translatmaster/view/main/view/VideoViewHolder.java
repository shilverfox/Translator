package com.translatmaster.view.main.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.customview.listFragment.CommonListFragmentViewHolder;
import com.translatmaster.data.AppConstData;
import com.translatmaster.utils.image.ImageLoader;
import com.translatmaster.view.login.util.DataUtil;
import com.translatmaster.view.main.entity.Celebrities;
import com.translatmaster.view.main.entity.RepertoryData;
import com.translatmaster.view.main.entity.Single;

import java.util.List;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class VideoViewHolder extends CommonListFragmentViewHolder<RepertoryData.SpecialSingles> {
    private Context mContext;

    private View mRootView;
    private TextView mTvAmount;
    private TextView mTvTitle;
    private TextView mTvCelebrity;
    private ImageView mIvImageUrl;

    private RepertoryData.SpecialSingles mData;
    private int mCurrentPosition;

    private OnMyItemClickListener onMyItemClickListener;

    public VideoViewHolder(Context context, View view) {
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
    public void drawViews(RepertoryData.SpecialSingles data, int position) {
        mData = data;
        mCurrentPosition = position;

        if (data != null && data.getSingle() != null) {
            Single single = data.getSingle();

            // 单一一级不显示数量
            mTvAmount.setVisibility(View.INVISIBLE);

            // 描述、标题
            mTvTitle.setText(single.getTitle());

            // 主讲
            if (mTvCelebrity != null) {
                mTvCelebrity.setText(getCelebrity());
            }

            // 图片
            String imgUrl = single.getAppImageUrl();
            ImageLoader.displayImage(imgUrl, mIvImageUrl);
        }
    }

    /**
     * 主讲人
     *
     * @return
     */
    private String getCelebrity() {
        String name = "";

        if (mData != null) {
            name = DataUtil.getCelebrity(mData.getCelebrities());
        }
        return "主讲：" + name;
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

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }
}
