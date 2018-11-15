package com.jbsx.view.myinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.view.myinfo.fragment.MyMessageFragment;

/**
 * 我的消息页面
 */
public class MyMessageActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title_bar_activity);

        findViews();
        initTitleBar();
        registerEvents();
        replaceFragment(MyMessageFragment.newInstance());
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setCenterTitle("我的消息");
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
            fragmentManager.beginTransaction().replace(R.id.layout_common_fragment_container, fragment).commitAllowingStateLoss();
        }
    }
}
