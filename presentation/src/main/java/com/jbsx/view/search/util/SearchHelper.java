package com.jbsx.view.search.util;

import android.text.TextUtils;

import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.PersistentUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 搜索辅助类
 */
public class SearchHelper {
    /** 上限 */
    private final static int MAX_HISTORY_COUNT = 10;

    public SearchHelper() {
    }

    public List<String> getSearchHistory() {
        ArrayList<String> historyList = new ArrayList<String>();
        String searchHistory = getSharePrefSearchList();
        if (TextUtils.isEmpty(searchHistory) || ",".equals(searchHistory)) {
            return historyList;
        }

        String[] historyArray = searchHistory.split(",");
        for (String item : historyArray) {
            historyList.add(item);
        }

        Collections.reverse(historyList);
        return historyList;
    }

    private String getSharePrefSearchList() {
        String searchHistory = PersistentUtils.getSearcherHistory(MainApplicationLike.getAppContext());
        return searchHistory;
    }

    public void removeSearchHistory() {
        PersistentUtils.clearSearchHistory(MainApplicationLike.getAppContext());
    }

    public void saveSearchHistory(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return;
        }

        ArrayList<String> historyList = new ArrayList<String>();
        String searchHistory = getSharePrefSearchList();
        if (TextUtils.isEmpty(searchHistory) || ",".equals(searchHistory)) {
            historyList.add(searchKey);
        } else {
            String[] historyArray = searchHistory.split(",");
            for (String item : historyArray) {
                if (!item.equals(searchKey)) {
                    historyList.add(item);
                }
            }
            historyList.add(searchKey);
        }

        if (historyList.size() > MAX_HISTORY_COUNT) {
            // 不超过10条记录
            historyList.remove(0);
        }

        StringBuilder sbf = new StringBuilder();
        for (String his : historyList) {
            sbf.append(his);
            sbf.append(",");
        }

        PersistentUtils.saveSearcherHistory(MainApplicationLike.getAppContext(), sbf.toString());
    }

    public void removeKey(String searchKey) {
        // 1  获取已经存在的搜索词
        String searchHistory = getSharePrefSearchList();

        if (TextUtils.isEmpty(searchHistory) || ",".equals(searchHistory)) {
            return;
        }

        // 2 将获取的搜索词，组装成list, 删除搜索历史词
        List<String> list = Arrays.asList(searchHistory.split(","));
        List<String> arrList = new ArrayList(list);

        Iterator<String> it = arrList.iterator();
        while (it.hasNext()) {
            String item = it.next();
            if (item.contains(searchKey)) {
                it.remove();
                break;
            }
        }

        // 3  拼装存储
        StringBuilder sbf = new StringBuilder();
        for (String str : arrList) {
            sbf.append(str).append(",");
        }

        PersistentUtils.saveSearcherHistory(MainApplicationLike.getAppContext(), sbf.toString());
    }

    public void doSearch(final String searchKey) {
        doSearch(searchKey, 0, true);
    }

    /**
     * 搜索
     *
     * @param searchKey
     * @param defaultFocus 搜索结果页的默认选中tab
     * @param saveKey 是否保存搜索关键字
     */
    public void doSearch(final String searchKey, final int defaultFocus, boolean saveKey) {
        if (saveKey) {
            saveSearchHistory(searchKey);
        }

        // Event bus 需要等到Activity初始化完毕后才能响应
        MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(SearchEventGenerator.getKeyWordSearch(searchKey, defaultFocus));
            }
        }, 1000);
    }
}
