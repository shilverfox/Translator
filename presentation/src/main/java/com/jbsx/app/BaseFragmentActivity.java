package com.jbsx.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.jbsx.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lijian15 on 2016/12/14.
 */

public class BaseFragmentActivity extends FragmentActivity {
    public Context mContext;
    protected EventBus eventBus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventBus = EventBus.getDefault();
        eventBus.register(this);
        mContext = this;

        // 设置沉浸式状态栏
        StatusBarUtil.setStatusBar(getWindow());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        eventBus.unregister(this);
    }

    /**
     * Subscribe Event bus
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEvent event) {/* Do something */};
}
