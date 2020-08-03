package com.jbsx.view.myinfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.Router;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.fragment.CommentDetailFragment;
import com.jbsx.view.myinfo.fragment.MyCommentFragment;
import com.jbsx.view.myinfo.view.detail.CommentDetailListView;

/**
 * 评论详细页面
 */
public class CommentDetailActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;
    private UserComments mUserComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_title_bar_activity);

        getDataFromIntent();
        findViews();
        initTitleBar();
        registerEvents();
        replaceFragment(CommentDetailFragment.newInstance(mUserComments));
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mUserComments = intent.getParcelableExtra(Router.COMMENT_DETAIL_KEY);
        }
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setCenterTitle("评论详情");
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
