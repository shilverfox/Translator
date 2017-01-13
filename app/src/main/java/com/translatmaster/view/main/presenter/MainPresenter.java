package com.translatmaster.view.main.presenter;

import com.translatmaster.data.ConstData;
import com.translatmaster.data.HttpRequestPool;
import com.translatmaster.net.BaseResponse;
import com.translatmaster.net.IBaseRequestCallback;
import com.translatmaster.net.RequestManager;
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
 * Anything that belong to the Android can not be used in here.
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
     * Send request to Google and get the result.
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
            public void call(BaseResponse s) {
                // You can handle both cases for succeed and failure
                if (mView != null && s != null) {
                    mView.drawTranslatResult(s.getContent());
                }
            }
        };

        Observable.just("").observeOn(Schedulers.io())
                .map(dataAction)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(viewAction);



//        String url = ConstData.GOOGLE_TRANS_URL + paramToUrl(content);
//        RequestManager.setRequest(HttpRequestPool.getTranslateResult(url),
//                new IBaseRequestCallback() {
//
//                    @Override
//                    public void onRequestFailed(String errMessage) {
//                        // Show some error message to user.
//                    }
//
//                    @Override
//                    public void onRequestSuccessful(String json) {
//                        // Update UI
//                        if (mView != null) {
//                            mView.drawTranslatResult(json);
//                        }
//                    }
//                });
    }

//    /**
//     * Generate url that can be handled by Google API.
//     * formate to: q="This is a beautiful day!&target=es&format=text&source=en&key=" + APP_KEY;
//     *
//     * @param content string that need to be translated
//     * @return
//     */
//    private String paramToUrl(String content) {
//        StringBuilder mParams = new StringBuilder();
//        mParams.append("q=").append(content).append("&");
//        mParams.append("target=").append("zh").append("&");
//        mParams.append("format=").append("text").append("&");
//        mParams.append("source=").append("en").append("&");
//        mParams.append("key=").append(ConstData.GOOTLE_APP_KEY);
//
//        return mParams.toString();
//    }
}
