//package com.translatmaster.view.login.view.presenter;
//
//import android.text.TextUtils;
//
//import com.translatmaster.view.login.data.LoginConstData;
//import com.translatmaster.view.login.loginsdk.callback.OnCommonCallbackApp;
//import com.translatmaster.view.login.loginsdk.model.AppErrorResult;
//import com.translatmaster.view.login.loginsdk.model.AppFailResult;
//import com.translatmaster.view.login.loginsdk.model.AppReplyCode;
//import com.translatmaster.view.login.loginsdk.model.AppWXTokenInfo;
//import com.translatmaster.view.login.util.LoginUtils;
//import com.translatmaster.view.login.view.contact.LoginThirdPartyContract;
//
///**
// * Created by lijian15 on 2016/11/9.
// */
//
//public class LoginThirdPartyPresenter implements LoginThirdPartyContract.Presenter {
//
//    private final static String TAG = "LoginThirdPartyPresenter";
//
//    private LoginThirdPartyContract.View mView;
//
//    public LoginThirdPartyPresenter(LoginThirdPartyContract.View view) {
//        mView = view;
//    }
//
//    /**
//     * 京东登录sdk实现微信登录
//     *
//     * @param code
//     */
//    @Override
//    public void loginByWechatWithCode(String code) {
//        if (TextUtils.isEmpty(code)) {
//            return;
//        }
//
//        AppWXTokenInfo tokenInfo = new AppWXTokenInfo();
//        tokenInfo.setCode(code);
//        LoginSdkHelper.wxLogin(tokenInfo, new OnCommonCallbackApp() {
//            @Override
//            public void onSuccess() {
//                handleLoginSuccess();
//            }
//
//            @Override
//            public void onError(AppErrorResult errorResult) {
//                clearWaitingStatusUi();
//
//                if (errorResult != null) {
//                    LoginUtils.showErrorMessage(errorResult.getErrorMsg());
//                }
//            }
//
//            @Override
//            public void onFail(AppFailResult failResult) {
//                clearWaitingStatusUi();
//
//                if (failResult != null) {
//                    LoginUtils.showErrorMessage(failResult.getMessage());
//
//                     if (failResult.getReplyCode() >= AppReplyCode.reply0x80
//                            && failResult.getReplyCode() <= AppReplyCode.reply0x8f) {
//                        // 风控
//                        handleAccountVerify(failResult,
//                                LoginConstData.TYPE_HTML_REQUEST_WECHAT_ACCOUNT_VERIFY);
//                    }
//                }
//            }
//        });
//    }
//
//    /**
//     * 京东登录sdk实现微信登录，风控后调用
//     */
//    @Override
//    public void bindAccountByToken(String token) {
//        LoginSdkHelper.bindAccountLogin(token, new OnCommonCallbackApp() {
//            @Override
//            public void onSuccess() {
//                handleLoginSuccess();
//            }
//
//            @Override
//            public void onError(AppErrorResult errorResult) {
//                if (errorResult != null) {
//                    AppFailResult failResult = new AppFailResult();
//                    failResult.setMessage(errorResult.getErrorMsg());
//
//                    onFail(failResult);
//                }
//            }
//
//            @Override
//            public void onFail(AppFailResult failResult) {
//                clearWaitingStatusUi();
//
//                if (failResult != null) {
//                    LoginUtils.showErrorMessage(failResult.getMessage());
//                }
//            }
//        });
//    }
//
//    /**
//     * 登录sdk返回成功
//     */
//    private void handleLoginSuccess() {
//        if (mView != null && mView.isFragmentAlive()) {
//            bindAccount();
//        }
//    }
//
//    private void clearWaitingStatusUi() {
//        if (mView != null) {
//            mView.removeProgress();
//        }
//    }
//
//    /**
//     * 账号风控
//     *
//     * @param openUrlResult
//     * @param requestSrc 请求来源
//     */
//    private void handleAccountVerify(AppFailResult openUrlResult, int requestSrc) {
//        if (openUrlResult != null) {
//            clearWaitingStatusUi();
//            LoginUtils.handleVerifyAccount(openUrlResult, requestSrc);
//        }
//    }
//
//    /**
//     * 在这里通过pin和a2向后台获取用户是否是第一次登陆
//     */
//    private void bindAccount() {
//    }
//
//    @Override
//    public boolean checkIsSupportJd() {
//        return LoginSdkHelper.isJDAppInstalled()
//                && LoginSdkHelper.isJDAppSupportAPI();
//    }
//
//    @Override
//    public void loginByJdApp() {
//    }
//
//    @Override
//    public void loginWithToken(String token) {
//        LoginSdkHelper.loginWithToken(token, new OnCommonCallbackApp() {
//            @Override
//            public void onSuccess() {
//                handleLoginSuccess();
//            }
//
//            @Override
//            public void onError(AppErrorResult errorResult) {
//                if (errorResult != null) {
//                    AppFailResult failResult = new AppFailResult();
//                    failResult.setMessage(errorResult.getErrorMsg());
//                    onFail(failResult);
//                }
//            }
//
//            @Override
//            public void onFail(AppFailResult failResult) {
//                if (failResult != null) {
//                    clearWaitingStatusUi();
//                    LoginUtils.showErrorMessage(failResult.getMessage());
//                }
//            }
//        });
//    }
//}
