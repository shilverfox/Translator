package com.jbsx.view.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.utils.ViewUtils;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.callback.OnFeedItemClick;
import com.jbsx.view.main.entity.RepertoryData;

public class VideoFeedAdapter extends CommonListFragmentAdapter {

    private OnFeedItemClick mFeedItemClick;
    private int mImageWidth;

    public VideoFeedAdapter(Context context, int imageWidth, OnFeedItemClick callback) {
        super(context);
        mImageWidth = imageWidth;
        mFeedItemClick = callback;
    }

    @Override
    public int getViewId() {
        return R.layout.video_feed_item_view;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        VideoFeedHolder holder = new VideoFeedHolder(getContext(), rootView);
        holder.setAdapter(this);
        return holder;
    }

    private void calculateImageHeight(ImageView imageView) {
        if (imageView != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.height = mImageWidth;
            imageView.setLayoutParams(params);
        }
    }

    public class VideoFeedHolder extends CommonListFragmentViewHolder<RepertoryData.FeedItem> {
        private Context mContext;

        private View mRootView;
        private TextView mTvTitle;
        private TextView mTvDes;
        private ImageView mIvImageUrl;

        private RepertoryData.FeedItem mData;
        private int mCurrentPosition;

        private CommonListFragmentAdapter mAdapter;

        public VideoFeedHolder(Context context, View view) {
            super(view);
            mContext = context;
        }

        @Override
        public void findViews(View rootView) {
            if (rootView != null) {
                mRootView = rootView;
                mIvImageUrl = mRootView.findViewById(R.id.iv_video_item_image);
                mTvTitle = mRootView.findViewById(R.id.iv_video_item_name);
                mTvDes = mRootView.findViewById(R.id.iv_album_item_desc);
                mTvDes.setVisibility(View.VISIBLE);
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
        public void drawViews(RepertoryData.FeedItem data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null) {
                // 图片
                calculateImageHeight(mIvImageUrl);
                String imgUrl = data.getVideoPreview();
                ImageLoader.displayImage(imgUrl, mIvImageUrl);
                ViewUtils.drawText(mTvTitle, data.getVideoName());
                ViewUtils.drawText(mTvDes, data.getClassifyNames());
            }
        }

        public void setAdapter(CommonListFragmentAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void isTheLastLine(boolean theLastLine) {
        }

        private void handleRootViewClick(int position) {
            if (mFeedItemClick != null) {
                mFeedItemClick.onItemClick(mData.getVideoCode());
            }
        }
    }
}
