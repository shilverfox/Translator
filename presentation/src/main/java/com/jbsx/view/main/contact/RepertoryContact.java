package com.jbsx.view.main.contact;

import com.jbsx.view.main.entity.CelebrityData;

public class RepertoryContact {
    public interface View {
        void createPresenter();
        void drawCelebrities(CelebrityData data);
    }

    public interface Presenter {
        void start();

        void requestCelebrities();
    }
}
