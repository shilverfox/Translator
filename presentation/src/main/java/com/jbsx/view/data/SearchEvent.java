package com.jbsx.view.data;

public class SearchEvent {
    public String mNaviId;
    public String mTabType;
    public String mRequestParam;

    /** 当前页面类型 */
    public Integer mCurrentPageType;

    public SearchEvent(String naviId, String tabType, Integer curType, String requestParam) {
        mNaviId = naviId;
        mTabType = tabType;
        mCurrentPageType = curType;
        mRequestParam = requestParam;
    }
}
