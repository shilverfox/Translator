package com.jbsx.view.search.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.SearchUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.view.search.contact.SearchContact;
import com.jbsx.view.search.presenter.SearchPresenter;

/**
 * 搜索中间页，输入搜索词及显示搜索历史、热词
 */
public class SearchFragment extends BaseFragment implements SearchContact.View {
    private View mRootView;
    private SearchContact.Presenter mPresenter;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void createPresenter() {
        SearchUserCase userCase = new SearchUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new SearchPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.search_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();

        return mRootView;
    }

    private void initViews() {

    }

    private void initEvents() {

    }
}
