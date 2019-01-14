package com.jbsx.view.main.view;

import android.content.Context;
import android.view.View;

import com.jbsx.customview.listFragment.CommonListFragmentViewHolder;
import com.jbsx.view.search.view.SearchResultAdapter;
import com.jbsx.view.search.view.SearchResultHolder;

public class RepertoryListAdapter extends SearchResultAdapter {
    public RepertoryListAdapter(Context context) {
        super(context);
    }

    @Override
    public CommonListFragmentViewHolder getViewHolder(View rootView) {
        RepertoryListHolder holder = new RepertoryListHolder(getContext(), rootView);
        holder.showGuwen(false);

        return holder;
    }
}
