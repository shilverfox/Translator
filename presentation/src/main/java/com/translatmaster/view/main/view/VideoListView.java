package com.translatmaster.view.main.view;

import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.translatmaster.customview.listFragment.CommonListFragment;
import com.translatmaster.customview.listFragment.CommonListFragmentAdapter;
import com.translatmaster.view.main.entity.RepertoryData;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示视频列表，公共view
 * 首页片库中的视频列表，搜索结果页都可以用
 */
public class VideoListView extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private String mArgument;
    private VideoAdapter mAdapter;
    private RepertoryData mRepertoryData;
    private int mCelebrityId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
        }
    }

    public static VideoListView newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        VideoListView contentFragment = new VideoListView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    /**
     * 设置要查询的名家id列表
     *
     * @param celebrityId
     */
    public void setCelebrityId(int celebrityId) {
        mCelebrityId = celebrityId;
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new VideoAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        return HttpRequestPool.getRepertoryListEntity(mCelebrityId, pageIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        mRepertoryData = gson.fromJson(result, RepertoryData.class);

        if (mRepertoryData != null || mRepertoryData.getPayload() != null) {
            return mRepertoryData.getPayload().getSpecialSingles();
        }

        return new ArrayList<RepertoryData.SpecialSingles>();
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