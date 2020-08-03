package com.jbsx.view.login.view.fragment.html;

import android.os.Bundle;

import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.Router;
import com.jbsx.view.login.LoginHtmlBaseActivity;
import com.jbsx.view.login.data.HtmlModal;
import com.jbsx.view.login.data.LoginConstData;
import com.jbsx.view.login.loginsdk.model.AppFailResult;
import com.jbsx.view.login.loginsdk.model.AppJumpResult;

/**
 * 控制页面跳转
 *
 * Created by lijian15 on 2016/11/15.
 */

public class LoginHtmlHelper {
    private static LoginHtmlHelper mInstance = new LoginHtmlHelper();

    private LoginHtmlHelper(){

    }

    public static LoginHtmlHelper getInstance() {
        return mInstance;
    }

    /**
     * 跳转到对应的html页面
     *
     * @param htmlModal
     */
    public void gotoLoginHtmlView(HtmlModal htmlModal) {
        if (htmlModal != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(LoginConstData.INTENT_HTML_MODEL, htmlModal);
            Router.getInstance().open(LoginHtmlBaseActivity.class,
                    MainApplicationLike.getAppContext(), bundle);
        }
    }

    /**
     * 根据来源类型判断跳转到的Fragment
     *
     * @param requestSrc
     * @return
     */
    public LoginHtmlBaseFragment getHtmlFragment(int requestSrc) {
        LoginHtmlBaseFragment fragment = null;

        switch (requestSrc) {
            case LoginConstData.TYPE_HTML_REQUEST_WECHAT_ACCOUNT_VERIFY:
                fragment = new LoginHtmlWechatVerifyFragment();
                break;
            default:
                return null;
        }

        return fragment;
    }

    private String wrapUrl(AppJumpResult jumpResult, int requestSrc) {
        String url = "";
//        if (jumpResult != null) {
//            url = String.format("%1$s?appid=%2$s&token=%3$s&" + getReturnUrl(jumpResult.getUrl(), requestSrc),
//                    jumpResult.getUrl(), LoginSdkHelper.getDwAppID(), jumpResult.getToken());
//        }

        return url;
    }

    private String getReturnUrl(String returnUrl, int requestSrc) {
        String returnUrlKey = null;
        String returnUrlValue = ("=" + LoginConstData.RETURN_URL_VALUE + "://myhost");

        if (LoginConstData.TYPE_HTML_REQUEST_JD_ACCOUNT_VERIFY == requestSrc) {
            returnUrlKey = LoginConstData.RETURN_URL_KEY_JD + returnUrlValue;
        } else if (LoginConstData.TYPE_HTML_REQUEST_WECHAT_ACCOUNT_VERIFY == requestSrc) {
            returnUrlKey = LoginConstData.RETURN_URL_KEY_JD + "=" + returnUrl;
        } else {
            returnUrlKey = LoginConstData.RETURN_URL_KEY_THIRD + returnUrlValue;
        }

        return returnUrlKey;
    }

    /**
     * 生成模型对象，以后直接扩展HtmlModal和该方法即可
     *
     * @param openUrlResult
     * @param requestSrc
     * @return
     */
    public HtmlModal wrapToHtmlData(AppFailResult openUrlResult, int requestSrc) {
        HtmlModal modal = new HtmlModal();

        if (openUrlResult != null && openUrlResult != null) {
            AppJumpResult jumpResult = openUrlResult.getJumpResult();

            if (jumpResult != null) {
                modal = new HtmlModal();
                modal.setCookieName(jumpResult.getToken());
                modal.setOpenUrl(wrapUrl(jumpResult, requestSrc));
                modal.setReturnUrl(getReturnUrl(jumpResult.getUrl(), requestSrc));
            }
        }

        // 页面来源
        modal.setRequestSource(requestSrc);

        return modal;
    }
}
