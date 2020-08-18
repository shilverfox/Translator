package com.jbsx.view.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ViewUtils;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.callback.OnFeedItemClick;
import com.jbsx.view.main.entity.SearchResultData;

import java.util.List;

/**
 * 通用列表
 * 检索、视频、专辑公用
 */
public class CommonFeedAdapter extends CommonListFragmentAdapter {

    private OnFeedItemClick mFeedItemClick;
    private int mImageWidth;

    public CommonFeedAdapter(Context context, int imageWidth, OnFeedItemClick callback) {
        super(context);
        mImageWidth = imageWidth;
        mFeedItemClick = callback;
    }

    @Override
    public CommonListFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(getContext()).inflate(getViewId(viewType), null, false);
        return getViewHolder(rootView, viewType);
    }

    private boolean isAlbum(int viewType) {
        return viewType == AppConstData.TYPE_RESOURCE_ALBUM;
    }

    @Override
    public int getViewId() {
        return getViewId(AppConstData.TYPE_RESOURCE_ALBUM);
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        return getViewHolder(rootView, getViewId());
    }

    @Override
    public int getItemViewType(int position) {
        return ((List<SearchResultData.SearchResultItem>)getData()).get(position).getResourceType();
    }

    public int getViewId(int viewType) {
        return isAlbum(viewType) ? R.layout.album_feed_item_view : R.layout.video_feed_item_view;
    }

    public CommonListFragmentViewHolder getViewHolder(View rootView, int viewType) {
        if (isAlbum(viewType)) {
            CommonFeedAlbumHolder holder = new CommonFeedAlbumHolder(getContext(), rootView);
            holder.setAdapter(this);
            return holder;
        } else {
            CommonFeedVideoHolder holder = new CommonFeedVideoHolder(getContext(), rootView);
            holder.setAdapter(this);
            return holder;
        }
    }

    private void calculateImageHeight(ImageView imageView) {
        if (imageView != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.height = mImageWidth;
            imageView.setLayoutParams(params);
        }
    }

    public class CommonFeedAlbumHolder extends CommonListFragmentViewHolder<SearchResultData.SearchResultItem> {
        private Context mContext;

        private View mRootView;
        private ImageView mIvImageUrl;
        private TextView mTvName;
        private TextView mTvDescription;

        private SearchResultData.SearchResultItem mData;
        private int mCurrentPosition;

        private CommonListFragmentAdapter mAdapter;

        public CommonFeedAlbumHolder(Context context, View view) {
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
        public void drawViews(SearchResultData.SearchResultItem data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null) {
                // 图片
                calculateImageHeight(mIvImageUrl);
                String imgUrl = data.getResourcePreview();
                ImageLoader.displayImage(imgUrl, mIvImageUrl);
                ViewUtils.drawText(mTvName, data.getResourceName());
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
                mFeedItemClick.onItemClick(mData.getResourceCode());
            }
        }
    }

    public class CommonFeedVideoHolder extends CommonListFragmentViewHolder<SearchResultData.SearchResultItem> {
        private Context mContext;

        private View mRootView;
        private TextView mTvTitle;
        private ImageView mIvImageUrl;

        private SearchResultData.SearchResultItem mData;
        private int mCurrentPosition;

        private CommonListFragmentAdapter mAdapter;

        public CommonFeedVideoHolder(Context context, View view) {
            super(view);
            mContext = context;
        }

        @Override
        public void findViews(View rootView) {
            if (rootView != null) {
                mRootView = rootView;
                mIvImageUrl = mRootView.findViewById(R.id.iv_video_item_image);
                mTvTitle = mRootView.findViewById(R.id.iv_video_item_name);
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
        public void drawViews(SearchResultData.SearchResultItem data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null) {
                // 图片
                calculateImageHeight(mIvImageUrl);
                String imgUrl = data.getResourcePreview();
                ImageLoader.displayImage(imgUrl, mIvImageUrl);
                mTvTitle.setText(data.getResourceName());
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
                mFeedItemClick.onItemClick(mData.getResourceCode());
            }
        }
    }
}
