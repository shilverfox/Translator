package com.jbsx.view.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.view.login.view.fragment.LoginResetPasswordFragment;
import com.jbsx.view.login.view.fragment.LoginSimpleInputSmsFragment;

public class ResetPasswordActivity extends BaseFragmentActivity {
    private LinearLayout mLayoutRoot;
    private TitleBar mTopBarLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_activity);

        findViews();
        registEvents();
        initTitleBar();
        replaceFragment();
    }

    private void initTitleBar() {
        mTopBarLayout.setCenterTitle("重置密码");
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });
    }

    private void findViews() {
        mLayoutRoot = (LinearLayout) findViewById(R.id.root);
        mTopBarLayout = (TitleBar) findViewById(R.id.layout_title_bar_container);
    }

    private void registEvents() {

    }

    private void onBackEvent() {
        finish();
    }

    public void replaceFragment() {
        Fragment fragment = new LoginResetPasswordFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_pane, fragment).commitAllowingStateLoss();
    }
}
