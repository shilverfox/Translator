package com.jbsx.view.main.fragment;

import android.app.Activity;
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
import com.jbsx.player.util.PlayerHelper;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.entity.RepertoryData;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.SpecialSingles;
import com.jbsx.view.main.entity.UserSingle;

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
        BaseRequestEntity entity = HttpRequestPool.getVideoFeedEntity("00000011", pageIndex);
        return entity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        mRepertoryData = gson.fromJson(result, RepertoryData.class);

        if (mRepertoryData != null && mRepertoryData.getBody() != null
                && mRepertoryData.getBody().getList() != null) {
            return mRepertoryData.getBody().getList();
        }

        return new ArrayList<SpecialSingles>();
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
            return R.layout.my_info_videl_item;
        }

        @Override
        public CommonListFragmentViewHolder getViewHolder(View rootView) {
            VideoFeedHolder holder =  new VideoFeedHolder(getContext(), rootView);
            holder.setAdapter(this);

            return holder;
        }
    }

    public class VideoFeedHolder extends CommonListFragmentViewHolder<UserSingle> {
        private Context mContext;

        private View mRootView;
        private TextView mTvGroup;
        private TextView mTvAmount;
        private TextView mTvTitle;
        private TextView mTvCelebrity;
        private ImageView mIvImageUrl;
        private ImageView mIvCheck;

        private UserSingle mData;
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
        public void drawViews(UserSingle data, final int position) {
            mData = data;
            mCurrentPosition = position;

            if (data != null && data.getSingle() != null) {
                final Single single = data.getSingle();

                // 图片
                String imgUrl = single.getAppImageUrl();
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
            PlayerHelper.gotoPlayer((Activity)mContext, PlayerHelper.makePlayerData(
                    mData.getSingle().getSpecialAlbumId(),
                    mData.getSingle().getId(), ConstData.VIDEO_DEFINITION_TYPE_STAND));
        }
    }
}
