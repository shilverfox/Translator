package com.jbsx.view.start;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.jbsx.R;
import com.jbsx.app.BaseActivity;
import com.jbsx.utils.UiTools;
import com.jbsx.view.main.activity.MainActivity;
import com.jbsx.view.main.util.MainViewUtil;

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
                .withBackgroundResource(R.drawable.logo)
//                .withHeaderText("Header")
//                .withFooterText("Copyright 2016")
//                .withBeforeLogoText("My cool company")
                .withLogo(R.drawable.wave)
//                .withAfterLogoText("Some more details")
                .create();

        FrameLayout layout = new FrameLayout(mContext);
        layout.addView(easySplashScreenView);
        ImageView image = new ImageView(mContext);
        image.setImageResource(R.drawable.hello);

        int logoSize = (int)MainViewUtil.getDimen(R.dimen.splash_logo_width);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(logoSize, logoSize);
        params.gravity = Gravity.CENTER;
        layout.addView(image, params);

        setContentView(layout);
    }
}
