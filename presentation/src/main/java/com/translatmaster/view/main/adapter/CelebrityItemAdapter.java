package com.translatmaster.view.main.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.adapter.UniversalAdapter2;
import com.translatmaster.adapter.UniversalViewHolder2;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.utils.image.ImageLoader;
import com.translatmaster.view.main.entity.Album;
import com.translatmaster.view.main.entity.Celebrities;
import com.translatmaster.view.main.entity.CelebrityData;
import com.translatmaster.view.main.entity.SpecialAlbums;

/**
 * 名家列表项
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
