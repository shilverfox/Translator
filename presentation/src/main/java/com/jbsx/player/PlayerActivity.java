package com.jbsx.player;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;

/**
 * Created by lijian on 2018/11/19.
 */

public class PlayerActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_title_bar_activity);

        replaceFragment(PlayerFragment.newInstance(null));
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_no_title_fragment_container, fragment).commitAllowingStateLoss();
        }
    }
}
