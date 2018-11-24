package com.jbsx.player.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.adapter.UniversalAdapter2;
import com.jbsx.adapter.UniversalViewHolder2;
import com.jbsx.view.main.entity.Celebrities;
import com.jbsx.view.main.entity.Single;

/**
 * 竖屏状态的选集列表项
 */
public class EpisodePortraitItemAdapter extends UniversalAdapter2<Single> {
    private OnMyItemClickListener onMyItemClickListener;
    private int mFocusedPosition;
    private int mCurrentPosition;

    public EpisodePortraitItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    public void setFocusedPosition(int position) {
        mFocusedPosition = position;
    }

    @Override
    public void convert(final UniversalViewHolder2 holder, final Single single, final int position) {
        if (single == null) {
            return;
        }

        mCurrentPosition = position;

        TextView name = (TextView) holder.getViewById(R.id.tv_episode_item_name);

        // 没有0集
        name.setText(position + 1 + "");
        name.setTextColor(getTextColor());
        name.setBackgroundResource(getBackgroundColor());

        holder.getViewById(R.id.tv_episode_item_name).setOnClickListener(new View.OnClickListener() {
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

    public int getTextColor() {
        return (isSelected() ? 0xffb83438 : 0xff666666);
    }

    public int getBackgroundColor() {
        return isSelected() ? R.drawable.shape_por_episode_select_bg
                : R.drawable.shape_por_episode_normal_bg;
    }

    public boolean isSelected() {
        return mFocusedPosition == mCurrentPosition;
    }

    public interface OnMyItemClickListener {
        void onClick(int id);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.onMyItemClickListener = onMyItemClickListener;
    }
}
