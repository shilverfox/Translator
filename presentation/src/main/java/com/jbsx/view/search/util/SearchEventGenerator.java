package com.jbsx.view.search.util;

import com.app.domain.net.data.ConstData;
import com.jbsx.view.search.entity.SearchEvent;

/**
 * 搜索条件
 */
public class SearchEventGenerator {
    /**
     * 关键字搜索（所以名家id不要）
     * 首页搜索
     *
     * @param searchKey
     * @return
     */
    public static SearchEvent getKeyWordSearch(String searchKey, int defaultFocus) {
        SearchEvent event = new SearchEvent(ConstData.INVALID_CELEBRITY_ID, searchKey,
                SearchEvent.SEARCH_TYPE_PIANKU_AND_ZHUANTI,
                SearchEvent.SEARCH_SORT_BY_HOT);
        event.setDefaultFocus(defaultFocus);

        return event;
    }

    /**
     * 通过名家id查询
     * 首页片库
     *
     * @param celebrityId
     * @return
     */
    public static SearchEvent getCelebrityIdSearch(int celebrityId, int sortType) {
        SearchEvent event = new SearchEvent(celebrityId, null,
                SearchEvent.SEARCH_TYPE_PIANKU, sortType);

        return event;
    }
}
