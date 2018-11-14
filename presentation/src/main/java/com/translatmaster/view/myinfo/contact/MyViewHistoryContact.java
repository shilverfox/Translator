package com.translatmaster.view.myinfo.contact;

/**
 * 观看历史
 */
public class MyViewHistoryContact {
    public interface View {
        void createPresenter();
    }

    public interface Presenter {
        void start();
        void requestDelete();
    }
}
