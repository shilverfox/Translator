package com.jbsx.view.login.view.contact;

import android.content.Context;

/**
 * Created by lijian15 on 2017/9/29.
 */

public class LoginSimpleInputSmsContract {
    public interface View {
        void onGetSmsSuccessful();
        void onGetSmsFailed(String errorMessage);

        void onRegisterByPhoneSuccessful();
        void onRegisterByPhoneError(int code, String errorMessage);
        String getSuccessToast();
        String getSubmitButtonName();
        boolean needShowUserAgreement();
        Context getViewContext();
    }

    public interface Presenter {
        void handleGetSms(String mobile);
        void handleRegisterByPhone(String mobile, String password, String sms);
    }
}
