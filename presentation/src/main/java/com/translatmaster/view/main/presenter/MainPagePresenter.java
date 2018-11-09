package com.translatmaster.view.main.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.interactor.MainViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.translatmaster.view.main.contact.MainContact;
import com.translatmaster.view.main.contact.MainPageContact;
import com.translatmaster.view.main.entity.BannerData;
import com.youth.banner.Banner;

/**
 * 首页
 */

public class MainPagePresenter implements MainPageContact.Presenter {
    private final static String TAG = "MainPagePresenter";

    private MainPageContact.View mView;
    private MainPageUserCase mMainPageUserCase;

    public MainPagePresenter(MainPageContact.View view, MainPageUserCase userCase) {
        mView = view;
        mMainPageUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestBannerInfo() {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.requestBannerInfo(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadBannerFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadBannerSuccessful(data);
                }
            });
        }
    }

    private void handleLoadBannerSuccessful(String data) {
        BannerData bannerData = ParseUtil.parseData(data, BannerData.class);
        if (mView != null) {
            mView.drawBannerInfo(bannerData);
        }
    }

    private void handleLoadBannerFailed(BaseDomainData data) {

    }
}
