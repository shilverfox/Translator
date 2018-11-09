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
}
