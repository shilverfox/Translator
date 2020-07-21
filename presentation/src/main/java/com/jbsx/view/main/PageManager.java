package com.jbsx.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.jbsx.view.data.PageChangeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理所有的Fragment
 */
public class PageManager {
    /** 每个tab对应一个map的数据 */
    private Map<String, List<Fragment>> mAllPages;

    private FragmentManager mFragmentManager;

    public PageManager() {
        mAllPages = new HashMap();
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    /**
     * 对应具体的tab数量
     *
     * @param key
     */
    public void addTab(String key) {
        if (!TextUtils.isEmpty(key)) {
            mAllPages.put(key, new ArrayList<Fragment>());
        }
    }

    public void removeTab(String key) {
        if (!TextUtils.isEmpty(key)) {
            mAllPages.remove(key);
        }
    }

    /**
     * 给某个tab增加一个fragment
     *
     * @param parentKey
     * @param fragment
     */
    public void addPage(String parentKey, Fragment fragment) {
        if (!TextUtils.isEmpty(parentKey) && fragment != null) {
            List<Fragment> pageList = mAllPages.get(parentKey);
            if (pageList != null) {
                pageList.add(fragment);
            }
        }
    }

    public void removePage(String parentKey, Fragment fragment) {
        if (!TextUtils.isEmpty(parentKey) && fragment != null) {
            List<Fragment> pageList = mAllPages.get(parentKey);
            if (pageList != null) {
                pageList.remove(fragment);
            }
        }
    }

    /**
     * 管理页面切换
     *
     * @param changeEvent
     */
    public void handlePageChange(PageChangeEvent changeEvent) {
        if (changeEvent != null) {
            String tabType = changeEvent.mTabType;
            String requestParam = changeEvent.mRequestParam;
        }
    }

    /**
     * 每个tab内部容纳fragment的id
     *
     * @param tabType
     * @return
     */
    private int getPageContainerId(String tabType) {
        return 0;
    }

    private void getPage(String tabType, String currentPageType) {
//        FragmentManager manager = getChildFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.dddddddd, FeedFragment.newInstance());
//        transaction.commitAllowingStateLoss();

        if (!TextUtils.isEmpty(tabType)) {
            List<Fragment> pageList = mAllPages.get(tabType);
            if (pageList != null) {
            }
        }
    }
}
