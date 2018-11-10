package com.translatmaster.view.main.contact;

import com.translatmaster.view.main.entity.BannerData;
import com.translatmaster.view.main.entity.HostData;
import com.youth.banner.Banner;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class MainPageContact {
    public interface View {
        void createPresenter();
        void drawBannerInfo(BannerData bannerData);
        void drawHostInfo(HostData hostData);
    }

    public interface Presenter {
        void start();
        void requestBannerInfo();

        /**
         * 获取主讲嘉宾列表
         */
        void requestHostList();
    }
}
