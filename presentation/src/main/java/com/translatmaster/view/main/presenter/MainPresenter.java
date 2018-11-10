package com.translatmaster.view.main.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.translatmaster.view.main.contact.MainContact;

/**
 * Presenter
 *
 * Created by lijian15 on 2016/12/14.
 */

public class MainPresenter implements MainContact.Presenter {
    private final static String TAG = "MainPresenter";

    private MainContact.View mView;
    private MainViewUserCase mMainViewUserCase;

    public MainPresenter(MainContact.View view, MainViewUserCase userCase) {
        mView = view;
        mMainViewUserCase = userCase;
    }

    @Override
    public void start() {

    }

    private BaseRequestCallback mTranslateCallback = new BaseRequestCallback() {
        @Override
        public void onRequestSuccessful(String json) {
            handleResponseSuccessful(json);
        }

        @Override
        public void onRequestFailed(BaseDomainData baseDomainData) {
            handleResponseFailed(baseDomainData);
        }

        @Override
        public void onNetError() {

        }
    };

    /**
     * All the request should be handled by TaskManager.
     *
     * @param content string that need to be translated
     */
    @Override
    public void requestTranslate(final String content, final String src, final String dest) {
        if (mMainViewUserCase != null) {
//            mMainViewUserCase.setRequestCallback(mTranslateCallback);
            mMainViewUserCase.requestTranslate(content, src, dest, mTranslateCallback);
        }
    }

    private void handleResponseSuccessful(String resultContent) {
        if (mView != null) {
            mView.drawTranslatResult(resultContent + "");
        }
    }

    private void handleResponseFailed(BaseDomainData baseDomainData) {
        if (mView != null) {
            mView.drawTranslatResult(baseDomainData.getMsg());
        }
    }
}
