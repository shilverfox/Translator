package com.jbsx.player.adapter;

import android.content.Context;

import com.jbsx.R;

/**
 * 横屏状态的选集列表
 * Created by lijian on 2018/11/24.
 */

public class EpisodeLandScapeItemAdapter extends EpisodePortraitItemAdapter {

    public EpisodeLandScapeItemAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public int getTextColor() {
        return 0xffffffff;
    }

    @Override
    public int getBackgroundColor() {
        return isSelected() ? R.drawable.shape_land_episode_select_bg
                : R.drawable.shape_land_episode_normal_bg;
    }
}
