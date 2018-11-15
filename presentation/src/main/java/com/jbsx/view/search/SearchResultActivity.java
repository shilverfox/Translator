package com.jbsx.view.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.Router;
import com.jbsx.view.search.callback.ISearchRequestListener;
import com.jbsx.view.search.entity.SearchEvent;
import com.jbsx.view.search.fragment.SearchFragment;
import com.jbsx.view.search.fragment.SearchResultFragment;
import com.jbsx.view.search.util.SearchHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 搜索结果页
 */
public class SearchResultActivity extends BaseFragmentActivity implements ISearchRequestListener {
    private TitleBar mTopBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        findViews();
        initTitleBar();
        registerEvents();
        replaceFragment(SearchResultFragment.newInstance());
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.getLeftInput().setFocusable(false);
        mTopBarLayout.setLeftInput("搜索", R.drawable.title_search_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchClick();
            }
        });
        mTopBarLayout.setRightButton("搜索", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearchClick();
            }
        });
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });
    }

    private void handleSearchClick() {
        // 回到搜索页
        Router.getInstance().open(SearchActivity.class, SearchResultActivity.this);
        finish();
    }

    private void registerEvents() {

    }

    private void onBackEvent() {
        finish();
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_search_fragment_container, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedSearchRequest(SearchEvent event) {
        if (event != null) {
            mTopBarLayout.getLeftInput().setText(event.getSearchKey());
        }
    }
}
