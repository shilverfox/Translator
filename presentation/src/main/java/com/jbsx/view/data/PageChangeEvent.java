package com.jbsx.view.data;

public class PageChangeEvent {
    public String mNaviId;
    public String mTabType;
    public String mRequestParam;

    /** 当前页面类型 */
    public Integer mCurrentPageType;

    /** 是否有子页面 */
    public boolean mHasChildren;

    public PageChangeEvent(String tabType, String requestParam) {
        mTabType = tabType;
        mRequestParam = requestParam;
    }

    public PageChangeEvent(String naviId, String tabType, Integer curType, String requestParam) {
        mNaviId = naviId;
        mTabType = tabType;
        mCurrentPageType = curType;
        mRequestParam = requestParam;
    }

    public PageChangeEvent(String naviId, String tabType, Integer curType, String requestParam, boolean hasChildren) {
        this(naviId, tabType, curType, requestParam);
        mHasChildren = hasChildren;
    }
}
