package com.translatmaster.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class BaseActivity extends Activity {
    public Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
