package com.jbsx.view.myinfo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.view.myinfo.contact.MyViewHistoryContact;
import com.jbsx.view.myinfo.presenter.MyViewHistoryPresenter;

public class MyViewHistoryFragment extends BaseFragment implements MyViewHistoryContact.View {
    private View mRootView;
    private MyViewHistoryContact.Presenter mPresenter;

    public MyViewHistoryFragment() {
        // Required empty public constructor
    }

    public static MyViewHistoryFragment newInstance() {
        return new MyViewHistoryFragment();
    }

    @Override
    public void createPresenter() {
        MyInfoUserCase userCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new MyViewHistoryPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.main_page_fragment, null, false);
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
