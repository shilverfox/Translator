package com.jbsx.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.jbsx.R;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.LogTools;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.fragment.GalleryFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理所有的Fragment
 */
public class PageManager {
    /** 每个tab对应一个map的数据 */
    private Map<String, Map<String, Fragment>> mAllPages;

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
            mAllPages.put(key, new HashMap<String, Fragment>());
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
    public void addPage(String parentKey, String pageType, Fragment fragment) {
        if (!TextUtils.isEmpty(parentKey) && fragment != null) {
            Map<String, Fragment> pageList = mAllPages.get(parentKey);
            if (pageList != null) {
                pageList.put(pageType, fragment);
            }
        }


        LogTools.e("MainActivity", mAllPages.toString());
    }

    public void removePage(String parentKey, String pageType, Fragment fragment) {
        if (!TextUtils.isEmpty(parentKey) && fragment != null) {
            Map<String, Fragment> pageList = mAllPages.get(parentKey);
            if (pageList != null) {
                pageList.remove(pageType);
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
            dispatchPage(changeEvent);
        }
    }

    /**
     * tab容器里
     *
     * @param tabType
     * @return
     */
    public String getPageTypeByTab(String tabType) {
        return "";
    }

    /**
     * 每个tab内部容纳fragment的id
     *
     * @param tabType
     * @return
     */
    private int getPageContainerId(String tabType) {
        return R.id.fl_container;
    }

    private String getNextPage(String currentPage) {
        if (AppConstData.PAGE_TYPE_MAIN.equals(currentPage)) {
            return AppConstData.PAGE_TYPE_VIDEO_1;
        }

        return "";
    }

    /**
     * 调度某个fragment显示
     */
    private void dispatchPage(PageChangeEvent pageChangeData) {
        String naviId = pageChangeData.mNaviId;
        String tabType = pageChangeData.mTabType;
        String pageType = getNextPage(pageChangeData.mCurrentPageType);

        if (!TextUtils.isEmpty(naviId) && !TextUtils.isEmpty(pageType)) {
            Map<String, Fragment> pageList = mAllPages.get(naviId);
            if (pageList != null) {
                Fragment page = pageList.get(pageType);
                if (page == null) {
                    // 创建一个新的
                    pageChangeData.mCurrentPageType = pageType;
                    page = createPage(pageChangeData);
                }

                if (mFragmentManager != null) {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    transaction.add(getPageContainerId(tabType), page);
                    transaction.commitAllowingStateLoss();
                }
            }
        }
    }

    private Fragment createPage(PageChangeEvent pageChangeData) {
        return GalleryFragment.newInstance(pageChangeData.mNaviId, pageChangeData.mTabType,
                pageChangeData.mCurrentPageType, pageChangeData.mRequestParam);
    }
}
