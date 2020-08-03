package com.jbsx.view.search.callback;

import com.jbsx.view.search.entity.SearchEvent;

/**
 * 监听搜索请求
 *
 * Created by lijian on 2018/11/10.
 */

public interface ISearchRequestListener {
    void onReceivedSearchRequest(SearchEvent event);
}
