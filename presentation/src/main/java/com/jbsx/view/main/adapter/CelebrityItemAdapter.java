package com.jbsx.view.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.adapter.UniversalAdapter2;
import com.jbsx.adapter.UniversalViewHolder2;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.main.entity.Album;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.CelebrityData;
import com.jbsx.view.main.entity.SpecialAlbums;

/**
 * 名家列表项
 * 用于片库中显示名家姓名
 */
public class CelebrityItemAdapter extends UniversalAdapter2<Celebrities> {
    private OnMyItemClickListener onMyItemClickListener;
    private int mFocusedPosition;
    private int mCurrentPosition;

    public CelebrityItemAdapter(Context context, int layoutId) {
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

        TextView name = (TextView) holder.getViewById(R.id.tv_celebrity_item_name);
        name.setText(celebrityData.getName());
        name.setTextColor(getTextColor());
        name.setBackgroundResource(getBackgroundColor());

        holder.getViewById(R.id.tv_celebrity_item_name).setOnClickListener(new View.OnClickListener() {
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
        return (isSelected() ? 0xffb83438 : 0xff666666);
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
