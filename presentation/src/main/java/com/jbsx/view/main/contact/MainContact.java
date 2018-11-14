package com.jbsx.view.main.contact;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class MainContact {
    public interface View {
        void createPresenter();

        /** Draw result of translate to UI */
        void drawTranslatResult(String content);
    }

    public interface Presenter {
        void start();

        /** Send request to Google API */
        void requestTranslate(String content, String src, String dest);
    }
}
