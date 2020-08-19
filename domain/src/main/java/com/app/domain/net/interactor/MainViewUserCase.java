package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Created by lijian15 on 2018/1/18.
 */

public class MainViewUserCase extends UserCase {
    public MainViewUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * Call data layer to get data.
     *
     * @param content
     * @param src
     * @param dest
     */
    public void requestTranslate(final String content, final String src, final String dest,
                                 BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getTestEntity();// getTranslateResult(content, src, dest);
        handleSendRequest(entity, callback);
    }

    public void requestNavigation(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getNavigationEntity();
        handleSendRequest(entity, callback);
    }

    public void requestOrgState(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getOrgStateEntity();
        handleSendRequest(entity, callback);
    }

    public void sendHearBeatInfo(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getHeartBeatEntity();
        handleSendRequest(entity, callback);
    }
}
