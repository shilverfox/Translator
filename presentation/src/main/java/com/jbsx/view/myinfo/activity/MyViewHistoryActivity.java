package com.jbsx.view.myinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.view.myinfo.fragment.MyFavoriteFragment;
import com.jbsx.view.myinfo.fragment.MyViewHistoryFragment;

/**
 * 观看历史
 */
public class MyViewHistoryActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_title_bar_activity);

        findViews();
        initTitleBar();
        registerEvents();
        replaceFragment(getFragment());
    }

    public Fragment getFragment() {
        return MyViewHistoryFragment.newInstance();
    }

    private void findViews() {

    }

    private void initTitleBar() {

    }

    private void registerEvents() {

    }

    private void onBackEvent() {
        finish();
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_no_title_fragment_container, fragment).commitAllowingStateLoss();
        }
    }
}
