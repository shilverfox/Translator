package com.jbsx.utils;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 搜索辅助类
 */
public class SearchHelper {
    private Context mContext;
    private Handler mHanlder;

    public SearchHelper(Context context, Handler handler) {
        mContext = context;
        mHanlder = handler;
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
        String searchHistory = PersistentUtils.getSearcherHistory(mContext);
        return searchHistory;
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

        if (historyList.size() > 10) {
            // 不超过10条记录
            historyList.remove(0);
        }
        StringBuilder sbf = new StringBuilder();
        for (String his : historyList) {
            sbf.append(his);
            sbf.append(",");
        }

        PersistentUtils.saveSearcherHistory(mContext, sbf.toString());
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

        PersistentUtils.saveSearcherHistory(mContext, sbf.toString());
    }
}
