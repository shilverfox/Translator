package com.jbsx.view.myinfo.view.video;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.UserSingle;
import com.jbsx.view.main.entity.ViewHistoryData;
import com.jbsx.view.myinfo.util.SortListUtil;

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

    /** 编辑按钮 */
    private View mBtnSelection;

    /** 只有functionId不同 */
    private String mFunctionId;

    private IDataListener mDataListener;

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

    public void setBtnSelection(View view) {
        mBtnSelection = view;
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    /**
     * 清空Adapter数据
     */
    public void clearAdapter() {
        mAdapter.clearData();
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new MyInfoVideoAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        BaseRequestEntity entity = HttpRequestPool.getVideoFeedEntity(LoginHelper.getInstance().getUserToken(), pageIndex);
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
            List<UserSingle> emptySource = new ArrayList<>();
            List<UserSingle> data = SortListUtil.makeWrappedList(needClearData() ? emptySource : mAdapter.getData(),
                    mHistoryData.getPayload().getUserSingles());

            // 通知数据回调
            handleDataCallback(data == null ? 0 : data.size());

            return data;
        }

        return new ArrayList<UserSingle>();
    }

    @Override
    public void handleUiByData() {
        handleBtnVisible();
    }

    /**
     * 有数据再显示右上角的编辑按钮
     */
    private void handleBtnVisible() {
        boolean hasList = mAdapter.getItemCount() > 0;
        if (mBtnSelection != null) {
            mBtnSelection.setVisibility(hasList ? View.VISIBLE : View.INVISIBLE);
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

    public void setFunctionId(String functionId) {
        mFunctionId = functionId;
    }

    public void freshAdapter() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private List<UserSingle> getAllSings() {
        return mHistoryData.getPayload().getUserSingles();
    }

    private void handleSelectAll(boolean select) {
        List<UserSingle> allSingle = getAllSings();
        if (allSingle != null) {
            for (UserSingle  userSingle: allSingle) {
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

    public void setDataListener(IDataListener listener) {
        mDataListener = listener;
    }

    private void handleDataCallback(int count) {
        if (mDataListener != null) {
            mDataListener.onLoadDataSuccess(count);
        }
    }
}
