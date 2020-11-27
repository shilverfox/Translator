package com.jbsx.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.jbsx.utils.UiTools;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.entity.LocalVideoFeedData;
import com.jbsx.view.main.util.MainViewUtil;
import com.jbsx.view.main.util.PageUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 本地视频列表页
 */
public class LocalVideoFeedFragment extends CommonListFragment {
    public static final int GRID_COLUM = 4;

    public static final String ARGUMENT = "argument";
    private final static int PADDING_HORIZONTAL = LocalImageFeedFragment.PADDING_HORIZONTAL;

    private VideoFeedAdapter mAdapter;
    private LocalVideoFeedData mLocalVideoData;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    private String mClassicName;

    public LocalVideoFeedFragment() {
        // Required empty public constructor
    }

    public static LocalVideoFeedFragment newInstance(String naviId, String naviType, Integer pageType,
                                                     String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        LocalVideoFeedFragment contentFragment = new LocalVideoFeedFragment();
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
        View rootView = getRootView();
        if (rootView != null) {
            rootView.setBackgroundResource(R.drawable.background);
            getRv().setPadding(PADDING_HORIZONTAL, UiTools.dip2px(20), PADDING_HORIZONTAL, UiTools.dip2px(20));
        }
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        return HttpRequestPool.getLocalVideoFeedEntity(mClassicName, pageIndex);
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
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            float width = MainViewUtil.calculateFeedImageHeight(GRID_COLUM, PADDING_HORIZONTAL);
            params.height = (int) width;
            imageView.setLayoutParams(params);
        }
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        LocalVideoFeedData videoData = gson.fromJson(result, LocalVideoFeedData.class);
        if (videoData != null && videoData.getBody() != null
                && videoData.getBody().getLocalVideoList() != null) {
            handleHead(videoData.getBody().getClassifyList());
            return videoData.getBody().getLocalVideoList();
        }

        return new ArrayList();
    }

    /**
     * 是否已经增加了头部
     *
     * @return
     */
    private boolean hasHeadAdded() {
        ViewGroup headView = getHeaderView();
        return headView != null && headView.getChildCount() > 0;
    }

    /**
     * 顶部选项
     *
     * @param classifyList
     */
    private void handleHead(List<String> classifyList) {
        if (hasHeadAdded()) {
            return;
        }

        MainViewUtil.getLocalHeaderView(getHeaderView(), classifyList, new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                mClassicName = ((RadioButton)radioGroup.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                clearAndFresh();
            }
        });
        getHeaderView().setPadding(PADDING_HORIZONTAL, getHeaderView().getPaddingTop(),
                PADDING_HORIZONTAL, getHeaderView().getPaddingBottom());
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
        EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType,
                AppConstData.PAGE_TYPE_VIDEO_DETAIL, requestParams));
    }

    public class VideoFeedAdapter extends CommonListFragmentAdapter {

        public VideoFeedAdapter(Context context) {
            super(context);
        }

        @Override
        public int getViewId() {
            return R.layout.video_feed_item_view;
        }

        @Override
        public CommonListFragmentViewHolder getViewHolder(View rootView) {
            VideoFeedHolder holder =  new VideoFeedHolder(getContext(), rootView);
            holder.setAdapter(this);
            return holder;
        }
    }

    public class VideoFeedHolder extends CommonListFragmentViewHolder<LocalVideoFeedData.FeedItem> {
        private Context mContext;

        private View mRootView;
        private TextView mTvTitle;
        private ImageView mIvImageUrl;

        private LocalVideoFeedData.FeedItem mData;
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
        public void drawViews(LocalVideoFeedData.FeedItem data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null) {
                // 图片
                calculateImageHeight(mIvImageUrl);
                String imgUrl = data.getLocalVideoPreview();
                ImageLoader.displayImage(imgUrl, mIvImageUrl);
                mTvTitle.setText(data.getLocalVideoName());
            }
        }

        public void setAdapter(CommonListFragmentAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void isTheLastLine(boolean theLastLine) {
        }

        private void handleRootViewClick(int position) {
            handleItemClick(mData.getFilePath());
        }
    }
}