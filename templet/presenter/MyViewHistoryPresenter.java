package com.translatmaster.view.myinfo.presenter;

import com.app.domain.net.interactor.MyInfoUserCase;
import com.translatmaster.view.myinfo.contact.MyViewHistoryContact;

public class MyViewHistoryPresenter implements MyViewHistoryContact.Presenter {
    private MyViewHistoryContact.View mView;
    private MyInfoUserCase mUserCase;

    public MyViewHistoryPresenter(MyViewHistoryContact.View view, MyInfoUserCase userCase) {
        mView = view;
        mUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestHistoryList(int page) {

    }
}
