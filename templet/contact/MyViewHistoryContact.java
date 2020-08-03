package com.jbsx.view.myinfo.contact;

/**
 * 观看历史
 */
public class MyViewHistoryContact {
    public interface View {
        void createPresenter();
    }

    public interface Presenter {
        void start();

        /**
         * 观看历史列表
         */
        void requestHistoryList(int page);
    }
}
