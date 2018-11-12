package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * 首页：片库
 */
public class RepertoryUserCase extends UserCase {
    public RepertoryUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * 获取名家列表
     *
     * @param callback
     */
    public void requestCelebrities(BaseRequestCallback callback) {
        BaseRequestEntity entity = HttpRequestPool.getRepertoryCelebrtiesEntity();
        handleSendRequest(entity, callback);
    }
}
