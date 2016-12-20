package com.translatmaster.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class BaseFragment extends Fragment {

    public Context mContext;

    public BaseFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this.getActivity();
    }
}
