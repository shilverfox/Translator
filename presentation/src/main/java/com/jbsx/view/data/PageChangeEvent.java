package com.jbsx.view.data;

public class PageChangeEvent {
    public String mTabType;
    public String mRequestParam;

    /** 当前页面类型 */
    public String mCurrentPageType;

    public PageChangeEvent(String tabType, String requestParam) {
        mTabType = tabType;
        mRequestParam = requestParam;
    }

    public PageChangeEvent(String tabType, String curType, String requestParam) {
        mTabType = tabType;
        mCurrentPageType = curType;
        mRequestParam = requestParam;
    }
}
