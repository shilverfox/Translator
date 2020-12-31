package com.jbsx.view.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.view.data.CloseVideoEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 通用web页，视频播放，新闻
 */
public class CommonWebFragment extends BaseFragment {
    private View mRootView;
    private WebView webView;

    /** 视频全屏播放用到 */
    private ViewGroup mViewFullScreen;
    private View mVideoView;
    private WebChromeClient.CustomViewCallback mCustomViewCallback;

    private String mRequestParams;
    private String mNaviType;
    private Integer mPageType;
    private String mNaviId;

    public CommonWebFragment() {
        // Required empty public constructor
    }

    public static CommonWebFragment newInstance(String naviId, String naviType, Integer pageType,
                                                String requestParams) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstData.INTENT_KEY_NAVI_ID, naviId);
        bundle.putString(AppConstData.INTENT_KEY_NAVI_TYPE, naviType);
        bundle.putInt(AppConstData.INTENT_KEY_PAGE_TYPE, pageType);
        bundle.putString(AppConstData.INTENT_KEY_REQUEST_PARAMS, requestParams);

        CommonWebFragment contentFragment = new CommonWebFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mNaviId = bundle.getString(AppConstData.INTENT_KEY_NAVI_ID);
            mNaviType = bundle.getString(AppConstData.INTENT_KEY_NAVI_TYPE);
            mPageType = bundle.getInt(AppConstData.INTENT_KEY_PAGE_TYPE);
            mRequestParams = bundle.getString(AppConstData.INTENT_KEY_REQUEST_PARAMS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.video_player_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();
        openUrl(mRequestParams);

        return mRootView;
    }

    public void createPresenter() {
        MyInfoUserCase userCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    private void initViews() {
        webView = mRootView.findViewById(R.id.web_video_player);
        mViewFullScreen = mRootView.findViewById(R.id.web_video_full_screen_player);
    }

    private void initEvents() {

    }

    private void openUrl(String url) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setUseWideViewPort(true);
        webView.loadUrl(url);
        ProgressBarHelper.addProgressBar(webView);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ProgressBarHelper.removeProgressBar(webView);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);

                mVideoView = view;
                mCustomViewCallback = callback;
                mViewFullScreen.addView(view);
                mViewFullScreen.setVisibility(View.VISIBLE);
                mVideoView.setVisibility(View.VISIBLE);
                webView.setVisibility(View.GONE);
                mViewFullScreen.bringToFront();
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();

                mVideoView.setVisibility(View.GONE);
                mViewFullScreen.removeView(mVideoView);
                mViewFullScreen.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
                mVideoView = null;
                try {
                    mCustomViewCallback.onCustomViewHidden();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CookieSyncManager.createInstance(getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.getSettings().setJavaScriptEnabled(false);
        webView.clearCache(true);
        webView.destroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CloseVideoEvent event) {
        if (event != null) {
            webView.destroy();
        }
    }
}
