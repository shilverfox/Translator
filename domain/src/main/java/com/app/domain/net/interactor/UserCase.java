package com.app.domain.net.interactor;

import com.app.domain.net.IBaseRequestCallback;
import com.app.domain.net.executor.PostExecutionThread;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.net.repository.ITaskDataSource;
import com.app.domain.util.ParseUtil;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lijian15 on 2018/1/18.
 */

public class UserCase<T> {
    private ITaskDataSource mTaskDataSource;
    private IBaseRequestCallback<T> mRequestCallback;
    private PostExecutionThread mThreadExecutor;

    public UserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor,
                    IBaseRequestCallback<T> callback) {
        mTaskDataSource = dataSource;
        mRequestCallback = callback;
        mThreadExecutor = threadExecutor;
    }

    public void requestTranslate(final String content, final String src, final String dest) {
        Func1 dataAction = new Func1() {
            @Override
            public Object call(Object o) {
                return mTaskDataSource.requestTranslate(content, src, dest);
            }
        };

        Action1 viewAction = new Action1<String>() {
            @Override
            public void call(String response) {
                handleResponse(response, mRequestCallback);
            }
        };

        Observable.just("").observeOn(Schedulers.io())
                .map(dataAction)
                .subscribeOn(mThreadExecutor.getScheduler())
                .subscribe(viewAction);
    }

    private void handleResponse(String response, IBaseRequestCallback<T> callback) {
        BaseDomainData data = ParseUtil.parseData(response, BaseDomainData.class);
        if (isResponseSuccessful(data)) {
            handleResponseSuccessful(response, callback);
        } else {
            handleResponseFailed(response, callback);
        }
    }

    private boolean isResponseSuccessful(BaseDomainData data) {
        return data != null && "0".equals(data.getCode());
    }

    private void handleResponseSuccessful(String response, IBaseRequestCallback callback) {
        if (callback != null) {
            callback.onRequestSuccessful(response);
        }
    }

    private void handleResponseFailed(String response, IBaseRequestCallback callback) {
        if (callback != null) {
            callback.onRequestFailed(response);
        }
    }
}
