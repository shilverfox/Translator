package com.translatmaster.view.main.presenter;

import com.translatmaster.net.BaseResponse;
import com.translatmaster.view.main.contact.MainContact;
import com.translatmaster.view.main.task.TaskDataSourceImpl;
import com.translatmaster.view.main.task.TaskManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Presenter
 *
 * Created by lijian15 on 2016/12/14.
 */

public class MainPresenter implements MainContact.Presenter {
    private final static String TAG = "MainPresenter";

    private MainContact.View mView;
    private TaskManager mTaskManager;

    public MainPresenter(MainContact.View view) {
        mView = view;
        mTaskManager = new TaskManager(new TaskDataSourceImpl());
    }

    @Override
    public void start() {

    }

    /**
     * All the request should be handled by TaskManager.
     *
     * @param content string that need to be translated
     */
    @Override
    public void requestTranslate(final String content) {
        Func1 dataAction = new Func1() {
            @Override
            public Object call(Object o) {
                return mTaskManager.requestTranslate(content);
            }
        };

        Action1 viewAction = new Action1<BaseResponse>() {

            @Override
            public void call(BaseResponse response) {
                // You can handle both cases for succeed and failure
                if (mView != null && response != null) {
                    mView.drawTranslatResult(response.getContent());
                }
            }
        };

        Observable.just("").observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);
    }
}
