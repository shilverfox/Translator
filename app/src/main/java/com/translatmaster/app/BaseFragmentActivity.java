package com.translatmaster.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class BaseFragmentActivity extends FragmentActivity {
    public Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }
}
