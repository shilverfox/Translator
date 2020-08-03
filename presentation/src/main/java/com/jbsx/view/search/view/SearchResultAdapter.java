package com.jbsx.view.search.view;

import android.content.Context;
import android.view.View;

import com.jbsx.R;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;

/**
 * Created by lijian15 on 2017/9/4.
 */

public class SearchResultAdapter extends CommonListFragmentAdapter {
    /** 是否显示艺术顾问 */
    private boolean mShowGuwen;

    public SearchResultAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.album_grid_1_item;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        SearchResultHolder holder = new SearchResultHolder(getContext(), rootView);
        holder.showGuwen(mShowGuwen);

        return holder;
    }

    public void showGuwen(boolean show) {
        mShowGuwen = show;
    }
}
