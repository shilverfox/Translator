package com.app.domain.net.interactor;

import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.net.model.BaseRequestEntity;
import com.app.domain.net.model.BaseResponse;
import com.app.domain.net.repository.ITaskDataSource;
import com.app.domain.util.ParseUtil;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lijian15 on 2018/1/18.
 */

public abstract class UserCase {
    public ITaskDataSource mTaskDataSource;
//    public BaseRequestCallback mRequestCallback;
    public PostExecutionThread mThreadExecutor;

    public UserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor) {
        mTaskDataSource = dataSource;
        mThreadExecutor = threadExecutor;
    }

//    public void setRequestCallback(BaseRequestCallback callback) {
//        mRequestCallback = callback;
//    }

    public void handleSendRequest(final BaseRequestEntity entity,
                                  final BaseRequestCallback callback) {
        Func1 dataAction = new Func1() {
            @Override
            public Object call(Object o) {
                return mTaskDataSource.handleRequest(entity);
            }
        };

        Action1 viewAction = new Action1<BaseResponse>() {
            @Override
            public void call(BaseResponse response) {
                handleResponse(response, callback);
            }
        };

        Observable.just("").subscribeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(mThreadExecutor.getScheduler())
                .subscribe(viewAction);
    }

    private void handleResponse(BaseResponse response, BaseRequestCallback callback) {
        BaseDomainData data = ParseUtil.parseData(response.getContent(), BaseDomainData.class);
        if (isResponseSuccessful(data)) {
            handleResponseSuccessful(response.getContent(), callback);
        } else {
            handleResponseFailed(data, response.getContent(), callback);
        }
    }

    private boolean isResponseSuccessful(BaseDomainData baseDomainData) {
        return baseDomainData != null && baseDomainData.getPayload() != null
                && baseDomainData.getPayload().getResultStatus() != null
                && "0".equals(baseDomainData.getPayload().getResultStatus().getStatus());
    }

    private void handleResponseSuccessful(String responseContent, BaseRequestCallback callback) {
        if (callback != null) {
            callback.onRequestSuccessful(responseContent);
        }
    }

    private void handleResponseFailed(BaseDomainData baseDomainData, String errorMessage,
                                      BaseRequestCallback callback) {
        if (callback != null) {
            // If there is json or just a error message.
            if (baseDomainData == null) {
                // just a error message 联网失败
//                baseDomainData = createErrorDomainData(errorMessage);
                callback.onNetError();
            } else {
                callback.onRequestFailed(baseDomainData);
            }

        }
    }

    /**
     * Data层联网失败会返回默认字符串，非json格式，因此需要单独处理
     *
     * @param errorMessage
     * @return
     */
    private BaseDomainData createErrorDomainData(String errorMessage) {
        BaseDomainData data = new BaseDomainData();
        data.setMsg(errorMessage);
        return data;
    }
}
