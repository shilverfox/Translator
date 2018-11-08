//package com.translatmaster.view.login.view.presenter;
//
//import com.google.gson.Gson;
//import com.translatmaster.view.login.data.LoginBindingPhoneData;
//import com.translatmaster.view.login.loginsdk.callback.OnCommonCallbackApp;
//import com.translatmaster.view.login.loginsdk.model.AppErrorResult;
//import com.translatmaster.view.login.loginsdk.model.AppFailResult;
//import com.translatmaster.view.login.util.LoginUtils;
//import com.translatmaster.view.login.view.contact.LoginSetPasswordContract;
//
//import java.util.List;
//
//import base.net.open.CookieListener;
//import base.net.open.JDErrorListener;
//import base.net.open.JDListener;
//import base.net.open.JDStringRequest;
//import base.net.open.RequestEntity;
//import jd.LoginHelper;
//import jd.loginsdk.LoginSdkHelper;
//import jd.loginsdk.callback.OnCommonCallbackApp;
//import jd.loginsdk.model.AppErrorResult;
//import jd.loginsdk.model.AppFailResult;
//import jd.net.PDJRequestManager;
//import jd.net.ServiceProtocol;
//import main.login.data.LoginBindingPhoneData;
//import main.login.util.LoginUtils;
//import main.login.view.contact.LoginSetPasswordContract;
//
///**
// * Created by lijian15 on 2016/11/3.
// */
//
//public class LoginSetPasswordPresenter implements LoginSetPasswordContract.Presenter {
//    private LoginSetPasswordContract.View mView;
//
//    public LoginSetPasswordPresenter(LoginSetPasswordContract.View view) {
//        mView = view;
//    }
//
//    @Override
//    public void start() {
//
//    }
//
//    /**
//     * 设置密码
//     */
//    @Override
//    public void handleSetPwsRequest(final String mobile, String password) {
//        LoginSdkHelper.setPasswordForPhoneNumLogin(mobile, password, new OnCommonCallbackApp() {
//            @Override
//            public void onSuccess() {
//                requestBindStatus(mobile);
//            }
//
//            @Override
//            public void onError(AppErrorResult errorResult) {
//                AppFailResult failResult = new AppFailResult();
//                failResult.setMessage(failResult.getMessage());
//                onFail(failResult);
//            }
//
//            @Override
//            public void onFail(AppFailResult failResult) {
//                if (mView != null) {
//                    mView.setSubmitBtnEnable(true);
//                }
//
//                if (failResult == null) {
//                    return;
//                }
//
//                if (mView != null) {
//                    mView.handleFailedReason(failResult.getMessage());
//                    mView.requestInputFocus();
//                    mView.hideInputMethod();
//                }
//            }
//        });
//    }
//
//    /**
//     * 查询账号绑定状态
//     *
//     * @param mobile
//     */
//    private void requestBindStatus(String mobile) {
//        final String xpin = LoginSdkHelper.getPin();
//        final String xa2 = LoginSdkHelper.getA2();
//
//        if (mView != null) {
//            mView.showProgressBar();
//        }
//
//        JDListener<String> listener = new JDListener<String>() {
//            @Override
//            public void onResponse(String string) {
//                Gson gson = new Gson();
//                LoginBindingPhoneData loginByPhoneData = null;
//
//                try {
//                    loginByPhoneData = gson.fromJson(string, LoginBindingPhoneData.class);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (loginByPhoneData == null) {
//                    if (mView != null) {
//                        mView.hideProgressBar();
//                        mView.setSubmitBtnEnable(true);
//                        mView.showDefaultErrorInfo();
//                    }
//                } else {
//                    if (!loginByPhoneData.getCode().equals("0") || loginByPhoneData.getResult() == null) {
//                        LoginUtils.showErrorMessage(loginByPhoneData.getMsg());
//
//                        if (mView != null) {
//                            mView.hideProgressBar();
//                            mView.setSubmitBtnEnable(true);
//                        }
//
//                        return;
//                    }
//
//                    if (loginByPhoneData.getResult().getFlg() == 2) {
//                        // 验证成功，跳转主界面
//                        if (mView != null) {
//                            mView.handleAccountBindSuccess(xpin, loginByPhoneData);
//                        }
//                    } else {
//                        LoginUtils.showErrorMessage(loginByPhoneData.getMsg());
//
//                        if (mView != null) {
//                            mView.hideProgressBar();
//                            mView.setSubmitBtnEnable(true);
//                        }
//                    }
//                }
//
//            }
//        };
//
//        JDErrorListener errorListener = new JDErrorListener() {
//            @Override
//            public void onErrorResponse(String error, int code) {
//                if (mView != null) {
//                    mView.hideProgressBar();
//                    mView.setSubmitBtnEnable(true);
//                    mView.showDefaultNetErrorInfo();
//                }
//            }
//        };
//
//        CookieListener<List<String>> cookieListener = new CookieListener<List<String>>() {
//            @Override
//            public void onResponse(String cookie, List<String> list) {
//                if (cookie != null) {
//                    LoginHelper.getInstance().setCookies(cookie);
//                }
//            }
//        };
//
//        RequestEntity requestEntity = ServiceProtocol.JDBindingByPhoneNum(xpin, xa2, mobile);
//        JDStringRequest stringRequest = new JDStringRequest(requestEntity, listener, errorListener,
//                cookieListener);
//        PDJRequestManager.addRequest(stringRequest, "");
//    }
//}
