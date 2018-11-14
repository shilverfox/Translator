package com.jbsx.view.myinfo.contact;

/**
 * 观看历史
 */
public class MyViewHistoryContact {
    public interface View {
        void createPresenter();

        /** 当前页面是否为观看历史 */
        boolean isHistory();
    }

    public interface Presenter {
        void start();
        void requestDelete();
    }
}
