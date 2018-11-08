package com.translatmaster.view.login.view.fragment.html;

import android.webkit.WebView;

import com.translatmaster.view.login.data.LoginConstData;
import com.translatmaster.view.login.util.LoginUtils;

/**
 * 微信登录 风控
 * Created by lijian15 on 2016/11/15.
 */

public class LoginHtmlWechatVerifyFragment extends LoginHtmlBaseFragment {
    public LoginHtmlWechatVerifyFragment() {
    }

    public static LoginHtmlWechatVerifyFragment getInstance() {
        return new LoginHtmlWechatVerifyFragment();
    }

    @Override
    public String getTitleBarTitle() {
        return "账号验证";
    }

    @Override
    public void handleWebViewUrlEvent(WebView view, String url) {
        if (mHtmlModal != null) {
//            LoginUtils.handleWechatH5Result(mContext, view, url, mHtmlModal.getReturnUrl(),
//                    LoginConstData.TYPE_HTML_REQUEST_WECHAT_ACCOUNT_VERIFY, eventBus);
        }
    }

    @Override
    public void initDatas() {

    }
}
