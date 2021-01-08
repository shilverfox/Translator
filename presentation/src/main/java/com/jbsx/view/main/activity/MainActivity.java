package com.jbsx.view.main.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpHeaderManager;
import com.app.domain.net.event.BadSessionEvent;
import com.app.domain.net.interactor.MainViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.flyco.tablayout.SlidingCommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.DeviceUtil;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.LogTools;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ReloadBarHelper;
import com.jbsx.utils.Router;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.image.IImageLoadListener;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.data.BackKeyEvent;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.login.callback.ILoginResultListener;
import com.jbsx.view.login.callback.IOnLoginListener;
import com.jbsx.view.login.data.LoginResultEvent;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.PageManager;
import com.jbsx.view.main.entity.NavigationData;
import com.jbsx.view.main.entity.TabEntity;
import com.jbsx.view.main.fragment.GalleryFragment;
import com.jbsx.view.main.fragment.LocalResourceFragment;
import com.jbsx.view.main.fragment.MainPageFragment;
import com.jbsx.view.main.util.PageUtils;
import com.jbsx.view.main.util.WebViewGestureHelper;
import com.jbsx.view.main.view.SearchWindow;
import com.jbsx.view.myinfo.activity.MyViewHistoryActivity;
import com.jbsx.view.search.SearchActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity implements ILoginResultListener {

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private SlidingCommonTabLayout mTabLayout;
    private TitleBar mTopBarLayout;
    private ViewPager mViewPager;
    private View mBtnBack;
    private TextView mTvDeviceId;
    private ImageView mIvOrgLogo;
    private TextView mBtnLabel;
    private View mBtnSearch;
    private View mViewLoading;
    private ViewGroup mViewFullScreen;

    private long mExitTime = 0;

    private MainViewUserCase mMainPageUserCase;

    private PageManager mPageMager;
    private String mCurrentTabId;

    private Chronometer mTimerView;

    private SearchWindow mSearchWindow;

    private View mVideoControllerView;
    private WebViewGestureHelper mViewGestureHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        init();
        findViews();
        initEvent();
        initTitleBar();
//        handlePermissions();
        checkOrgState();
        initTimer();
        drawDeviceInfo();
//        showDeviceInfoDialog();
        mViewGestureHelper = new WebViewGestureHelper(mContext, mVideoControllerView);
        mViewGestureHelper.handleGesture(mViewFullScreen);
    }

    private void init() {
        mMainPageUserCase = new MainViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPageMager = new PageManager();
        mPageMager.setFragmentManager(getSupportFragmentManager());
        mSearchWindow = new SearchWindow();
        HttpHeaderManager.getInstance().setDeviceId(DeviceUtil.getAndroidId());
    }

    private void showDeviceInfoDialog() {
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setTitle("")
                .setMessage(DeviceUtil.getDeviceInfo(this))
                .show();
    }

    /**
     * 检查机构状态
     */
    private void checkOrgState() {
        if (mMainPageUserCase != null) {
            handleProgressBar(true);
            mMainPageUserCase.requestOrgState(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadOrgStateFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadOrgStateSuccessful(data);
                }

                @Override
                public void onNetError() {
                    handleOrgStateNetError();
                }
            });
        }
    }

    private void handleLoadOrgStateSuccessful(String data) {
        loadNavigation();
    }

    private void handleLoadOrgStateFailed(BaseDomainData data) {
        handleProgressBar(false);
        MessageTools.showErrorMessage(data);
        handleEmptyNaviData(data.getMsg());
        delayExit();
    }

    private void handleOrgStateNetError() {
        handleProgressBar(false);
        handleEmptyNaviData(ErroBarHelper.ERRO_TYPE_NET_INTERNET);
    }

    private void loadNavigation() {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.requestNavigation(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadNaviFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadNaviSuccessful(data);
                }

                @Override
                public void onNetError() {
                    handlePageNetError();
                }
            });
        }
    }

    private boolean isNavigationNotEmpty(NavigationData navData) {
        return (navData != null && navData.getBody() != null
                && navData.getBody().getClassifyList() != null
                && navData.getBody().getClassifyList().size() > 0);
    }

    private void handleLoadNaviSuccessful(String data) {
        handleProgressBar(false);
        NavigationData navData = ParseUtil.parseData(data, NavigationData.class);
        drawOrgLogo(navData);
        setHttpHeaderInfo(navData);
        if (isNavigationNotEmpty(navData)) {
            initMainTab(navData.getBody().getClassifyList());
            registEvents();
        } else {
            handleEmptyNaviData("导航数据为空");
        }
    }

    private void setHttpHeaderInfo(NavigationData navData) {
        if (navData != null && navData.getBody() != null) {
            HttpHeaderManager.getInstance().setDeviceCode(navData.getBody().getDeviceCode());
            HttpHeaderManager.getInstance().setOrgCode(navData.getBody().getOrgCode());
            HttpHeaderManager.getInstance().setOrgLogo(navData.getBody().getOrgLogo());
            HttpHeaderManager.getInstance().setOrgName(navData.getBody().getOrgName());
        }
    }

    private void handleLoadNaviFailed(BaseDomainData data) {
        handleProgressBar(false);
        MessageTools.showErrorMessage(data);
        handleEmptyNaviData(data.getMsg());
    }

    private void handlePageNetError() {
        handleProgressBar(false);
        handleEmptyNaviData(ErroBarHelper.ERRO_TYPE_NET_INTERNET);
    }

    private void handleEmptyNaviData(String errorMessage) {
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = ErroBarHelper.ERRO_TYPE_NET_NAME;
        }

        ReloadBarHelper.addReloadBar(mViewPager, errorMessage, new Runnable() {
            @Override
            public void run() {
                loadNavigation();
            }
        });
    }

    private void handleProgressBar(boolean show) {
        if (show) {
            ProgressBarHelper.addProgressBar(mViewLoading);
        } else {
            ProgressBarHelper.removeProgressBar(mViewLoading);
        }
    }

    private void findViews() {
        mTabLayout = findViewById(R.id.tab_main);
        mViewPager = findViewById(R.id.vp_container);
        mBtnBack = findViewById(R.id.btn_main_back);
        mTimerView = findViewById(R.id.chronometer);
        mTvDeviceId = findViewById(R.id.tv_device_info);
        mIvOrgLogo = findViewById(R.id.iv_org_logo);
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
        mBtnLabel = findViewById(R.id.btn_common_label);
        mBtnLabel.setText("检索");
        mBtnSearch = findViewById(R.id.btn_main_search);
        mViewLoading = findViewById(R.id.view_main_activity_loading);
        mViewFullScreen = findViewById(R.id.view_main_video_full_screen);
        mVideoControllerView = findViewById(R.id.digital_layout);
    }

    private void initEvent() {
        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSearchBtnClick();
            }
        });
    }

    private void handleSearchBtnClick() {
        mSearchWindow.showSearchDialog(mBtnSearch, mCurrentTabId, "");
    }

    private void drawDeviceInfo() {
        mTvDeviceId.setText("终端编号：" + HttpHeaderManager.getInstance().getDeviceCode());
    }

    /**
     * 机构logo
     */
    private void drawOrgLogo(NavigationData data) {
        ImageLoader.loadImage(data.getBody().getOrgLogo(), new IImageLoadListener() {
            @Override
            public void onLoadingComplete(Drawable drawable) {
                if (drawable != null) {
                    mIvOrgLogo.setImageDrawable(drawable);
                }
            }

            @Override
            public void onLoadingFailed(Drawable errorDrawable) {
                mIvOrgLogo.setImageResource(R.drawable.default_org_logo);
            }
        });
    }

    private void initTimer() {
        startTimer();
        mTimerView.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                // 每分钟发送一次心跳
                int interval = 1000*60;
                long timeDifference = SystemClock.elapsedRealtime() - chronometer.getBase();
                if ((timeDifference > interval) && (timeDifference % interval <= 1000)) {
                    sentHeartBeatInfo();
                }
            }
        });
    }

    /**
     * 发送心跳包
     */
    private void sentHeartBeatInfo() {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.sendHearBeatInfo(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                }

                @Override
                public void onRequestSuccessful(String data) {
                }

                @Override
                public void onNetError() {
                }
            });
        }
    }

    public void startTimer() {
        mTimerView.setBase(SystemClock.elapsedRealtime());
        mTimerView.start();
    }

    public void stopTimer() {
        mTimerView.stop();
    }

    /**
     * 动态权限申请
     */
    private void handlePermissions() {
        PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                ShowTools.toast("您拒绝了程序运行所需要的必要权限");

                // 没有权限就别用了
                delayExit();
            }
        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 退出app
     */
    private void delayExit() {
        MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleExit();
            }
        }, 10000);
    }

    private Fragment getFragmentByType(String naviId, int type, String params) {
        LogTools.e("MainActivity", type + "");
        switch (type) {
            case AppConstData.TYPE_NAVI_ALBUM:
                return GalleryFragment.newInstance(naviId, type + "", AppConstData.PAGE_TYPE_ALBUM_1,
                        params);
            case AppConstData.TYPE_NAVI_VIDEO:
                return GalleryFragment.newInstance(naviId, type + "", AppConstData.PAGE_TYPE_VIDEO_1, params);
            case AppConstData.TYPE_NAVI_LOCAL:
                return LocalResourceFragment.newInstance(naviId, type + "", AppConstData.PAGE_TYPE_LOCAL_1, params);
            case AppConstData.TYPE_NAVI_MAIN:
            default:
                return MainPageFragment.newInstance(naviId, type + "", AppConstData.PAGE_TYPE_MAIN, params);
        }
    }

    private Integer getPageTypeByTab(String tabType) {
        int type = PageUtils.parseTabType(tabType);

        switch (type) {
            case AppConstData.TYPE_NAVI_ALBUM:
                return AppConstData.PAGE_TYPE_ALBUM_1;
            case AppConstData.TYPE_NAVI_VIDEO:
                return AppConstData.PAGE_TYPE_VIDEO_1;
            case AppConstData.TYPE_NAVI_LOCAL:
                return AppConstData.PAGE_TYPE_LOCAL_1;
            default:
                return AppConstData.PAGE_TYPE_MAIN;
        }
    }

    private void initMainTab(List<NavigationData.ClassifyEntity> allNavigation) {
        for (NavigationData.ClassifyEntity entity : allNavigation) {
            if (entity != null && !TextUtils.isEmpty(entity.getClassifyName())) {
                mTabEntities.add(new TabEntity(entity.getClassifyName(), 0, 0));

                String navId = entity.getClassifyName();
                Fragment frameLayout = getFragmentByType(navId, entity.getClassifyType(),
                        entity.getClassifyCode());
                mFragmentList.add(frameLayout);

                String tabType = Integer.toString(entity.getClassifyType());
                mPageMager.addTab(navId);
                mPageMager.addPage(navId, getPageTypeByTab(tabType), frameLayout);
            }
        }




//        mFragmentList.add(MainPageFragment.newInstance());
//        mFragmentList.add(RepertoryFragment.newInstance());
//        mFragmentList.add(SpecialAlbumFragment.newInstance());
//        mFragmentList.add(MyInfoFragment.newInstance());
//
//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
//        }

        // 不支持滑动切换
        mTabLayout.setTabData(mTabEntities, this, R.id.fl_container, mFragmentList);
        mViewPager.setVisibility(View.GONE);

        int defaultTab = 0;
        mTabLayout.setCurrentTab(0);
        mCurrentTabId = mTabEntities.get(defaultTab).getTabTitle();
        findViewById(R.id.fl_container).setVisibility(View.VISIBLE);


        // 滑动切换
//        mTabLayout.setTabData(mTabEntities);
//        mViewPager.setOffscreenPageLimit(5);
//        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        mViewPager.setCurrentItem(0);
    }

    private void registEvents() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                mCurrentTabId = mTabEntities.get(position).getTabTitle();
                mPageMager.handleTabChange(mCurrentTabId);
                PageUtils.closeAudioAndVideo();
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

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageMager.handleBackEvent(mCurrentTabId);
                PageUtils.closeAudioAndVideo();
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

        mTopBarLayout.getRightButton().setTextSize(11);
        mTopBarLayout.setRightButton("观看历史", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginHelper.getInstance().isLogin()) {
                    handleGotoHistory();
                } else {
                    LoginHelper.getInstance().showLoginDialog(mContext, new IOnLoginListener() {
                        @Override
                        public void onSucess() {
                            handleGotoHistory();
                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                }
            }
        });
        mTopBarLayout.setRightButtonDrawables(R.drawable.view_history, -1, -1, -1);
    }

    private void handleGotoHistory() {
        Router.getInstance().open(MyViewHistoryActivity.class, MainActivity.this);
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
            return "";
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
            handleExit();
        }
    }

    private void handleExit() {
        finish();
        System.exit(0);
    }

    /**
     * 设置全屏模式
     */
    public void setFullScreenMode() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void exitFullScreenMode() {
        final WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public ViewGroup getViewFullScreen() {
        return mViewFullScreen;
    }

    /**
     * page 切换事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PageChangeEvent event) {
        if (mPageMager != null) {
            mPageMager.handlePageChange(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BackKeyEvent event) {
        if (mPageMager != null) {
            mPageMager.handleBackEvent(mCurrentTabId);
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
//                    mViewPager.setCurrentItem(0);
                }
            }, 500);
        }
    }

    /**
     * 接受domain层传递过来的登录失效event
     * 唤起登录
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BadSessionEvent event) {
        if (event != null) {
            LoginHelper.getInstance().logOut();
            LoginHelper.getInstance().startLogin(MainActivity.this, new IOnLoginListener() {
                @Override
                public void onSucess() {
                    ShowTools.toast("请重新尝试刷新页面");
                }

                @Override
                public void onFailed() {
                    ShowTools.toast("登录失败，请重试");
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
    }
}
