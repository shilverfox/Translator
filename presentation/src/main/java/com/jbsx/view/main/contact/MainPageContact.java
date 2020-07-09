package com.jbsx.view.main.contact;

import com.jbsx.view.main.entity.BannerData;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.entity.HostData;
import com.jbsx.view.main.entity.MainPageData;
import com.jbsx.view.main.entity.SpecialAlbumData;
import com.youth.banner.Banner;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class MainPageContact {
    public interface View {
        void createPresenter();
        void drawBannerInfo(MainPageData data);
        void drawHostInfo(HostData hostData);
        void drawSpecialAlbumInfo(SpecialAlbumData albumsData);
        void drawCelebritiesInfo(CelebrityData celebrityData);

        /** 获取banner数据为空，每个模块单独处理 */
        void drawEmptyBanner(String errorMessage);
        void drawEmptyCelebrities(String errorMessage);
        void drawEmptySpecialAlbum(String errorMessage);

        /** 联网失败，整个一个大页面 */
        void drawNetError();
    }

    public interface Presenter {
        void start();
        void requestMainPageInfo();

        /**
         * 获取主讲嘉宾列表
         */
        void requestHostList();

        /**
         * 专题列表
         */
        void requestSpecialAlbumList(int page);

        /** 名家列表 */
        void requestCelebrities();
    }
}
