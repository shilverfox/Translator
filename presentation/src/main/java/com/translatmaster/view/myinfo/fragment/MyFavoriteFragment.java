package com.translatmaster.view.myinfo.fragment;

import com.app.domain.net.data.ConstData;

/**
 * 我的收藏
 */
public class MyFavoriteFragment extends MyViewHistoryFragment {
    public MyFavoriteFragment() {
        // 因为他是静态的，每次一个新的Fragment要重新初始化
        mCanSelectItem = false;
    }

    public static MyFavoriteFragment newInstance() {
        return new MyFavoriteFragment();
    }

    @Override
    public String getTitle() {
        return "我的收藏";
    }

    @Override
    public String getSearchFunctionId() {
        return ConstData.FUNCTION_ID_MY_FAVORITE;
    }

    @Override
    public boolean isHistory() {
        return false;
    }
}
