package com.jbsx.view.search.view;

import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.main.entity.RepertoryData;
import com.jbsx.view.main.entity.SpecialSingles;
import com.jbsx.view.search.entity.SearchEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果列表
 *
 * 显示视频列表，公共view
 * 首页片库中的视频列表，搜索结果页都可以用
 */
public class SearchResultView extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private SearchEvent mSearchData;
    private SearchResultAdapter mAdapter;
    private RepertoryData mRepertoryData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mSearchData = bundle.getParcelable(ARGUMENT);
        }
    }

    public static SearchResultView newInstance(SearchEvent argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        SearchResultView contentFragment = new SearchResultView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    /**
     * 设置要查询的数据
     */
    public void setSearchData(SearchEvent searchData) {
        mSearchData = searchData;
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new SearchResultAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        if (mSearchData != null) {
            return HttpRequestPool.getSearchEntity(mSearchData.getCelebrityId(),
                    mSearchData.getSearchKey(), mSearchData.getSearchType(),
                    mSearchData.getSort(), pageIndex);
        } else {
            return null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        mRepertoryData = gson.fromJson(result, RepertoryData.class);

        if (mRepertoryData != null && mRepertoryData.getPayload() != null) {
            return mRepertoryData.getPayload().getSpecialSingles();
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
}
