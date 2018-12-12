package com.jbsx.view.myinfo.presenter;

import android.util.Log;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.utils.MessageTools;
import com.jbsx.view.login.data.LoginData;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.contact.MyInfoContact;

public class MyInfoPresenter implements MyInfoContact.Presenter {
    private MyInfoContact.View mView;
    private MyInfoUserCase mUserCase;

    public MyInfoPresenter(MyInfoContact.View view, MyInfoUserCase userCase) {
        mView = view;
        mUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestUserInfo(String id) {
        mUserCase.requestUserInfo(LoginHelper.getInstance().getUserToken(), id, new BaseRequestCallback() {

            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleGetUserFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleGetUserSuccessful(data);
            }

            @Override
            public void onNetError() {

            }
        });
    }

    private void handleGetUserSuccessful(String data) {
        LoginData userData = ParseUtil.parseData(data, LoginData.class);
        if (mView != null) {
            mView.updateUserInfo(userData);
        }
    }

    private void handleGetUserFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }
}
