package com.jbsx.view.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.view.myinfo.fragment.MyViewHistoryFragment;
import com.jbsx.view.search.fragment.SearchFragment;

public class SearchActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        findViews();
        initTitleBar();
        registerEvents();
        replaceFragment(SearchFragment.newInstance());
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setLeftInput("搜索", R.drawable.title_search_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTopBarLayout.setRightButton("搜索", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });
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
}
