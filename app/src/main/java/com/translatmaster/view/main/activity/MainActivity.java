package com.translatmaster.view.main.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.translatmaster.R;
import com.translatmaster.app.BaseFragmentActivity;
import com.translatmaster.view.main.fragment.MainFragment;

public class MainActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createFragment();
    }

    /**
     * Attach a fragment
     */
    private void createFragment() {
        FragmentManager fm = getSupportFragmentManager();
        MainFragment fragment = MainFragment.newInstance();
        fm.beginTransaction().add(R.id.layout_root, fragment).commitAllowingStateLoss();
    }
}
