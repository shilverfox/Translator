//package com.translatmaster.view.login.view.contact;
//
//import com.translatmaster.view.login.data.LoginData;
//
//import main.login.data.LoginData;
//
///**
// * Created by lijian15 on 2016/11/9.
// */
//
//public class LoginThirdPartyContract {
//    public interface View {
//        void createPresenter();
//
//        /**
//         * Fragment是否还活着
//         */
//        boolean isFragmentAlive();
//
//        void whereToGo(LoginData loginData);
//        void removeProgress();
//        void addProgress();
//    }
//
//    public interface Presenter {
//        /**
//         * 京东sdk实现微信登录
//         *
//         * @param code
//         */
//        void loginByWechatWithCode(String code);
//
//        /**
//         * 用第三方sdk返回的token调用sdk登录
//         */
//        void bindAccountByToken(String code);
//
//        boolean checkIsSupportJd();
//
//        void loginByJdApp();
//
//        /** 调用京东app获取token之后获取登录态 */
//        void loginWithToken(String token);
//    }
//}
