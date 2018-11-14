package com.app.domain.net.interactor;

import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.repository.ITaskDataSource;

public class MyInfoUserCase extends UserCase {
    public MyInfoUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }
}
