package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

public class SearchUserCase extends UserCase {
    public SearchUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }

    /**
     * 获取搜索热词
     * @param callback
     */
    public void requestHotWords(BaseRequestCallback callback) {
//        BaseRequestEntity entity = HttpRequestPool.getDeleteVideoEntity(token, isHistory);
//        handleSendRequest(entity, callback);
    }
}
