package com.jbsx.customview.recyclerview;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

/**
 * 分页展示数据时，RecyclerView的FooterView State 操作工具类
 * <p>
 * RecyclerView一共有几种State：Normal/Loading/Error/TheEnd
 */
public class RecyclerViewStateUtils {

    /**
     * 设置headerAndFooterAdapter的FooterView State
     *
     * @param instance      context
     * @param recyclerView  recyclerView
     * @param pageSize      分页展示时，recyclerView每一页的数量
     * @param state         FooterView State
     * @param errorListener FooterView处于Error状态时的点击事件
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, int pageSize
            , LoadingFooter.State state, View.OnClickListener errorListener) {
        setFooterViewState(instance, recyclerView, pageSize, state, errorListener, false, null);
    }

    public static void setFooterViewState(Activity instance, RecyclerView recyclerView
            , int pageSize, LoadingFooter.State state, View.OnClickListener errorListener, boolean isNewEndText, String defaultText) {

        if (instance == null || instance.isFinishing()) {
            return;
        }

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();

        if (outerAdapter == null || !(outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter)) {
            return;
        }

        HeaderAndFooterRecyclerViewAdapter headerAndFooterAdapter = (HeaderAndFooterRecyclerViewAdapter) outerAdapter;
        LoadingFooter footerView;

        // 已经有footerView了
        if (headerAndFooterAdapter.getFooterViewsCount() > 0) {
            footerView = (LoadingFooter) headerAndFooterAdapter.getFooterView();
            if (isNewEndText) {
                if (TextUtils.isEmpty(defaultText)) {
                    footerView.setStrEndText("去看看其他分类吧");
                } else {
                    footerView.setStrEndText(defaultText);
                }
            }
            footerView.setState(state);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        } else {
            footerView = new LoadingFooter(instance);
            if (isNewEndText) {
                if (TextUtils.isEmpty(defaultText)) {
                    footerView.setStrEndText("去看看其他分类吧");
                } else {
                    footerView.setStrEndText(defaultText);
                }
            }
            footerView.setState(state);

            if (state == LoadingFooter.State.NetWorkError) {
                footerView.setOnClickListener(errorListener);
            }

            headerAndFooterAdapter.addFooterView(footerView);
            recyclerView.scrollToPosition(headerAndFooterAdapter.getItemCount() - 1);
        }
    }

    /**
     * 获取当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     */
    public static LoadingFooter.State getFooterViewState(RecyclerView recyclerView) {

        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView.getState();
            }
        }

        return LoadingFooter.State.Normal;
    }

    public static View getFooterView(RecyclerView recyclerView) {
        RecyclerView.Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                return footerView;
            }
        }

        return null;
    }

    /**
     * 设置当前RecyclerView.FooterView的状态
     *
     * @param recyclerView
     * @param state
     */
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, LoadingFooter.State state) {
        RecyclerView.Adapter outerAdapter = null;
        if (recyclerView != null) {
            outerAdapter = recyclerView.getAdapter();
        }
        if (outerAdapter != null && outerAdapter instanceof HeaderAndFooterRecyclerViewAdapter) {
            if (((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
                LoadingFooter footerView = (LoadingFooter) ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).getFooterView();
                footerView.setState(state);
            } else {
                LoadingFooter footerView = new LoadingFooter(instance);
                ((HeaderAndFooterRecyclerViewAdapter) outerAdapter).addFooterView(footerView);
                footerView.setState(LoadingFooter.State.ONE_PAGE);
            }
        }
    }


//
}
