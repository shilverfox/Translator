package com.jbsx.view.login.view.contact;

import com.jbsx.view.login.data.LoginData;

/**
 * Created by lijian15 on 2016/11/9.
 */

public class LoginThirdPartyContract {
    public interface View {
        void createPresenter();

        /**
         * Fragment是否还活着
         */
        boolean isFragmentAlive();
        void removeProgress();
        void addProgress();

        void whereToGo(LoginData loginData);
    }

    public interface Presenter {
        void loginByWechatWithCode(String code);
    }
}
