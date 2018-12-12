package com.jbsx.view.myinfo.contact;

import com.jbsx.view.login.data.LoginData;

/**
 * 我的页面
 */
public class MyInfoContact {
    public interface View {
        void createPresenter();
        void updateUserInfo(LoginData userInfo);
    }

    public interface Presenter {
        void start();
        void requestUserInfo(String id);
    }
}
