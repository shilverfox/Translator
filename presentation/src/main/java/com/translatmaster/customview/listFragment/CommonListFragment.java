package com.translatmaster.customview.listFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.CommonListFragmentUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.net.model.BaseRequestEntity;
import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.customview.recyclerview.EndlessRecyclerOnScrollListener;
import com.translatmaster.customview.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.translatmaster.customview.recyclerview.LoadingFooter;
import com.translatmaster.customview.recyclerview.RecyclerViewStateUtils;
import com.translatmaster.utils.ErroBarHelper;
import com.translatmaster.utils.MessageTools;
import com.translatmaster.utils.ProgressBarHelper;
import com.translatmaster.utils.RecyclerViewHelper;

import java.util.List;

/**
 * 通用的包含RecyclerView的Fragment
 *
 * Created by lijian15 on 2017/9/4.
 */

public abstract class CommonListFragment<T> extends BaseFragment {
    private final static String ERROR_INFO_NO_DATA = "无数据";

    /** 每页条数 */
    public final static int PAGE_SIZE = 10;

    /** 翻页索引起始值 */
    private final static int DEFAULT_START_PAGE = 1;

    /** 是否有下页，默认有 */
    private boolean mHasNextPage = true;

    /** 页面索引，从1计数 */
    private int mPageIndex = DEFAULT_START_PAGE;

    /** 最大页数，-1为无限制 */
    private int mMaxPage = -1;

    /** 当前请求是否需要清空数据 */
    private boolean mNeedClearData;

    /** 每页数量 */
    private int mPageSize;

    /** 列表无数据时的提示文案 */
    private String mNoDataAlertContent;

    /** 接口异常时返回的提示文案 */
    private String mInvalidResponseMessage;

    private CommonListFragmentUserCase mUserCase;

