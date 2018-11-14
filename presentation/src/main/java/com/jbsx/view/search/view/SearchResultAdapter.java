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

    public SearchResultAdapter(Context context) {
        super(context);
    }

    @Override
    public int getViewId() {
        return R.layout.album_grid_1_item;
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        return new SearchResultHolder(getContext(), rootView);
    }
}
