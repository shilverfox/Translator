//package com.translatmaster.view.login.view.contact;
//
//import com.translatmaster.view.login.data.LoginBindingPhoneData;
//
///**
// * Created by lijian15 on 2016/11/3.
// */
//
//public class LoginSetPasswordContract {
//    public interface View {
//        void createPresenter();
//
//        void setSubmitBtnEnable(boolean enable);
//
//        /** 返回错误码处理 */
//        void handleFailedReason(String errorMessage);
//
//        /** 输入框获取焦点 */
//        void requestInputFocus();
//
//        void hideInputMethod();
//
//        void hideProgressBar();
//        void showProgressBar();
//
//        /** 网络错误 */
//        void showDefaultNetErrorInfo();
//
//        void showDefaultErrorInfo();
//
//        /** 账号绑定状态验证成功 */
//        void handleAccountBindSuccess(String xpin, LoginBindingPhoneData bindData);
//    }
//
//    public interface Presenter {
//        void start();
//
//        /** 设置密码 */
//        void handleSetPwsRequest(String mobile, String password);
//    }
//}
