package com.jbsx.view.main.fragment;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.StatisticsReportUtil;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.ViewUtils;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.entity.AlbumFeedData;
import com.jbsx.view.main.entity.RepertoryData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频列表页
 */
public class VideoFeedFragment extends CommonListFragment {
    public static final int GRID_COLUM = 5;
    public static final int PADDING_HORIZONTAL = UiTools.dip2px(60);

    public static final String ARGUMENT = "argument";

    private VideoFeedAdapter mAdapter;
    private RepertoryData mRepertoryData;
    private TextView mTvTitle;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    public VideoFeedFragment() {
        // Required empty public constructor
    }

    public static VideoFeedFragment newInstance(String naviId, String naviType, Integer pageType,
                                              String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
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
            mPageType = bundle.getInt(AppConstData.INTENT_KEY_PAGE_TYPE);
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
    public void initViews() {
        mTvTitle = getTitleView();
        View rootView = getRootView();
        if (rootView != null) {
            rootView.setBackgroundResource(R.drawable.background);
            rootView.setPadding(PADDING_HORIZONTAL, UiTools.dip2px(20),PADDING_HORIZONTAL, UiTools.dip2px(20));
        }
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
        return AppConstData.PAGE_TYPE_ALBUM_2 == mPageType;
    }

    /**
     * 根据屏幕宽度和宽高比计算高度
     * @param imageView
     */
    private void calculateImageHeight(ImageView imageView) {
        if (imageView != null) {
            int itemPadding = (int)mContext.getResources().getDimension(R.dimen.video_feed_item_padding);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            float width = (StatisticsReportUtil.getScreenWidth() - (GRID_COLUM - 1)*UiTools.dip2px(itemPadding) - 2*PADDING_HORIZONTAL) / GRID_COLUM;
            params.height = (int) width;
            imageView.setLayoutParams(params);
        }
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        if (isAlbumType()) {
            AlbumFeedData albumData = gson.fromJson(result, AlbumFeedData.class);
            if (albumData != null && albumData.getBody() != null
                    && albumData.getBody().getList() != null
                    && albumData.getBody().getList().size() > 0) {
                setTitle(albumData.getBody().getList().get(0).getClassifyName());
                return albumData.getBody().getList();
            }
        } else {
            RepertoryData videoData = gson.fromJson(result, RepertoryData.class);
            if (videoData != null && videoData.getBody() != null
                    && videoData.getBody().getList() != null
                    && videoData.getBody().getList().size() > 0) {
                setTitle(videoData.getBody().getList().get(0).getClassifyNames());
                return videoData.getBody().getList();
            }
        }

        return new ArrayList();
    }

    private void setTitle(String titleName) {
        boolean isEmpty = TextUtils.isEmpty(titleName);
        mTvTitle.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        if (!isEmpty) {
            mTvTitle.setText(titleName);
        }
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
        return new GridLayoutManager(mContext, GRID_COLUM);
    }

    private void handleItemClick(String requestParams) {
        EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType, mPageType, requestParams));
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
        private TextView mTvTitle;
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
                mTvTitle.setText(data.getVideoName());
            }
        }

        public void setAdapter(CommonListFragmentAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void isTheLastLine(boolean theLastLine) {
        }

        private void handleRootViewClick(int position) {
            handleItemClick(mData.getVideoCode());
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
            handleItemClick(mData.getAlbumCode());
        }
    }
}