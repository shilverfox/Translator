package com.jbsx.customview.listFragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijian15 on 2017/6/5.
 */

public abstract class CommonListFragmentAdapter<T>
        extends RecyclerView.Adapter<CommonListFragmentViewHolder> {

    private Context mContext;
    private List<T> mAllData;
    private IOnCommonItemClick mClickListener;

    public CommonListFragmentAdapter(Context context) {
        mContext = context;
        mAllData = new ArrayList();
    }

    public void clearData() {
        mAllData.clear();
    }

    public void addItems(List<T> allList) {
        if (allList != null) {
            for (int i = 0; i < allList.size(); i++) {
                mAllData.add(allList.get(i));
            }
        }

        notifyDataSetChanged();
    }

    public void setData(List<T> allData) {
        mAllData = allData;
    }

    public List<T> getData() {
        return mAllData;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public CommonListFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
                getViewId(), null, false);

        return getViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CommonListFragmentViewHolder holder, final int position) {
        if (mAllData != null) {
            holder.isTheLastLine(position == mAllData.size() - 1);
            holder.setData(mAllData.get(position), position);
            holder.setItemClickListener(mClickListener);
        }
    }

    @Override
    public int getItemCount() {
        return mAllData != null ? mAllData.size(): 0 ;
    }

    /**
     * 设置Item点击监听
     *
     * @param clickListener
     */
    public void setItemClickListener(IOnCommonItemClick clickListener) {
        mClickListener = clickListener;
    }


    /** Holder的layout */
    public abstract int getViewId();

    /** 创建Holder对象 */
    public abstract CommonListFragmentViewHolder getViewHolder(View rootView);
}
