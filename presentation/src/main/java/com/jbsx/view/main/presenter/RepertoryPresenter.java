package com.jbsx.view.main.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.interactor.RepertoryUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.view.main.contact.RepertoryContact;
import com.jbsx.view.main.entity.CelebrityData;

/**
 * 片库Tab
 */

public class RepertoryPresenter implements RepertoryContact.Presenter {
    private final static String TAG = "RepertoryPresenter";

    private RepertoryContact.View mView;
    private RepertoryUserCase mUserCase;

    public RepertoryPresenter(RepertoryContact.View view, RepertoryUserCase userCase) {
        mView = view;
        mUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestCelebrities() {
        if (mUserCase != null) {
            mUserCase.requestCelebrities(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadCelebritiesFailed(ErroBarHelper.ERRO_TYPE_PARSE_NAME);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadCelebritiesSuccessful(data);
                }

                @Override
                public void onNetError() {
                    handleLoadCelebritiesFailed(ErroBarHelper.ERRO_TYPE_NET_INTERNET);
                }
            });
        }
    }

    private void handleLoadCelebritiesSuccessful(String data) {
        CelebrityData parseData = ParseUtil.parseData(data, CelebrityData.class);
        if (mView != null) {
            mView.drawCelebrities(parseData);
        }
    }

    private void handleLoadCelebritiesFailed(String errorMessage) {
        if (mView != null) {
            mView.drawGetCelebritiesError(errorMessage);
        }
    }
}
