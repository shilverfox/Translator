package com.jbsx.view.search.presenter;

import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.interactor.SearchUserCase;
import com.jbsx.view.myinfo.contact.MyViewHistoryContact;
import com.jbsx.view.search.contact.SearchContact;

public class SearchPresenter implements SearchContact.Presenter {
    private SearchContact.View mView;
    private SearchUserCase mUserCase;

    public SearchPresenter(SearchContact.View view, SearchUserCase userCase) {
        mView = view;
        mUserCase = userCase;
    }

    @Override
    public void start() {

    }

    @Override
    public void requestHotWords() {

    }

    @Override
    public void loadSearchHistory() {

    }
}
