package com.translatmaster.view.main.presenter;

import com.app.data.net.repository.TaskManager;
import com.app.domain.entity.TestData;
import com.app.domain.net.IBaseRequestCallback;
import com.app.domain.net.interactor.TestUserCase;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.view.main.contact.MainContact;

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
        new TestUserCase(TaskManager.getTaskManager(), MainApplicationLike.getUiThread(),
                new IBaseRequestCallback<TestData>() {
                    @Override
                    public void onRequestSuccessful(TestData json) {
                        handleResponseSuccessful(json);
                    }

                    @Override
                    public void onRequestFailed(TestData data) {
                        handleResponseFailed(data);
                    }
                }).requestTranslate(content, src, dest);
    }

    private void handleResponseSuccessful(TestData resultContent) {
        if (mView != null) {
            mView.drawTranslatResult(resultContent + "");
        }
    }

    private void handleResponseFailed(TestData data) {
        if (mView != null) {
            mView.drawTranslatResult(data + "");
        }
    }
}
