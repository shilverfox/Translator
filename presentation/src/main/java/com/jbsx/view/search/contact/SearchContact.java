package com.jbsx.view.search.contact;

/**
 * 搜索中间页
 */
public class SearchContact {
    public interface View {
        void createPresenter();
    }

    public interface Presenter {
        void start();

        /** 获取搜索热词 */
        void requestHotWords();

        /** 获取搜索历史 */
        void loadSearchHistory();
    }
}
