package com.jbsx.view.main.fragment;

import android.content.Context;
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
 * 检索结果页
 */
public class SearchResultFragment extends CommonListFragment {
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

    private String mKeyWord;
    private int mSearchType;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance(String naviId, String naviType, Integer pageType,
                                                   String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        SearchResultFragment contentFragment = new SearchResultFragment();
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
        return HttpRequestPool.getSearchResultEntity(mKeyWord, mSearchType, pageIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        AlbumFeedData albumData = gson.fromJson(result, AlbumFeedData.class);
        if (albumData != null && albumData.getBody() != null
                && albumData.getBody().getList() != null
                && albumData.getBody().getList().size() > 0) {
            setTitle(albumData.getBody().getList().get(0).getClassifyName());
            return albumData.getBody().getList();
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
            return R.layout.album_feed_item_view;
        }

        @Override
        public CommonListFragmentViewHolder getViewHolder(View rootView) {
            AlbumFeedHolder holder =  new AlbumFeedHolder(getContext(), rootView);
            holder.setAdapter(this);
            return holder;
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