    private View mRootView;
    protected RecyclerView mListView;
    private CommonListFragmentAdapter mAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_common_list, null, false);
        mPageSize = getPageSize();
        mUserCase = new CommonListFragmentUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());

        findViews(mRootView);
        setListAdapter();

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
            public void run() {
                loadAllData(true);
            }
        }, 200);
    }

    /**
     * 初始化View对象
     */
    private void findViews(View rootView) {
        if (rootView != null) {
            mListView = (RecyclerView) rootView.findViewById(R.id.recycler_list);
        }
    }

    private void setListAdapter() {
        mAdapter = getAdapter(mContext);
        mListView.setLayoutManager(new LinearLayoutManager(mContext));
        mListView.addItemDecoration(RecyclerViewHelper.getDivider(mContext));

        if (needLoadByPage()) {
            // 翻页，第三方框架实现RecycleView，增加页脚信息
            mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
            mListView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
            mListView.addOnScrollListener(mOnScrollListener);
        } else {
            mListView.setAdapter(mAdapter);
        }
    }

    /**
     * 加载数据
     *
     * @param clearData 是否需要清空原数据
     */
    protected void loadAllData(boolean clearData) {
        mNeedClearData = clearData;

        if (clearData) {
            // 清空
            mPageIndex = DEFAULT_START_PAGE;
            ProgressBarHelper.addProgressBar(mListView, false, false);
        } else {
            // 下一页
            mPageIndex++;
        }

        handleLoadData();
    }

    /**
     * 加载所有数据
     */
    private void handleLoadData() {
        BaseRequestEntity entity = getRequestEntity(mPageIndex);
        if (entity != null) {
            mUserCase.handleSendRequest(entity, new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    setInvalidResponseMessage(MessageTools.getMessage(data));
                    handleServerError();
                }

                @Override
                public void onRequestSuccessful(String data) {
                    List<T> allList = parseList(data);
                    handleSuccess(allList);
                }

                @Override
                public void onNetError() {
                    handleNetError();
                }
            });
        }
    }

    private void handleSuccess(List<T> allList) {
        handleList(allList);
        ProgressBarHelper.removeProgressBar(mListView);
    }

    private void handleDataEmpty() {
        if (!isTheFirstPage()) {
            handleHasNextPage(null);
        } else {
            ProgressBarHelper.removeProgressBar(mListView);
            ErroBarHelper.addErroBar(mListView, getNoDataAlertContent(), R.drawable.errorbar_icon_nonetwork,
                    null, null);
        }
    }

    private void handleNetError() {
        ProgressBarHelper.removeProgressBar(mListView);
        ErroBarHelper.addErroBar(mListView, ErroBarHelper.ERRO_TYPE_NET_INTERNET,
                new Runnable() {
                    @Override
                    public void run() {
                        loadAllData(true);
                    }
                }, "重新加载");
    }

    /**
     * 服务端错误，请求成功，但返回失败
     */
    private void handleServerError() {
        ProgressBarHelper.removeProgressBar(mListView);
        ErroBarHelper.addErroBar(mListView, getInvalidResponseMessage(),
                new Runnable() {
                    @Override
                    public void run() {
                        loadAllData(true);
                    }
                }, "重新加载");
    }

    /**
     * 分页
     */
    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {

        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(mListView);
            if(state == LoadingFooter.State.Loading) {
                return;
            }

            if (mHasNextPage) {
                // loading more
                RecyclerViewStateUtils.setFooterViewState(getActivity(), mListView, mPageSize,
                        LoadingFooter.State.Loading, null);
                loadAllData(false);
            }
        }
    };

    /**
     * 处理列表数据
     *
     * @param allList
     */
    private void handleList(List<T> allList) {
        if (allList != null && !allList.isEmpty() && mAdapter != null) {
            // 如果是第一页数据则需要清空之前数据
            if (isTheFirstPage()) {
                mAdapter.clearData();
            }

            mAdapter.addItems(allList);
        } else {
            // 无数据返回，根据当前list是否有数据来判断是否显示错误页
            if (mNeedClearData || mAdapter == null || mAdapter.getItemCount() == 0) {
                mAdapter.clearData();
                handleDataEmpty();
            }
        }

        handleHasNextPage(allList);
    }

    /**
     * 是否有下一页
     *
     * @param mAllData
     */
    private void handleHasNextPage(List<T> mAllData) {
        // 条数小于一页则不可翻页
        mHasNextPage = !(mAllData == null || mAllData.size() < mPageSize);

        // 是否有页数限制
        if (mMaxPage > 0 && mHasNextPage) {
            mHasNextPage = (mPageIndex < mMaxPage);
        }

        if (mHasNextPage) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mListView, LoadingFooter.State.Normal);
        } else {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), mListView, LoadingFooter.State.TheEnd);
        }
    }

    private String getNoDataAlertContent() {
        return TextUtils.isEmpty(mNoDataAlertContent) ? ERROR_INFO_NO_DATA : mNoDataAlertContent;
    }

    private String getInvalidResponseMessage() {
        return TextUtils.isEmpty(mInvalidResponseMessage) ? ErroBarHelper.ERRO_TYPE_PARSE_NAME
                : mInvalidResponseMessage;
    }

    /**
     * 设置最大翻页限制
     * 比如，共有10页数据，但是只想看前5页的
     *
     * @param page
     */
    public void setMaxPage(int page) {
        mMaxPage = page;
    }

    public CommonListFragmentAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * 清空数据并刷新页面
     */
    public void refreshView() {
        loadAllData(true);
    }

    /** 当前是否为第一页数据 */
    public boolean isTheFirstPage() {
        return mPageIndex ==  DEFAULT_START_PAGE;
    }

    public void setNoDataAlertContent(String alert) {
        mNoDataAlertContent = alert;
    }

    public void setInvalidResponseMessage(String message) {
        mInvalidResponseMessage = message;
    }


    /** 创建对应的Adapter */
    public abstract CommonListFragmentAdapter getAdapter(Context context);

    /** 网络请求 */
    public abstract BaseRequestEntity getRequestEntity(int pageIndex);

    /** 根据server返回的json解析出list */
    public abstract List<T> parseList(String result);

    /** 每页数据数量 */
    public abstract int getPageSize();

    /** 是否需要翻页显示数据 */
    public abstract boolean needLoadByPage();
}