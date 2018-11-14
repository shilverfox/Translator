package com.translatmaster.view.myinfo;

import android.os.Bundle;
import android.view.View;

import com.translatmaster.R;
import com.translatmaster.app.BaseFragmentActivity;
import com.translatmaster.customview.TitleBar;

public class MyInfoActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViews();
        initTitleBar();
        registerEvents();
    }

    private void findViews() {
        mTopBarLayout = (TitleBar) findViewById(R.id.layout_title_bar_container);
        mTopBarLayout.showBackButton(true);
    }

    private void initTitleBar() {
        mTopBarLayout.setCenterTitle("首页");
    }

    private void registerEvents() {
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });
    }

    private void onBackEvent() {
        finish();
    }
}
