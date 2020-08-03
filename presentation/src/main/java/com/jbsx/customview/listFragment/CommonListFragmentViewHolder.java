package com.jbsx.customview.listFragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 通用列表项Holder
 *
 * Created by lijian15 on 2017/6/5.
 */

public abstract class CommonListFragmentViewHolder<T> extends RecyclerView.ViewHolder {
    private View mRootView;
    public IOnCommonItemClick mClickListener;

    public CommonListFragmentViewHolder(View view) {
        super(view);

        mRootView = view;
        findViews(mRootView);
        registerEvent();
    }

    public void setData(T data, int position) {
        drawViews(data, position);
    }

    /**
     * 设置Item点击监听
     */
    public void setItemClickListener(IOnCommonItemClick clickListener) {
        mClickListener = clickListener;
    }

    /**
     * 返回整个View
     *
     * @return
     */
    public View getView() {
        return mRootView;
    }


    /** 初始化view对象 */
    public abstract void findViews(View rootView);

    /** 控件事件 */
    public abstract void registerEvent();

    /** UI渲染 */
    public abstract void drawViews(T data, int position);

    /** 是否为最后一行 */
    public abstract void isTheLastLine(boolean theLastLine);
}
