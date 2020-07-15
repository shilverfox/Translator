package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * 首页：精选
 */
public class MainPageUserCase extends UserCase {
    public MainPageUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    public void requestMainPageInfo(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageBannerInfoEntity();
        handleSendRequest(entity, callback);
    }

    /**
     * 主讲嘉宾
     * @param callback
     */
    public void requestHostList(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageHostListEntity();
        handleSendRequest(entity, callback);
    }

    /**
     * 第一个tab中的专题列表
     */
    public void requestSpecialAlbusList(int page, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageSpecialAlbumEntity(page, false);
        handleSendRequest(entity, callback);
    }

    /**
     * 获取名家列表
     *
     * @param callback
     */
    public void requestCelebrities(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageCelebritiesEntity();
        handleSendRequest(entity, callback);
    }

    /**
     * 首页专题tab
     *
     * @param callback
     */
    public void requestSpecialAlbum(int page, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageSpecialAlbumEntity(page, true);
        handleSendRequest(entity, callback);
    }

    /**
     * 获取一级分类列表（专辑、视频）
     *
     * @param callback
     */
    public void requestGalleryInfo(String code, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageGalleryEntity(code);
        handleSendRequest(entity, callback);
    }
}
