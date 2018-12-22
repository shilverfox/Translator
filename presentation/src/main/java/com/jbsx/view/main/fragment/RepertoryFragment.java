package com.jbsx.view.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.RepertoryUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.SortBar;
import com.jbsx.customview.recyclerview.CenterLayoutManager;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.view.main.adapter.CelebrityItemAdapter;
import com.jbsx.view.main.contact.RepertoryContact;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.presenter.RepertoryPresenter;
import com.jbsx.view.search.data.SearchConstData;
import com.jbsx.view.search.entity.SearchEvent;
import com.jbsx.view.search.util.SearchEventGenerator;
import com.jbsx.view.search.view.SearchResultView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 首页片库Tab
 */
public class RepertoryFragment extends BaseFragment implements RepertoryContact.View {
    private RepertoryContact.Presenter mPresenter;
    private View mRootView;
    private RecyclerView mRvCelebrities;
    private CelebrityItemAdapter mAdapterCelebrity;
    private List<Celebrities> mListCelebrity = new ArrayList<>();

    /** 片库列表 */
    private SearchResultView mRepertoryList;

    private SortBar mSortBar;

    private Drawable[] mSortDrawable;
    private List<String> mSortType = Arrays.asList(SearchConstData.SORT_BAR_HOT,
            SearchConstData.SORT_BAR_EPISODE_COUNT, SearchConstData.SORT_BAR_NAME);

    public RepertoryFragment() {
        // Required empty public constructor
    }

    public static RepertoryFragment newInstance() {
        return new RepertoryFragment();
    }

    @Override
    public void createPresenter() {
        RepertoryUserCase userCase = new RepertoryUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new RepertoryPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.repertory_fragment, null, false);
        createPresenter();
        initViews();
        requestCelebrities();

        return mRootView;
    }

    private void findView() {
        mRvCelebrities = mRootView.findViewById(R.id.rv_repertory_celebrities);
        mSortBar = mRootView.findViewById(R.id.sortView);
    }

    private void initViews() {
        findView();
        initCelebritiesView();
        initVideoListView();
        initSortBar();
    }

    private void initSortBar() {
        mSortDrawable = new Drawable[]{
                ContextCompat.getDrawable(mContext, R.mipmap.icon_sort_defalut),
                ContextCompat.getDrawable(mContext,R.mipmap.icon_sort_true),
                ContextCompat.getDrawable(mContext,R.mipmap.icon_sort_false)};
        mSortBar.setType(mSortType, mSortDrawable);
        mSortBar.setNotifyOutsideListener(new SortBar.notifyOutsideListener() {
            @Override
            public void notifySort(String tag, int desOrasc) {
                handleSortBarClick(tag, desOrasc);
            }
        });
    }

    /**
     * 排序点击事件
     *
     * @param tag
     * @param desOrAss
     */
    private void handleSortBarClick(String tag, int desOrAss) {
        mRepertoryList.setSearchData(SearchEventGenerator.getCelebrityIdSearch(
                ConstData.INVALID_CELEBRITY_ID, getSortType(tag, desOrAss)));
        mRepertoryList.clearAndFresh();
    }

    /**
     * 根据点击的tag匹配排序类型
     *
     * @param tag
     * @param desOrAss
     * @return
     */
    private int getSortType(String tag, int desOrAss) {
        if (SearchConstData.SORT_BAR_HOT.equals(tag)) {
            return mSortBar.isDescend(desOrAss) ? SearchEvent.SEARCH_SORT_BY_NOT_HOT
                    : SearchEvent.SEARCH_SORT_BY_HOT;
        } else if (SearchConstData.SORT_BAR_EPISODE_COUNT.equals(tag)) {
            return mSortBar.isDescend(desOrAss) ? SearchEvent.SEARCH_SORT_BY_COUNT_DESCENT
                    : SearchEvent.SEARCH_SORT_BY_COUNT_INCREASE;
        } else if (SearchConstData.SORT_BAR_NAME.equals(tag)) {
            return mSortBar.isDescend(desOrAss) ? SearchEvent.SEARCH_SORT_BY_NAME_DESCENT
                    : SearchEvent.SEARCH_SORT_BY_NAME_INCREASE;
        } else {
            // 默认按热度
            return SearchEvent.SEARCH_SORT_BY_HOT;
        }
    }

    private void initVideoListView() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mRepertoryList = SearchResultView.newInstance(
                SearchEventGenerator.getCelebrityIdSearch(ConstData.INVALID_CELEBRITY_ID,
                        SearchEvent.SEARCH_SORT_BY_HOT));
        transaction.add(R.id.rv_repertory_video_list, mRepertoryList);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取名家列表
     */
    private void requestCelebrities() {
        ProgressBarHelper.addProgressBar(mRvCelebrities);
        mPresenter.requestCelebrities();
    }

    private void initCelebritiesView() {
        CenterLayoutManager layoutManager = new CenterLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        mRvCelebrities.setLayoutManager(layoutManager);

        mAdapterCelebrity = new CelebrityItemAdapter(mContext, R.layout.celebrity_item);
        mAdapterCelebrity.setDatas(mListCelebrity);
        mAdapterCelebrity.setOnMyItemClickListener(new CelebrityItemAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                // 点击item的自动居中
                mRvCelebrities.smoothScrollToPosition(position);
                handleCelebritySelect(position);
            }
        });

        mRvCelebrities.setAdapter(mAdapterCelebrity);
    }

    /**
     * 选择名家后，查询这个名家的所有视频
     *
     * @param position
     */
    private void handleCelebritySelect(int position) {
        if (mListCelebrity != null && mListCelebrity.size() > 0
                && position >= 0 && position < mListCelebrity.size()) {
            Celebrities celebrity = mListCelebrity.get(position);

            if (celebrity != null) {
                mRepertoryList.setSearchData(SearchEventGenerator.getCelebrityIdSearch(
                        celebrity.getId(), SearchEvent.SEARCH_SORT_BY_HOT));
                mRepertoryList.clearAndFresh();
            }
        }
    }

    @Override
    public void drawCelebrities(CelebrityData celebrityData) {
        if (celebrityData != null && celebrityData.getPayload() != null) {
            List<Celebrities> data = celebrityData.getPayload().getCelebrities();
            if (data != null && !data.isEmpty()) {
                // 添加一个查看全部的item
                addViewAllCelebrityItem(data);
                mAdapterCelebrity.addList(data);
                mListCelebrity = mAdapterCelebrity.getDatas();

                // 模拟点击第一个item
                handleCelebritySelect(0);
            }
        }

        ProgressBarHelper.removeProgressBar(mRvCelebrities);
    }

    private void addViewAllCelebrityItem(List<Celebrities> data) {
        Celebrities celebrities = new Celebrities();
        celebrities.setId(ConstData.INVALID_CELEBRITY_ID);
        celebrities.setName("查看全部");
        data.add(0, celebrities);
    }

    /**
     * 获取数据失败
     */
    @Override
    public void drawGetCelebritiesError(String errorMessage) {
        ErroBarHelper.addErroBar(mRvCelebrities, errorMessage, new Runnable() {
            @Override
            public void run() {
                requestCelebrities();
            }
        });
    }
}
