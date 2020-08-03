package com.jbsx.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * 通用的ViewHolder
 */
public class UniversalViewHolder2 extends RecyclerView.ViewHolder {
    private SparseArray<View> mView;// 界面控件缓存
    private View mConvertView;// 当前界面
    private Context mContext;

    public UniversalViewHolder2(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mView = new SparseArray<View>();
        mConvertView = itemView;
        mConvertView.setTag(this);

    }

    /**
     * 获取重新生成或者缓存的Holder
     */
    public static UniversalViewHolder2 getHolder(View convertView) {
        if (convertView == null || convertView.getTag() == null) {//防止一个item有多种view，导致getTag==null
            return new UniversalViewHolder2(convertView);
        } else {
            UniversalViewHolder2 holder = (UniversalViewHolder2) convertView.getTag();
            return holder;
        }
    }

    /**
     * 获取当前的界面
     */
    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过id获取控件
     */
    public View getViewById(int viewId) {
        View view = mView.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mView.put(viewId, view);
        }
        return view;
    }

    /**
     * 设置文本
     */
    public UniversalViewHolder2 setText(int viewId, String text) {
        ((TextView) getViewById(viewId)).setText(text);
        return this;
    }

    public UniversalViewHolder2 setTextColor(int viewId, int colorId) {
        ((TextView) getViewById(viewId)).setTextColor(mContext.getResources().getColor(colorId));
        return this;
    }

    public UniversalViewHolder2 setBackGround(int viewId, int bgId) {
        getViewById(viewId).setBackgroundResource(bgId);
        return this;
    }

}
