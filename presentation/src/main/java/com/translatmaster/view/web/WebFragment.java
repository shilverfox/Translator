package com.translatmaster.view.web;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.customview.TitleBar;
import com.translatmaster.view.login.data.HtmlModal;
import com.translatmaster.view.login.util.LoginUtils;

public class WebFragment extends BaseFragment {
    private View mRootView;
    private WebView mWebView;
    private String mUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.web_fragment, null, false);

        findViews(mRootView);
        initViews();
        registEvents();

        return mRootView;
    }

    public WebFragment() {
        // Required empty public constructor
    }

    public static WebFragment newInstance() {
        return new WebFragment();
    }

    private void findViews(View view) {
        if (view == null) {
            return;
        }
        mWebView = (WebView) view.findViewById(R.id.web_html_base);
    }

    private void initViews() {
        initWebView();
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        mWebView.removeJavascriptInterface("accessibility");
        mWebView.removeJavascriptInterface("accessibilityTraversal");
        webSettings.setSavePassword(false);
        webSettings.setAllowFileAccess(false);
        if (Build.VERSION.SDK_INT >= 16) {
            // 16以上版本处理
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(false);
        }

        if (!TextUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
            mWebView.setWebViewClient(new UserAgreementViewClient());
        }
    }

    /**
     * Web视图
     */
    private class UserAgreementViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void registEvents() {
    }

    /**
     * 回退事件
     */
    private void handleBackEvent() {
        LoginUtils.backWebView(getActivity(), mWebView);
    }

    /**
     * 关闭对应的Activity
     */
    private void closeActivity() {
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    public void setHtmlData(String url) {
        mUrl = url;
    }

    /**
     * 处理回退事件，Activity调用
     */
    public void handleBackKeyEvent() {
        handleBackEvent();
    }
}
