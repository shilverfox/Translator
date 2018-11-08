package com.translatmaster.view.login.view.contact;

import com.translatmaster.view.login.data.LoginData;
import com.translatmaster.view.login.loginsdk.model.AppFailResult;
import com.translatmaster.view.login.loginsdk.model.AppPicDataInfo;

/**
 * Created by lijian15 on 2016/11/2.
 */

public class LoginByUserContract {
    public interface View {
        void createPresenter();

        /** 初始化控件UI */
        void initViews();

        /**
         * 删除密码按键可见性及可操作性
         *
         * @param enable
         */
        void handleDelPsdUI(boolean enable);

        /**
         * 登录按钮状态判断
         */
        void setButtonState();

        void removeProgress();
        void addProgress();

        /**
         * Fragment是否还活着
         */
        boolean isFragmentAlive();

        void enableLoginButton();

        /**
         * 绑定手机号之后的跳转逻辑处理
         *
         * @param loginByPhoneData
         */
        void whereToGo(LoginData loginByPhoneData);

        /**
         * 显示登录失败
         */
        void showLoginFailedMessage();
    }

    public interface Presenter {
        void start();

        void login(String userName, String password);

        /**
         * 登录成功处理逻辑
         */
        void handleLoginSuccess(String result);
    }
}
