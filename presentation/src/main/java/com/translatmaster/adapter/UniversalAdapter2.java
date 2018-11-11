package com.translatmaster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用的Adapter
 */
public abstract class UniversalAdapter2<T> extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<T> mDatas;
    protected int mLayoutId;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((UniversalViewHolder2) holder, mDatas.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(mLayoutId, null);
        UniversalViewHolder2 holder = UniversalViewHolder2.getHolder(view);
        return holder;
    }

    public UniversalAdapter2(Context context, int layoutId) {
        this.mContext = context;
        this.mDatas = new ArrayList<T>();
        this.mLayoutId = layoutId;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 将数据转变到控件中
     */
    public abstract void convert(UniversalViewHolder2 holder, T t, int position);

    /**
     * 添加到当前数据
     */
    public void add(T t) {
        mDatas.add(t);
        notifyDataSetChanged();
    }

    public void addForClear(T t) {
        mDatas.clear();
        mDatas.add(t);
        notifyDataSetChanged();
    }

    /**
     * 添加到当前数据（不会清掉数据，累加）
     */
    public void addList(List<T> ts) {
        mDatas.addAll(ts);

        int oldPosition = mDatas.size() - 1;
        notifyItemInserted(oldPosition);
    }

    /**
     * 添加到当前数据（会清掉当前数据）
     */
    public void setList(List<T> ts) {
        mDatas.clear();
        if (ts != null) {
            mDatas.addAll(ts);

        }
        notifyDataSetChanged();
    }

    public void setDatas(List<T> ts) {
        mDatas.clear();
        if (ts != null) {
            mDatas.addAll(ts);
        }
    }

    /**
     * 清除当前数据
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }
}
