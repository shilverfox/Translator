package com.jbsx.view.main.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.Router;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.callback.ILoginResultListener;
import com.jbsx.view.login.data.LoginResultEvent;
import com.jbsx.view.main.entity.TabEntity;
import com.jbsx.view.main.fragment.MainPageFragment;
import com.jbsx.view.main.fragment.RepertoryFragment;
import com.jbsx.view.myinfo.activity.MyViewHistoryActivity;
import com.jbsx.view.myinfo.fragment.MyInfoFragment;
import com.jbsx.view.search.SearchActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends BaseFragmentActivity implements ILoginResultListener {

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private String[] mTitles = {"精选", "片库", "我的"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private CommonTabLayout mTabLayout;
    private TitleBar mTopBarLayout;
    private ViewPager mViewPager;

    private long mExitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViews();
        initTitleBar();
        initMainTab();
        registEvents();
    }

    private void findViews() {
        mTabLayout = findViewById(R.id.tab_main);
        mTopBarLayout = (TitleBar) findViewById(R.id.layout_title_bar_container);
        mViewPager = findViewById(R.id.vp_container);
    }

    private void initMainTab() {
        mFragmentList.add(MainPageFragment.newInstance());
        mFragmentList.add(RepertoryFragment.newInstance());
        mFragmentList.add(MyInfoFragment.newInstance());

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        // 不支持滑动切换
//        mTabLayout.setTabData(mTabEntities, this, R.id.fl_container, mFragmentList);
//        mTabLayout.setCurrentTab(0);


        // 滑动切换
        mTabLayout.setTabData(mTabEntities);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
    }

    private void registEvents() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initTitleBar() {
        mTopBarLayout.setLeftImage(R.drawable.main_title_icon);
        mTopBarLayout.getLeftInput().setFocusable(false);
        mTopBarLayout.setLeftInput("搜索", R.drawable.search_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().open(SearchActivity.class, MainActivity.this);
            }
        });

        mTopBarLayout.setRightButton("观看历史", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().open(MyViewHistoryActivity.class, MainActivity.this);
            }
        });
        mTopBarLayout.setRightButtonDrawables(R.drawable.view_history, -1, -1, -1);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ShowTools.toast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginResultEvent event) {
        if (event != null && event.action == LoginResultEvent.LoginAction.SUCCESS) {
            // 登录成功，切换到第一个tab
            MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mViewPager.setCurrentItem(0);
                }
            }, 500);
        }
    }
}
