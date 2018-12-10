package com.jbsx.view.search.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.main.entity.TabEntity;
import com.jbsx.view.search.callback.ISearchRequestListener;
import com.jbsx.view.search.entity.SearchEvent;
import com.jbsx.view.search.view.SearchResultView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
 * 搜索结果页，包含title及结果列表
 */
public class SearchResultFragment extends BaseFragment implements ISearchRequestListener {
    private View mRootView;
    private CommonTabLayout mTabLayout;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private String[] mTitles = {"标题", "主讲人"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    public SearchResultFragment() {
        // Required empty public constructor
    }

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.search_result_fragment, null, false);

        initViews();
        initEvents();

        return mRootView;
    }

    private void initViews() {
        mTabLayout = mRootView.findViewById(R.id.tag_search_result);
    }

    private void initEvents() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                ((SearchResultView)mFragmentList.get(position)).clearAndFresh();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private SearchEvent copySearchEvent(SearchEvent event) {
        SearchEvent result = new SearchEvent(event.getCelebrityId(), event.getSearchKey(),
                event.getSearchType(), event.getSort());
        return result;
    }

    /**
     * 设置查找条件，by title
     *
     * @param event
     * @return
     */
    private SearchEvent makeSearchByTitle(SearchEvent event) {
        SearchEvent result = copySearchEvent(event);
        result.setSearchType(SearchEvent.SEARCH_TYPE_TITLE);
        return result;
    }

    /**
     * 设置查找条件，by 主讲
     *
     * @param event
     * @return
     */
    private SearchEvent makeSearchByCelebrity(SearchEvent event) {
        SearchEvent result = copySearchEvent(event);
        result.setSearchType(SearchEvent.SEARCH_TYPE_CELEBRITY);
        return result;
    }

    private void initMainTab(SearchEvent event) {
        mFragmentList.add(SearchResultView.newInstance(makeSearchByTitle(event)));
        mFragmentList.add(SearchResultView.newInstance(makeSearchByCelebrity(event)));

        mTitles[0] = event.getSearchKey();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        // 不支持滑动切换
        mTabLayout.setTabData(mTabEntities, getActivity(), R.id.layout_search_result, mFragmentList);
        mTabLayout.setCurrentTab(event.getDefaultFocus());
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedSearchRequest(SearchEvent event) {
        if (event != null) {
            initMainTab(event);
        } else {
            ErroBarHelper.addErroBar(mRootView, "啥也没有");
        }
    }
}
