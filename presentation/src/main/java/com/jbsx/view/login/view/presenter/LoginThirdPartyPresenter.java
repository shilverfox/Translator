package com.jbsx.view.login.view.presenter;

import android.text.TextUtils;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.data.LoginData;
import com.jbsx.view.login.util.LoginUtils;
import com.jbsx.view.login.view.contact.LoginThirdPartyContract;

/**
 * Created by lijian15 on 2016/11/9.
 */

public class LoginThirdPartyPresenter implements LoginThirdPartyContract.Presenter {

    private final static String TAG = "LoginThirdPartyPresenter";

    private LoginThirdPartyContract.View mView;
    public LoginViewUserCase mLoginUserCase;

    public LoginThirdPartyPresenter(LoginThirdPartyContract.View view, LoginViewUserCase userCase) {
        mView = view;
        mLoginUserCase = userCase;
    }

    /**
     * 京东登录sdk实现微信登录
     *
     * @param code
     */
    @Override
    public void loginByWechatWithCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return;
        }

        mLoginUserCase.loginByWechat(code, new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                MessageTools.showErrorMessage(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleLoginSuccess(data);
            }

            @Override
            public void onNetError() {
            }
        });
    }

    /**
     * 登录sdk返回成功
     */
    private void handleLoginSuccess(String result) {
        if (mView != null && mView.isFragmentAlive()) {
            LoginData data = ParseUtil.parseData(result, LoginData.class);
            mView.whereToGo(data);
            clearWaitingStatusUi();
        }
    }

    private void clearWaitingStatusUi() {
        if (mView != null) {
            mView.removeProgress();
        }
    }
}
