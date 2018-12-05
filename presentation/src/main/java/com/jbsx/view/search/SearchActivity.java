package com.jbsx.view.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.Router;
import com.jbsx.view.myinfo.fragment.MyViewHistoryFragment;
import com.jbsx.view.search.fragment.SearchFragment;
import com.jbsx.view.search.util.SearchHelper;

public class SearchActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;
    private SearchHelper mSearchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        mSearchHelper = new SearchHelper();
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

        // 输入法显示搜索
        mTopBarLayout.getLeftInput().setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mTopBarLayout.getLeftInput().setInputType(InputType.TYPE_CLASS_TEXT);

        mTopBarLayout.setLeftInput("搜索", R.drawable.search_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        String searchKey = mTopBarLayout.getLeftInput().getText().toString();
        if (!TextUtils.isEmpty(searchKey)) {
            mSearchHelper.doSearch(searchKey);

            // 跳转到搜索结果页
            Router.getInstance().open(SearchResultActivity.class, SearchActivity.this);
        }
    }

    private void registerEvents() {
        // 响应软键盘搜索按键事件
        mTopBarLayout.getLeftInput().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    handleSearchClick();
                    return true;
                }
                return false;
            }
        });
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
