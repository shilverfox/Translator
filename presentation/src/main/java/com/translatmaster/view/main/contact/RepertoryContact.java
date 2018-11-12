package com.translatmaster.view.main.contact;

import com.translatmaster.view.main.entity.CelebrityData;

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
