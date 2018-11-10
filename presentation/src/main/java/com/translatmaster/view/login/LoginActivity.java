package com.translatmaster.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.translatmaster.R;
import com.translatmaster.app.BaseEvent;
import com.translatmaster.app.BaseFragmentActivity;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.customview.TitleBar;
import com.translatmaster.customview.TabGroupView;
import com.translatmaster.data.ITransKey;
import com.translatmaster.utils.Router;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.view.login.callback.ILoginResultListener;
import com.translatmaster.view.login.data.LoginResultEvent;
import com.translatmaster.view.login.util.LoginUtils;
import com.translatmaster.view.login.view.fragment.LoginByUserFragment;
import com.translatmaster.view.main.activity.MainActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Vector;

/**
 * 登录主界面
 */
public class LoginActivity extends BaseFragmentActivity implements ILoginResultListener {
    private LinearLayout mLayoutRoot;
    private TitleBar mTopBarLayout;
    private TabGroupView mTabGroupView;

    /**
     * 注册新用户
     */
//    private TextView mTxtRegister;

    /**
     * 是否需要回首页
     */
    private boolean mGoHome = true;

    /**
     * back是否需要回首页
     */
    private boolean mGoHomeForBack = false;

    /**
     * 是否是强制退出弹出的登录框
     */
    private boolean mIsForce = false;

    /**
     * 强制退出时弹出的提示消息
     */
    private String mMsg = "";

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

        // 是强制退出登录
        if (mIsForce) {
            if (!TextUtils.isEmpty(mMsg)) {
                ShowTools.toast(mMsg);
            }
        }
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
            mGoHome = this.getIntent().getBooleanExtra(ITransKey.KEY1, true);
            mGoHomeForBack = this.getIntent().getBooleanExtra("mGoHomeForBack", false);
            mIsForce = this.getIntent().getBooleanExtra("isForce", false);
            mMsg = this.getIntent().getStringExtra("msg");
        } else {
            mGoHome = true;
            mGoHomeForBack = false;
        }

//        LoginHelper.OnLoginListener onLoginListener = (LoginHelper.OnLoginListener) DataIntent.get(this.getIntent(), ITransKey.KEY);
//        list.add(onLoginListener);
    }

    private void findViews() {
        mLayoutRoot = (LinearLayout) findViewById(R.id.root);
        mTopBarLayout = (TitleBar) findViewById(R.id.layout_title_bar_container);
//        mTxtRegister = (TextView) findViewById(R.id.menu_text);
        mTabGroupView = (TabGroupView) this.findViewById(R.id.tabGroupView);
    }

    private void registEvents() {
//        mTxtRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 4.0去掉注册入口
////                Router.getsInstance().open(LoginRegisterActivity.class, LoginActivity.this);
//            }
//        });

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
        if (mFocusedFragment instanceof LoginByUserFragment
//                || mFocusedFragment instanceof LoginSimpleInputPhoneFragment
                ) {
            return false;
        }

        return true;
    }

    private void onBackEvent() {
        if (mGoHomeForBack) {
            Router.getInstance().open(MainActivity.class, this,
                    new Bundle(), Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else if (mIsForce) {
            Bundle bundle = new Bundle();
            bundle.putInt("selectpage", -1);
            Router.getInstance().open(MainActivity.class, LoginActivity.this, bundle,
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            removeTabFragment();
            finish();
            overridePendingTransition(0, R.anim.login_activity_exit);
        }
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
                LoginUtils.hideSoftInputMethod(mContext, mLayoutRoot);
                finish();
                break;
            default:
                break;
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoginUtils.hideSoftInputMethod(mContext, mLayoutRoot);
    }

    public View getRootView() {
        return mLayoutRoot;
    }
}
