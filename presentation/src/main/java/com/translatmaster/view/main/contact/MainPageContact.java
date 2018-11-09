package com.translatmaster.view.main.contact;

import com.translatmaster.view.main.entity.BannerData;
import com.youth.banner.Banner;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class MainPageContact {
    public interface View {
        void createPresenter();
        void drawBannerInfo(BannerData bannerData);
    }

    public interface Presenter {
        void start();
        void requestBannerInfo();
    }
}
