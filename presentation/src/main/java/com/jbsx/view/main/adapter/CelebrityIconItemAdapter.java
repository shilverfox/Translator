package com.jbsx.view.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.adapter.UniversalAdapter2;
import com.jbsx.adapter.UniversalViewHolder2;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.Celebrities;

/**
 * 名家头像列表项
 * 用于首页名家列表楼层
 */
public class CelebrityIconItemAdapter extends UniversalAdapter2<Celebrities> {
    private OnMyItemClickListener onMyItemClickListener;
    private int mFocusedPosition;
    private int mCurrentPosition;

    public CelebrityIconItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public void setFocusedPosition(int position) {
        mFocusedPosition = position;
    }

    @Override
    public void convert(final UniversalViewHolder2 holder, final Celebrities celebrityData, final int position) {
        if (celebrityData == null) {
            return;
        }

        mCurrentPosition = position;

        ImageView icon = (ImageView) holder.getViewById(R.id.tv_celebrity_icon_item_head);
        ImageLoader.displayImage(celebrityData.getImageUrl(), icon);

        TextView name = (TextView) holder.getViewById(R.id.tv_celebrity_icon_item_name);
        name.setText(celebrityData.getName());
//        name.setTextColor(getTextColor());
//        name.setBackgroundResource(getBackgroundColor());

        holder.getViewById(R.id.layout_celebrity_icon_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onMyItemClickListener != null) {
                    onMyItemClickListener.onClick(position);
                }

                mFocusedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    private int getTextColor() {
        return (isSelected() ? 0xff47b34f : 0xff666666);
    }

    private int getBackgroundColor() {
        return isSelected() ? R.drawable.shape_round_47b34f : R.drawable.shape_round_f8f8f8;
    }

    private boolean isSelected() {
        return mFocusedPosition == mCurrentPosition;
    }

    public interface OnMyItemClickListener {
        void onClick(int id);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }
}
