package com.app.domain.net.interactor;

import com.app.domain.entity.TestData;
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

public class TestUserCase {
    private ITaskDataSource mTaskDataSource;
    private IBaseRequestCallback<TestData> mRequestCallback;
    private PostExecutionThread mThreadExecutor;

    public TestUserCase(ITaskDataSource dataSource, PostExecutionThread threadExecutor,
                        IBaseRequestCallback<TestData> callback) {
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

        Observable.just("").subscribeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(mThreadExecutor.getScheduler())
                .subscribe(viewAction);
    }

    private void handleResponse(String response, IBaseRequestCallback<TestData> callback) {
        TestData data = ParseUtil.parseData(response, TestData.class);

        if (isResponseSuccessful(data)) {
            handleResponseSuccessful(data, callback);
        } else {
            handleResponseFailed(data, callback);
        }
    }

    private boolean isResponseSuccessful(TestData data) {
        return data != null && "0".equals(data.getCode());
    }

    private void handleResponseSuccessful(TestData response, IBaseRequestCallback<TestData> callback) {
        if (callback != null) {
            callback.onRequestSuccessful(response);
        }
    }

    private void handleResponseFailed(TestData response, IBaseRequestCallback<TestData> callback) {
        if (callback != null) {
            callback.onRequestFailed(response);
        }
    }
}
