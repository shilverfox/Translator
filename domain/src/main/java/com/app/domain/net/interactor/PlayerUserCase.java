package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * 播放器页面
 */
public class PlayerUserCase extends UserCase {
    public PlayerUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * 获取专辑中的视频列表
     *
     * @param albumId
     * @param userId
     * @param callback
     */
    public void requestVideoListOfAlbum(String albumId, String userId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getVideoListOfAlbumEntity(albumId, userId);
        handleSendRequest(entity, callback);
    }

    /**
     * 获得某个视频的播放信息
     *
     * @param token
     * @param singleId
     * @param callback
     */
    public void requestSingleVideo(String token, String singleId, String type, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getVideoPlayInfoEntity(token, singleId, type);
        handleSendRequest(entity, callback);
    }

    /**
     * 查看片库详情
     *
     * @param albumId
     * @param singleId
     * @param callback
     */
    public void requestAlbumDetail(String albumId, String singleId, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getAlbumDetailEntity(albumId, singleId);
        handleSendRequest(entity, callback);
    }
}
