package com.app.domain.net.interactor;

import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.repository.ITaskDataSource;

/**
 * Created by lijian15 on 2018/1/18.
 */

public class CommonListFragmentUserCase extends UserCase {
    public CommonListFragmentUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        super(dataSource, threadExecutor);
    }
}
