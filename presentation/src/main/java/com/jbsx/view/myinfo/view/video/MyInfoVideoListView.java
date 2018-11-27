package com.jbsx.view.myinfo.view.video;

import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.ViewHistoryData;

import java.util.ArrayList;
import java.util.List;

/**
 * 视频列表
 * 我的收藏及观看历史
 */
public class MyInfoVideoListView extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private String mArgument;
    private MyInfoVideoAdapter mAdapter;
    private ViewHistoryData mHistoryData;

    /** 只有functionId不同 */
    private String mFunctionId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mArgument = bundle.getString(ARGUMENT);
        }
    }

    public static MyInfoVideoListView newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        MyInfoVideoListView contentFragment = new MyInfoVideoListView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new MyInfoVideoAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        BaseRequestEntity entity = HttpRequestPool.getMyVideoHistoryEntity(LoginHelper.getInstance().getUserToken(), pageIndex);
        entity.setFunctionId(mFunctionId);

        return entity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        mHistoryData = gson.fromJson(result, ViewHistoryData.class);

        if (mHistoryData != null && mHistoryData.getPayload() != null) {
            return mHistoryData.getPayload().getUserSingles();
        }

        return new ArrayList<ViewHistoryData.UserSingle>();
    }

    @Override
    public int getPageSize() {
        return ConstData.DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean needLoadByPage() {
        return true;
    }

    public void setFunctionId(String functionId) {
        mFunctionId = functionId;
    }

    public void freshAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<ViewHistoryData.UserSingle> getAllSings() {
        return mHistoryData.getPayload().getUserSingles();
    }

    private void handleSelectAll(boolean select) {
        List<ViewHistoryData.UserSingle> allSingle = getAllSings();
        if (allSingle != null) {
            for (ViewHistoryData.UserSingle  userSingle: allSingle) {
                Single single = userSingle.getSingle();
                single.setCheck(select);
            }
        }
    }

    public void selectAll() {
        handleSelectAll(true);
        freshAdapter();
    }

    public void unSelectAll() {
        handleSelectAll(false);
        freshAdapter();
    }

    public void delete() {

    }
}
