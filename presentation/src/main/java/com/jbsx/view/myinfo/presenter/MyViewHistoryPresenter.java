package com.jbsx.view.myinfo.presenter;

import android.util.Log;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.utils.MessageTools;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.entity.BannerData;
import com.jbsx.view.myinfo.contact.MyViewHistoryContact;

public class MyViewHistoryPresenter implements MyViewHistoryContact.Presenter {
    private MyViewHistoryContact.View mView;
    private MyInfoUserCase mUserCase;

    public MyViewHistoryPresenter(MyViewHistoryContact.View view, MyInfoUserCase userCase) {
        mView = view;
        mUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestDelete(String[] ids) {
        mUserCase.requestDeleteVideo(LoginHelper.getInstance().getUserToken(), ids, mView.isHistory(),
                new BaseRequestCallback() {

            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleDeleteFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleDeleteSuccessful(data);
            }

            @Override
            public void onNetError() {

            }
        });
    }

    private void handleDeleteSuccessful(String data) {
        Log.v("", data);
//        BannerData bannerData = ParseUtil.parseData(data, BannerData.class);
//        if (mView != null) {
//            mView.refreshView(bannerData);
//        }
    }

    private void handleDeleteFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
//        if (mView != null) {
//            mView.drawEmptyBanner(MessageTools.getMessage(data));
//        }
    }
}
