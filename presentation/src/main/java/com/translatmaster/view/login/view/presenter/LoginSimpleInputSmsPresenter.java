package com.translatmaster.view.login.view.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.translatmaster.utils.MD5Calculator;
import com.translatmaster.utils.MessageTools;
import com.translatmaster.view.login.view.contact.LoginSimpleInputSmsContract;

/**
 * Created by lijian15 on 2017/9/29.
 */

public class LoginSimpleInputSmsPresenter implements LoginSimpleInputSmsContract.Presenter {
    public LoginSimpleInputSmsContract.View mView;
    public LoginViewUserCase mLoginUserCase;

    public LoginSimpleInputSmsPresenter(LoginSimpleInputSmsContract.View view, LoginViewUserCase userCase) {
        mView = view;
        mLoginUserCase = userCase;
    }

    @Override
    public void handleGetSms(String mobile) {
        if (mView != null && mLoginUserCase != null) {
            mLoginUserCase.requestSmsCode(ConstData.REQUEST_SMS_TYPE_REGISTER, mobile, new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    mView.onGetSmsFailed(MessageTools.getMessage(data));
                }

                @Override
                public void onRequestSuccessful(String data) {
                    mView.onGetSmsSuccessful();
                }

                @Override
                public void onNetError() {

                }
            });
        }
    }

    @Override
    public void handleRegisterByPhone(String mobile, String password, String sms) {
        if (mLoginUserCase != null) {
            String pswd = MD5Calculator.calculateMD5(password);
            mLoginUserCase.register(mobile, pswd, sms, new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    mView.onRegisterByPhoneError(0, MessageTools.getMessage(data));
                }

                @Override
                public void onRequestSuccessful(String data) {
                    if (mView != null) {
                        mView.onRegisterByPhoneSuccessful();
                    }
                }

                @Override
                public void onNetError() {

                }
            });
        }
    }
}
