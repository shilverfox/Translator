package com.jbsx.view.myinfo;

import android.support.v4.app.Fragment;

import com.jbsx.view.myinfo.fragment.MyFavoriteFragment;

/**
 * 我的收藏
 */
public class MyFavoriteActivity extends MyViewHistoryActivity {
    @Override
    public Fragment getFragment() {
        return MyFavoriteFragment.newInstance();
    }
}
