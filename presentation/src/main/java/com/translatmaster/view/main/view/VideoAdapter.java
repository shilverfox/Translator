package com.translatmaster.view.main.view;

import android.content.Context;
import android.view.View;

import com.translatmaster.R;
import com.translatmaster.customview.listFragment.CommonListFragmentAdapter;
import com.translatmaster.customview.listFragment.CommonListFragmentViewHolder;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class VideoAdapter extends CommonListFragmentAdapter {

    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.album_grid_1_item;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        return new VideoViewHolder(getContext(), rootView);
    }
}
