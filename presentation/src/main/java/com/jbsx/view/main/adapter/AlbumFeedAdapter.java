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
import com.jbsx.view.main.entity.AlbumFeedData;

public class AlbumFeedAdapter extends CommonListFragmentAdapter {

    private OnFeedItemClick mFeedItemClick;
    private int mImageWidth;

    public AlbumFeedAdapter(Context context, int imageWidth, OnFeedItemClick callback) {
        super(context);
        mImageWidth = imageWidth;
        mFeedItemClick = callback;
    }

    @Override
    public int getViewId() {
        return R.layout.album_feed_item_view;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        AlbumFeedHolder holder = new AlbumFeedHolder(getContext(), rootView);
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

    public class AlbumFeedHolder extends CommonListFragmentViewHolder<AlbumFeedData.AlbumFeedItem> {
        private Context mContext;

        private View mRootView;
        private ImageView mIvImageUrl;
        private TextView mTvName;
        private TextView mTvDescription;

        private AlbumFeedData.AlbumFeedItem mData;
        private int mCurrentPosition;

        private CommonListFragmentAdapter mAdapter;

        public AlbumFeedHolder(Context context, View view) {
            super(view);
            mContext = context;
        }

        @Override
        public void findViews(View rootView) {
            if (rootView != null) {
                mRootView = rootView;
                mIvImageUrl = mRootView.findViewById(R.id.iv_album_item_image);
                mTvName = mRootView.findViewById(R.id.iv_album_item_name);
                mTvDescription = mRootView.findViewById(R.id.iv_album_item_desc);
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
        public void drawViews(AlbumFeedData.AlbumFeedItem data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null) {
                // 图片
                calculateImageHeight(mIvImageUrl);
                String imgUrl = data.getAlbumPreview();
                ImageLoader.displayImage(imgUrl, mIvImageUrl);
                ViewUtils.drawText(mTvName, data.getAlbumName());
                if (data.getMetadata() != null) {
                    ViewUtils.drawText(mTvDescription, data.getMetadata().getActor());
                }
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
                mFeedItemClick.onItemClick(mData.getAlbumCode());
            }
        }
    }
}
