package com.jbsx.view.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.jbsx.R;
import com.jbsx.data.AppConstData;
import com.jbsx.utils.LogTools;
import com.jbsx.view.data.PageChangeEvent;
import com.jbsx.view.main.fragment.AlbumPlayerFragment;
import com.jbsx.view.main.fragment.GalleryFragment;
import com.jbsx.view.main.fragment.MainPageFragment;
import com.jbsx.view.main.fragment.VideoFeedFragment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
            mAllPages.put(key, new LinkedHashMap<String, Fragment>());
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
     * 响应返回按键事件，从tabId对应的map中移除当前头部的fragment，然后设置新的头部fragment为当前
     *
     * @param naviId
     */
    public void handleBackEvent(String naviId) {
        if (!TextUtils.isEmpty(naviId)) {
            LinkedHashMap<String, Fragment> pageList = (LinkedHashMap<String, Fragment>) mAllPages.get(naviId);
            if (pageList != null) {
                // 找到最后一个（最新添加的），删除
                Map.Entry currentPage = getTail(pageList);
                if (currentPage != null) {
                    pageList.remove(currentPage.getKey());

                    if (mFragmentManager != null) {
                        // 设置当前最新的显示到屏幕上
                        Map.Entry prevPage = getTail(pageList);
                        if (prevPage != null) {
                            FragmentTransaction transaction = mFragmentManager.beginTransaction();
                            // 隐藏当前的fragment 同时 设置前一个page显示
                            transaction.hide((Fragment) currentPage.getValue());
                            transaction.show((Fragment) prevPage.getValue());
                            transaction.commitAllowingStateLoss();
                        }
                    }
                }
            }
        }
    }

    /**
     * tab切换，隐藏所有当前存在的fragment, 显示当前的
     *
     * @param naviId
     */
    public void handleTabChange(String naviId) {
        if (!TextUtils.isEmpty(naviId)) {
            LinkedHashMap<String, Fragment> pageList = (LinkedHashMap<String, Fragment>) mAllPages.get(naviId);
            if (pageList != null) {
                // 找到最后一个（最新添加的）
                Map.Entry currentPage = getTail(pageList);

                // 遍历所有的fragment
                for (String key : mAllPages.keySet()) {
                    Map<String, Fragment> pages = mAllPages.get(key);
                    for (String innerKey : pages.keySet()) {
                        Fragment page = pages.get(innerKey);
                        // 显示目标fragment，否则隐藏
                        boolean needShow = (page == currentPage.getValue());
                        if (mFragmentManager != null) {
                            FragmentTransaction transaction = mFragmentManager.beginTransaction();
                            if (needShow) {
                                transaction.show(page);
                            } else {
                                transaction.hide(page);
                            }
                            transaction.commitAllowingStateLoss();
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取linkedMap的最后一个元素
     *
     * @param map
     * @param <K>
     * @param <V>
     * @return
     */
    public <K, V> Map.Entry<K, V> getTail(LinkedHashMap<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
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
        } else if (AppConstData.PAGE_TYPE_ALBUM_1.equals(currentPage)) {
            // 专辑分类 to 专辑feed
            return AppConstData.PAGE_TYPE_ALBUM_2;
        } else if (AppConstData.PAGE_TYPE_ALBUM_2.equals(currentPage)) {
            // 专辑列表 to 专辑详情
            return AppConstData.PAGE_TYPE_ALBUM_DETAIL;
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
                    // 加到page列表中
                    addPage(naviId, pageType, page);
                }

                if (mFragmentManager != null && !page.isAdded()) {
                    FragmentTransaction transaction = mFragmentManager.beginTransaction();
                    transaction.add(getPageContainerId(tabType), page);
                    transaction.commitAllowingStateLoss();
                }
            }
        }
    }

    private Fragment createPage(PageChangeEvent pageChangeData) {
        if (AppConstData.PAGE_TYPE_MAIN.equals(pageChangeData.mCurrentPageType)) {
            return MainPageFragment.newInstance(pageChangeData.mNaviId, pageChangeData.mTabType,
                    AppConstData.PAGE_TYPE_MAIN, pageChangeData.mRequestParam);
        } else if (AppConstData.PAGE_TYPE_ALBUM_1.equals(pageChangeData.mCurrentPageType)) {
            return GalleryFragment.newInstance(pageChangeData.mNaviId, pageChangeData.mTabType,
                    AppConstData.PAGE_TYPE_ALBUM_1, pageChangeData.mRequestParam);
        } else if (AppConstData.PAGE_TYPE_ALBUM_2.equals(pageChangeData.mCurrentPageType)) {
            return VideoFeedFragment.newInstance(pageChangeData.mNaviId, pageChangeData.mTabType,
                    AppConstData.PAGE_TYPE_ALBUM_2, pageChangeData.mRequestParam);
        } else if (AppConstData.PAGE_TYPE_ALBUM_DETAIL.equals(pageChangeData.mCurrentPageType)) {
            return AlbumPlayerFragment.newInstance(pageChangeData.mNaviId, pageChangeData.mTabType,
                    AppConstData.PAGE_TYPE_ALBUM_DETAIL, pageChangeData.mRequestParam);
        } else {
            // 默认显示首页
            return MainPageFragment.newInstance(pageChangeData.mNaviId, pageChangeData.mTabType,
                    AppConstData.PAGE_TYPE_MAIN, pageChangeData.mRequestParam);
        }
    }
}
