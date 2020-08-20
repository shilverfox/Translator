package com.jbsx.view.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
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
import com.jbsx.utils.JsonUtils;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.StatisticsReportUtil;
import com.jbsx.utils.UiTools;
import com.jbsx.utils.ViewUtils;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.data.SearchEvent;
import com.jbsx.view.main.adapter.AlbumFeedAdapter;
import com.jbsx.view.main.adapter.CommonFeedAdapter;
import com.jbsx.view.main.adapter.SearchResultAdapter;
import com.jbsx.view.main.adapter.VideoFeedAdapter;
import com.jbsx.view.main.callback.OnFeedItemClick;
import com.jbsx.view.main.entity.AlbumFeedData;
import com.jbsx.view.main.entity.RepertoryData;
import com.jbsx.view.main.entity.SearchResultData;
import com.jbsx.view.main.util.MainViewUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 检索结果页
 */
public class SearchResultFragment extends CommonListFragment {
    public static final int GRID_COLUM = 5;
    public static final int PADDING_HORIZONTAL = VideoFeedFragment.PADDING_HORIZONTAL;

    public static final String ARGUMENT = "argument";

    private CommonListFragmentAdapter mAdapter;

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
            parseParams();
        }
    }

    private void parseParams() {
        JSONObject json = null;
        try {
            json = new JSONObject(mRequestParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (json != null) {
            mKeyWord = JsonUtils.parseString(json, "search_key");
            mSearchType = JsonUtils.parseInt(json, "search_type");
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
        mAdapter = new SearchResultAdapter(mContext, getImageWidth(), new OnFeedItemClick() {
            @Override
            public void onItemClick(String code) {
                handleItemClick(code);
            }
        });
        return mAdapter;
    }

    /**
     * 查询类型
     *
     * @return
     */
    private boolean isAlbumType() {
        return mSearchType == AppConstData.SEARCH_TYPE_ALBUM;
    }

    @Override
    public void initViews() {
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
     */
    private int getImageWidth() {
        return MainViewUtil.calculateFeedImageHeight(GRID_COLUM, PADDING_HORIZONTAL);
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        SearchResultData searchResultData = gson.fromJson(result, SearchResultData.class);
        if (searchResultData != null && searchResultData.getBody() != null
                && searchResultData.getBody().getList() != null
                && searchResultData.getBody().getList().size() > 0) {
            setTitle("检索结果");
            return searchResultData.getBody().getList();
        }

        return new ArrayList();
    }

    private void setTitle(String titleName) {
        ViewGroup headView = getHeaderView();
        boolean isEmpty = TextUtils.isEmpty(titleName);
        TextView textView = new TextView(mContext);
        textView.setTextColor(0xffffffff);
        textView.setTextSize(20);
        headView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        if (!isEmpty) {
            textView.setText(titleName);
        }

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        headView.addView(textView, params);
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
        // 使用视频或专辑feed的页面类型
        Integer pageType = (isAlbumType()) ? AppConstData.PAGE_TYPE_ALBUM_2 : AppConstData.PAGE_TYPE_VIDEO_FEED;
        EventBus.getDefault().post(new PageChangeEvent(mNaviId, mNaviType, pageType, requestParams));
    }

    /**
     * page 切换事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SearchEvent event) {
        if (event != null && mNaviId != null && mNaviId.equals(event.mNaviId)) {
            mRequestParams = event.mRequestParam;
            parseParams();
            clearAndFresh();
        }
    }
}