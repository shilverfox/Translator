package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Created by lijian15 on 2018/1/18.
 */

public class TestUserCase extends UserCase {
    public TestUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor,
                        BaseRequestCallback callback) {
        super(dataSource, threadExecutor, callback);
    }

    /**
     * Call data layer to get data.
     *
     * @param content
     * @param src
     * @param dest
     */
    public void requestTranslate(final String content, final String src, final String dest) {
        BaseRequestEntity entity = HttpRequestPool.getTestEntity();// getTranslateResult(content, src, dest);
        handleSendRequest(entity);
    }
}
