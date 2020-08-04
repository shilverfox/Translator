package com.jbsx.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.AlbumFeedData;
import com.jbsx.view.main.entity.RepertoryData;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频列表页
 */
public class VideoFeedFragment extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private VideoFeedAdapter mAdapter;
    private RepertoryData mRepertoryData;

    private String mRequestParams;
    private String mNaviType;
    private String mPageType;
    private String mNaviId;

    public VideoFeedFragment() {
        // Required empty public constructor
    }

    public static VideoFeedFragment newInstance(String naviId, String naviType, String pageType,
                                              String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putString(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        VideoFeedFragment contentFragment = new VideoFeedFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mNaviId = bundle.getString(AppConstData.INTENT_KEY_NAVI_ID);
            mNaviType = bundle.getString(AppConstData.INTENT_KEY_NAVI_TYPE);
            mPageType = bundle.getString(AppConstData.INTENT_KEY_PAGE_TYPE);
            mRequestParams = bundle.getString(AppConstData.INTENT_KEY_REQUEST_PARAMS);
        }
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new VideoFeedAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        if (isAlbumType()) {
            // 根据type确定是否为专辑列表
            return HttpRequestPool.getAlbumFeedEntity(mRequestParams, pageIndex);
        } else {
            // 默认是视频列表
            return HttpRequestPool.getVideoFeedEntity(mRequestParams, pageIndex);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /** 是否为专辑类型 */
    private boolean isAlbumType() {
        return AppConstData.PAGE_TYPE_ALBUM_2.equals(mPageType);
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        if (isAlbumType()) {
            AlbumFeedData albumData = gson.fromJson(result, AlbumFeedData.class);
            if (albumData != null && albumData.getBody() != null
                    && albumData.getBody().getList() != null) {
                return albumData.getBody().getList();
            }
        } else {
            RepertoryData videoData = gson.fromJson(result, RepertoryData.class);
            if (videoData != null && videoData.getBody() != null
                    && videoData.getBody().getList() != null) {
                return videoData.getBody().getList();
            }
        }

        return new ArrayList();
    }

    @Override
    public int getPageSize() {
        return ConstData.DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean needLoadByPage() {
        return true;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(mContext, 4);
    }

    public class VideoFeedAdapter extends CommonListFragmentAdapter {

        public VideoFeedAdapter(Context context) {
            super(context);
        }

        @Override
        public int getViewId() {
            return isAlbumType() ? R.layout.album_feed_item_view : R.layout.video_feed_item_view;
        }

        @Override
        public CommonListFragmentViewHolder getViewHolder(View rootView) {
            if (isAlbumType()) {
                AlbumFeedHolder holder =  new AlbumFeedHolder(getContext(), rootView);
                holder.setAdapter(this);
                return holder;
            } else {
                VideoFeedHolder holder =  new VideoFeedHolder(getContext(), rootView);
                holder.setAdapter(this);
                return holder;
            }
        }
    }

    public class VideoFeedHolder extends CommonListFragmentViewHolder<RepertoryData.FeedItem> {
        private Context mContext;

        private View mRootView;
        private TextView mTvGroup;
        private TextView mTvAmount;
        private TextView mTvTitle;
        private TextView mTvCelebrity;
        private ImageView mIvImageUrl;
        private ImageView mIvCheck;

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

                mTvGroup  = mRootView.findViewById(R.id.iv_my_info_video_group);
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
        public void drawViews(RepertoryData.FeedItem data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null) {
                // 图片
//                String imgUrl = data.get.getAppImageUrl();
//                ImageLoader.displayImage(imgUrl, mIvImageUrl);
            }
        }

        public void setAdapter(CommonListFragmentAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void isTheLastLine(boolean theLastLine) {
        }

        private void handleRootViewClick(int position) {
        }
    }

    public class AlbumFeedHolder extends CommonListFragmentViewHolder<AlbumFeedData.AlbumFeedItem> {
        private Context mContext;

        private View mRootView;
        private ImageView mIvImageUrl;

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
                String imgUrl = data.getAlbumPreview();
                ImageLoader.displayImage(imgUrl, mIvImageUrl);
            }
        }

        public void setAdapter(CommonListFragmentAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void isTheLastLine(boolean theLastLine) {
        }

        private void handleRootViewClick(int position) {
        }
    }
}
