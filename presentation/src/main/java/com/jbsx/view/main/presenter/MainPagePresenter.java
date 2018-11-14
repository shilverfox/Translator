package com.jbsx.view.main.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.interactor.MainViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.utils.MessageTools;
import com.jbsx.view.main.contact.MainContact;
import com.jbsx.view.main.contact.MainPageContact;
import com.jbsx.view.main.entity.BannerData;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.entity.HostData;
import com.jbsx.view.main.entity.SpecialAlbumData;
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

                @Override
                public void onNetError() {
                    if (mView != null) {
                        mView.drawEmptyBanner();
                    }
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
        MessageTools.showErrorMessage(data);
    }

    @Override
    public void requestHostList() {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.requestHostList(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadHostFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadHostSuccessful(data);
                }

                @Override
                public void onNetError() {

                }
            });
        }
    }

    private void handleLoadHostSuccessful(String data) {
        HostData parseData = ParseUtil.parseData(data, HostData.class);
        if (mView != null) {
            mView.drawHostInfo(parseData);
        }
    }

    private void handleLoadHostFailed(BaseDomainData data) {

    }

    @Override
    public void requestSpecialAlbumList(int page) {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.requestSpecialAlbusList(page, new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadAlbumFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadAlbumSuccessful(data);
                }

                @Override
                public void onNetError() {

                }
            });
        }
    }

    private void handleLoadAlbumSuccessful(String data) {
        SpecialAlbumData parseData = ParseUtil.parseData(data, SpecialAlbumData.class);
        if (mView != null) {
            mView.drawSpecialAlbumInfo(parseData);
        }
    }

    private void handleLoadAlbumFailed(BaseDomainData data) {

    }

    @Override
    public void requestCelebrities() {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.requestCelebrities(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadCelebritiesFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadCelebritiesSuccessful(data);
                }

                @Override
                public void onNetError() {

                }
            });
        }
    }

    private void handleLoadCelebritiesSuccessful(String data) {
        CelebrityData parseData = ParseUtil.parseData(data, CelebrityData.class);
        if (mView != null) {
            mView.drawCelebritiesInfo(parseData);
        }
    }

    private void handleLoadCelebritiesFailed(BaseDomainData data) {

    }
}
