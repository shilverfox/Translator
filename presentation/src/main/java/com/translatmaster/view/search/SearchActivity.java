package com.translatmaster.view.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.translatmaster.R;
import com.translatmaster.app.BaseFragmentActivity;
import com.translatmaster.customview.TitleBar;
import com.translatmaster.view.myinfo.fragment.MyViewHistoryFragment;

public class SearchActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_title_bar_activity);

        findViews();
        initTitleBar();
        registerEvents();
        replaceFragment(MyViewHistoryFragment.newInstance());
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

    public void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_no_title_fragment_container, fragment).commitAllowingStateLoss();
        }
    }
}
