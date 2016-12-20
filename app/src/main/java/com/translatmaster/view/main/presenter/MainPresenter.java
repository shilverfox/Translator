package com.translatmaster.view.main.presenter;

import com.translatmaster.data.ConstData;
import com.translatmaster.data.HttpRequestPool;
import com.translatmaster.net.IBaseRequestCallback;
import com.translatmaster.net.RequestManager;
import com.translatmaster.view.main.contact.MainContact;

/**
 * Presenter
 * Anything that belong to the Android can not be used in here.
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
     * Send request to Google and get the result.
     *
     * @param content string that need to be translated
     */
    @Override
    public void requestTranslate(String content) {
        String url = ConstData.GOOGLE_TRANS_URL + paramToUrl(content);
        RequestManager.setRequest(HttpRequestPool.getTranslateResult(url),
                new IBaseRequestCallback() {

                    @Override
                    public void onRequestFailed(String errMessage) {
                        // Show some error message to user.
                    }

                    @Override
                    public void onRequestSuccessful(String json) {
                        // Update UI
                        if (mView != null) {
                            mView.drawTranslatResult(json);
                        }
                    }
                });
    }

    /**
     * Generate url that can be handled by Google API.
     * formate to: q="This is a beautiful day!&target=es&format=text&source=en&key=" + APP_KEY;
     *
     * @param content string that need to be translated
     * @return
     */
    private String paramToUrl(String content) {
        StringBuilder mParams = new StringBuilder();
        mParams.append("q=").append(content).append("&");
        mParams.append("target=").append("zh").append("&");
        mParams.append("format=").append("text").append("&");
        mParams.append("source=").append("en").append("&");
        mParams.append("key=").append(ConstData.GOOTLE_APP_KEY);

        return mParams.toString();
    }
}
