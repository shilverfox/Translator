package com.jbsx.view.login.view.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.jbsx.utils.MD5Calculator;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.smskey.ISmsKeyGeneratorListener;
import com.jbsx.utils.smskey.SmsKeyGenerator;
import com.jbsx.view.login.view.contact.LoginSimpleInputSmsContract;

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
    public void handleGetSms(final String mobile) {
        if (mView != null && mLoginUserCase != null) {
            SmsKeyGenerator.getKey(new ISmsKeyGeneratorListener() {
                @Override
                public void onShaComplete(String timeStamp, String randomInt, String resultKey) {
                    mLoginUserCase.requestSmsCode(ConstData.REQUEST_SMS_TYPE_REGISTER, mobile,
                            timeStamp, resultKey, randomInt, new BaseRequestCallback() {
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
            });
        }
    }

    @Override
    public void handleRegisterByPhone(String mobile, String password, String sms) {
        if (mLoginUserCase != null) {
//            String pswd = MD5Calculator.calculateMD5(password);
            mLoginUserCase.register(mobile, password, sms, new BaseRequestCallback() {
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
