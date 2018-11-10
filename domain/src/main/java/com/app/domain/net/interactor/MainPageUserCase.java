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

    public void requestBannerInfo(BaseRequestCallback callback) {
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
     * 专题列表
     *
     * @param albumId 专题id 可为空
     * @param videoStoreId 片库id 可为空
     */
    public void requestSpecialAlbusList(String albumId, String videoStoreId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getMainPageSpecialAlbumEntity(albumId, videoStoreId);
        handleSendRequest(entity, callback);
    }
}
