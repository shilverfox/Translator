package com.jbsx.view.main.view;

import android.content.Context;
import android.os.Bundle;

import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.search.entity.SearchEvent;
import com.jbsx.view.search.view.SearchResultAdapter;
import com.jbsx.view.search.view.SearchResultView;

public class RepertoryListView extends SearchResultView {
    private RepertoryListAdapter mAdapter;

    public static RepertoryListView newInstance(SearchEvent argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        RepertoryListView contentFragment = new RepertoryListView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new RepertoryListAdapter(mContext);
        return mAdapter;
    }
}
