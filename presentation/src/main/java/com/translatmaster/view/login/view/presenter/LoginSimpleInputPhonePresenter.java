//package com.translatmaster.view.login.view.presenter;
//
//import com.app.domain.net.BaseRequestCallback;
//import com.app.domain.net.data.ConstData;
//import com.app.domain.net.interactor.LoginViewUserCase;
//import com.app.domain.net.model.BaseDomainData;
//import com.translatmaster.utils.MessageTools;
//import com.translatmaster.view.login.view.contact.LoginSimpleInputPhoneContract;
//
///**
// * Created by lijian15 on 2017/9/29.
// */
//
//public class LoginSimpleInputPhonePresenter implements LoginSimpleInputPhoneContract.Presenter {
//    private LoginSimpleInputPhoneContract.View mView;
//    private LoginViewUserCase mLoginUserCase;
//
//    public LoginSimpleInputPhonePresenter(LoginSimpleInputPhoneContract.View view, LoginViewUserCase userCase) {
//        mView = view;
//        mLoginUserCase = userCase;
//    }
//
//    @Override
//    public void handleGetSms(String mobile) {
//        if (mView != null && mLoginUserCase != null) {
//            mLoginUserCase.requestSmsCode(ConstData.REQUEST_SMS_TYPE_REGISTER, mobile, new BaseRequestCallback() {
//                @Override
//                public void onRequestFailed(BaseDomainData data) {
//                    mView.onGetSmsFailed(MessageTools.getMessage(data));
//                }
//
//                @Override
//                public void onRequestSuccessful(String data) {
//                    mView.onGetSmsSuccessful();
//                }
//            });
//        }
//    }
//}
