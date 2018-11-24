package com.jbsx.view.start;

import android.os.Bundle;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseActivity;
import com.jbsx.view.main.activity.MainActivity;

import gr.net.maroulis.library.EasySplashScreen;

/**
 * Created by lijian on 2018/11/24.
 */

public class SplashActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View easySplashScreenView = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(4000)
                .withBackgroundResource(android.R.color.holo_red_light)
                .withHeaderText("Header")
                .withFooterText("Copyright 2016")
                .withBeforeLogoText("My cool company")
                .withLogo(R.mipmap.ic_launcher)
                .withAfterLogoText("Some more details")
                .create();

        setContentView(easySplashScreenView);
    }
}
