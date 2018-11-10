package com.translatmaster.view.login.view.presenter;

import android.os.Message;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.translatmaster.utils.MD5Calculator;
import com.translatmaster.utils.MessageTools;
import com.translatmaster.view.login.data.LoginData;
import com.translatmaster.view.login.util.LoginUtils;
import com.translatmaster.view.login.view.contact.LoginByUserContract;

/**
 * Created by lijian15
 */

public class LoginByUserPresenter implements LoginByUserContract.Presenter {
    private final static String TAG = "LoginByJdPresenter";

    private LoginByUserContract.View mView;
    private LoginViewUserCase mLoginUserCase;

    public LoginByUserPresenter(LoginByUserContract.View view, LoginViewUserCase userCase) {
        mView = view;
        mLoginUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void login(String userName, String password) {
        if (mLoginUserCase == null) {
            return;
        }

        String pswd = MD5Calculator.calculateMD5(password);
        mLoginUserCase.loginByUser(userName, password, new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                if (mView != null) {
                    if (!mView.isFragmentAlive()) {
                        return;
                    }

                    if (MessageTools.hasErrorMessage(data)) {
                        MessageTools.showErrorMessage(data);
                    } else {
                        mView.showLoginFailedMessage();
                    }

                    mView.setButtonState();
                    mView.removeProgress();
                }
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
     * 登录成功处理逻辑
     */
    @Override
    public void handleLoginSuccess(String result) {
        if (mView != null && mView.isFragmentAlive()) {
            LoginData data = ParseUtil.parseData(result, LoginData.class);
            mView.whereToGo(data);
            mView.enableLoginButton();
            mView.removeProgress();
        }
    }
}
