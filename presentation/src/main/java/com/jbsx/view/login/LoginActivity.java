package com.jbsx.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.customview.TabGroupView;
import com.jbsx.data.ITransKey;
import com.jbsx.utils.DataIntent;
import com.jbsx.view.login.callback.ILoginResultListener;
import com.jbsx.view.login.callback.IOnLoginListener;
import com.jbsx.view.login.data.LoginResultEvent;
import com.jbsx.view.login.util.LoginUtils;
import com.jbsx.view.login.view.fragment.LoginByUserFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 登录主界面
 */
public class LoginActivity extends BaseFragmentActivity implements ILoginResultListener {
    private List<IOnLoginListener> mLoginListener = new ArrayList<>();

    private LinearLayout mLayoutRoot;
    private TitleBar mTopBarLayout;
    private TabGroupView mTabGroupView;

    private Fragment mFocusedFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        getDataFromIntent();
        findViews();
        registEvents();
        initViews();
        initTitleBar();
    }

    private void initTitleBar() {
        mTopBarLayout.getCenterTitle().setTextColor(0xffffffff);
        mTopBarLayout.setCenterTitle("登录");
        mTopBarLayout.setBackButton(R.drawable.back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });
    }

    private void getLoginEntrance() {
        if (!isFinishing()) {
            int defaultIndex = 0;
            Vector<Fragment> pageList = new Vector<Fragment>();
            Bundle args = new Bundle();

//            pageList.add(new LoginSimpleInputSmsFragment());
            pageList.add(LoginByUserFragment.newInstance());

            mTabGroupView.setFragemntContainerId(R.id.main_pane);
            mTabGroupView.setFragmentManager(getSupportFragmentManager());
            mTabGroupView.setPageList(pageList);
            mTabGroupView.setSelected(defaultIndex);
            mTabGroupView.setVisibility(View.VISIBLE);

            mFocusedFragment = pageList.get(defaultIndex);
            mFocusedFragment.setArguments(args);

            // 第三方登录
            addThirdPartyEntrance();
        }
    }

    private void initViews() {
        mTabGroupView.setVisibility(View.GONE);

        // 获取登录入口状态
        MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
            @Override
            public void run() {
                getLoginEntrance();
            }
        }, 200);
    }

    /**
     * 第三方登录入口
     */
    private void addThirdPartyEntrance() {
//        FragmentManager fm = getSupportFragmentManager();
//        LoginThirdPartyFragment fragment = LoginThirdPartyFragment.newInstance();
//        fm.beginTransaction().add(R.id.third_party_pane, fragment).commitAllowingStateLoss();
    }

    private void getDataFromIntent() {
        if (getIntent() != null) {
            IOnLoginListener onLoginListener = (IOnLoginListener) DataIntent.get(this.getIntent(), ITransKey.KEY);
            mLoginListener.add(onLoginListener);
        }
    }

    private void findViews() {
        mLayoutRoot = (LinearLayout) findViewById(R.id.root);
        mTopBarLayout = (TitleBar) findViewById(R.id.layout_title_bar_container);
        mTabGroupView = (TabGroupView) this.findViewById(R.id.tabGroupView);
    }

    private void registEvents() {
        // Tab切换
        mTabGroupView.setOnMyClickListener(new TabGroupView.MyOnClickListener() {
            @Override
            public boolean onClick(View v) {
                if (isFragmentChanged()) {
                    if (mTabGroupView.getSelectIndex() == 1) {
                        replaceFragment(new LoginByUserFragment());
                    } else if (mTabGroupView.getSelectIndex() == 0) {
//                        replaceFragment(new LoginSimpleInputPhoneFragment());
                    }
                } else {
                    // 记录当前Frgment
                    mFocusedFragment = mTabGroupView.getCurrentPage();
                }

                return false;
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = mTabGroupView.getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_pane, fragment).commitAllowingStateLoss();

            mFocusedFragment = fragment;
        }
    }

    private boolean isFragmentChanged() {
        if (mFocusedFragment instanceof LoginByUserFragment) {
            return false;
        }

        return true;
    }

    private void onBackEvent() {
        removeTabFragment();
        finish();
        overridePendingTransition(0, R.anim.login_activity_exit);
    }

    private void removeTabFragment() {
        if (mTabGroupView != null && mFocusedFragment != null) {
            FragmentManager fm = mTabGroupView.getFragmentManager();
            if (fm != null) {
                fm.beginTransaction().remove(mFocusedFragment).commitAllowingStateLoss();
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            onBackEvent();
        }

        return false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

//    // @Subscribe
//    public void onEvent(final LoginUser loginUser) {
//        LoginActivity.this.onSuccess(loginUser);
//    }

//    /**
//     * 处理登录成功逻辑
//     *
//     * @param loginUser
//     */
//    public void onSuccess(final LoginUser loginUser) {
//        LoginUtils.hideSoftInputMethod(mContext, mLayoutRoot);
//
//        LoginHelper.getsInstance().saveData();
//        LoginHelper.getsInstance().setLogin(true);
//
//        if (loginUser != null) {
//            if (mGoHome) {
//                Bundle bundle = new Bundle();
//                Router.getsInstance().open(MainActivity.class, this, new Bundle(),
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            } else if (mIsForce) {
//                // 是强制退出回根Tab
//                Bundle bundle = new Bundle();
//                bundle.putInt("selectpage", -1);
//                Router.getsInstance().open(MainActivity.class, LoginActivity.this, bundle,
//                        Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            }
//
//            eventBus.post(new LoginUpdate(true));
//
//            if (!isFinishing()) {
//                finish();
//            }
//        } else {
//            ShowTools.toast("登录失败，请稍后再试");
//        }
//    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginResultEvent event) {
        switch (event.action) {
            case FAIL:
                // this.finish();
                break;

            case SUCCESS:
                handleLoginCallBack();
                LoginUtils.hideSoftInputMethod(mContext, mLayoutRoot);
                finish();
                break;
            default:
                break;
        }
    };

    private void handleLoginCallBack() {
        if (mLoginListener != null) {
            for (int i = 0; i < mLoginListener.size(); i++) {
                mLoginListener.get(i).onSucess();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoginUtils.hideSoftInputMethod(mContext, mLayoutRoot);
    }

    public View getRootView() {
        return mLayoutRoot;
    }
}
