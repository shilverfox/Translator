package com.jbsx.view.main.presenter;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MainPageUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.utils.MessageTools;
import com.jbsx.view.main.contact.MainPageContact;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.entity.HostData;
import com.jbsx.view.main.entity.MainPageData;
import com.jbsx.view.main.entity.SpecialAlbumData;

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
    public void requestMainPageInfo() {
        if (mMainPageUserCase != null) {
            mMainPageUserCase.requestMainPageInfo(new BaseRequestCallback() {
                @Override
                public void onRequestFailed(BaseDomainData data) {
                    handleLoadDataFailed(data);
                }

                @Override
                public void onRequestSuccessful(String data) {
                    handleLoadDataSuccessful(data);
                }

                @Override
                public void onNetError() {
                    handlePageNetError();
                }
            });
        }
    }

    private void handleLoadDataSuccessful(String data) {
        MainPageData bannerData = ParseUtil.parseData(data, MainPageData.class);
        if (mView != null) {
            mView.drawBannerInfo(bannerData);
        }
    }

    private void handleLoadDataFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
        if (mView != null) {
            mView.drawEmptyBanner(MessageTools.getMessage(data));
        }
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
                    handlePageNetError();
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
        if (mView != null) {
            mView.drawEmptyCelebrities(MessageTools.getMessage(data));
        }
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
                    handlePageNetError();
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
        if (mView != null) {
            mView.drawEmptySpecialAlbum(MessageTools.getMessage(data));
        }
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
                    handlePageNetError();
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
        if (mView != null) {
            mView.drawEmptyCelebrities(MessageTools.getMessage(data));
        }
    }

    /**
     * 联网失败统一走这里，整个页面显示一个失败
     */
    private void handlePageNetError() {
        if (mView != null) {
            mView.drawNetError();
        }
    }
}
