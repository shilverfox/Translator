package com.translatmaster.view.main.presenter;

import com.app.domain.net.repository.BaseDominData;
import com.app.domain.net.repository.TaskManager;
import com.translatmaster.view.main.contact.MainContact;

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

    public MainPresenter(MainContact.View view) {
        mView = view;
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
    public void requestTranslate(final String content, final String src, final String dest) {
        Func1 dataAction = new Func1() {
            @Override
            public Object call(Object o) {
                return TaskManager.getTaskManager().requestTranslate(content, src, dest);
            }
        };

        Action1 viewAction = new Action1<BaseDominData>() {

            @Override
            public void call(BaseDominData response) {
                // You can handle both cases for succeed and failure
                if (mView != null && response != null) {
                    mView.drawTranslatResult(response.getBaseResponse().getContent());
                }
            }
        };

        Observable.just("").observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);
    }
}
