package com.jbsx.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.player.data.PlayerData;
import com.jbsx.player.fragment.PlayerFragment;
import com.jbsx.utils.Router;

/**
 * Created by lijian on 2018/11/19.
 */

public class PlayerActivity extends BaseFragmentActivity {
    private PlayerData mPlayerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_title_bar_activity);

        getDataFromIntent();
        replaceFragment(PlayerFragment.newInstance(mPlayerData));
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            mPlayerData = intent.getParcelableExtra(Router.PLAYER_REQUEST_KEY);
        }
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_no_title_fragment_container, fragment).commitAllowingStateLoss();
        }
    }
}
