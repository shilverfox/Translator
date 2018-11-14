package com.jbsx.view.login.view.fragment.html;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.customview.TitleBar;
import com.jbsx.view.login.data.HtmlModal;
import com.jbsx.view.login.util.LoginUtils;

/**
 * 需要显示h5的Fragment，父类
 *
 * Created by lijian15 on 2016/11/15.
 */

public abstract class LoginHtmlBaseFragment extends BaseFragment {
    private View mRootView;

    /** Title Bar */
    private TitleBar mTopBarLayout;
    private WebView mWebView;

    /** Html封装的数据 */
    public HtmlModal mHtmlModal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.login_html_base_fragment, null, false);

        findViews(mRootView);
        initDatas();
        initViews();
        registEvents();

        return mRootView;
    }

    private void findViews(View view) {
        if (view == null) {
            return;
        }

        mTopBarLayout = (TitleBar) view.findViewById(R.id.layout_title_bar_container);
        mWebView = (WebView) view.findViewById(R.id.web_html_base);
    }

    private void initViews() {
        mTopBarLayout.setCenterTitle(getTitleBarTitle());
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.showCloseButton(true);
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

        String url = null;
        if (mHtmlModal != null) {
            url = mHtmlModal.getOpenUrl();

            if (!TextUtils.isEmpty(url)) {
                mWebView.loadUrl(url);
                mWebView.setWebViewClient(new UserAgreementViewClient());
            }
        }
    }

    /**
     * Web视图
     */
    private class UserAgreementViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // webView事件
            handleWebViewUrlEvent(view, url);
            return true;
        }
    }

    private void registEvents() {
        mTopBarLayout.setBackButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleBackEvent();
            }
        });

        mTopBarLayout.setCloseButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });
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

    /**
     * 为Html Fragment设置数据
     * @param modal
     */
    public void setHtmlData(HtmlModal modal) {
        mHtmlModal = modal;
    }

    /**
     * 处理回退事件，Activity调用
     */
    public void handleBackKeyEvent() {
        handleBackEvent();
    }


    /** 设置title bar 标题 */
    public abstract String getTitleBarTitle();

    /** 初始化数据 */
    public abstract void initDatas();

    /** url事件响应 */
    public abstract void handleWebViewUrlEvent(WebView view, String url);
}
