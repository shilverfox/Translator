package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

public class MyInfoUserCase extends UserCase {
    public MyInfoUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * 删除收藏、观看历史
     *
     * @param token
     * @param isHistory
     * @param callback
     */
    public void requestDeleteVideo(String token, boolean isHistory, BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getDeleteVideoEntity(token, isHistory);
        handleSendRequest(entity, callback);
    }
}
