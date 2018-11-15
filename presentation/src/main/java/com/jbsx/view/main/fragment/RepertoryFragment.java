package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.jbsx.customview.recyclerview.CenterLayoutManager;
import com.jbsx.view.main.adapter.CelebrityItemAdapter;
import com.jbsx.view.main.contact.RepertoryContact;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.presenter.RepertoryPresenter;
import com.jbsx.view.search.util.SearchEventGenerator;
import com.jbsx.view.search.view.SearchResultView;

import java.util.ArrayList;
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
    }

    private void initViews() {
        findView();
        initCelebritiesView();
        initVideoListView();
    }

    private void initVideoListView() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mRepertoryList = SearchResultView.newInstance(
                SearchEventGenerator.getCelebrityIdSearch(ConstData.INVALID_CELEBRITY_ID));
        transaction.add(R.id.rv_repertory_video_list, mRepertoryList);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取名家列表
     */
    private void requestCelebrities() {
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
                mRepertoryList.setSearchData(SearchEventGenerator.getCelebrityIdSearch(celebrity.getId()));
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
    }

    private void addViewAllCelebrityItem(List<Celebrities> data) {
        Celebrities celebrities = new Celebrities();
        celebrities.setId(ConstData.INVALID_CELEBRITY_ID);
        celebrities.setName("查看全部");
        data.add(0, celebrities);
    }
}
