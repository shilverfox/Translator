package com.jbsx.view.myinfo.contact;

/**
 * 观看历史
 */
public class MyViewHistoryContact {
    public interface View {
        void createPresenter();

        /** 当前页面是否为观看历史 */
        boolean isHistory();

        /** 操作成功后刷新页面 */
        void refreshView();
        void showMessage(String message);
    }

    public interface Presenter {
        void start();
        void requestDelete(String[] ids);
    }
}
