package com.jbsx.view.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;

public class WebActivity extends BaseFragmentActivity {
	private WebFragment mWebFragment;
	private TitleBar mTitleBar;

	private String mUrl;
	private String mTitle;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_activity);

		getDataFromIntent();
		initTitleBar();
		createFragment();
	}

	private void initTitleBar() {
		mTitleBar = (TitleBar) findViewById(R.id.layout_title_bar_container);

		if (mTitle != null) {
			mTitleBar.setCenterTitle(mTitle);
		}

		mTitleBar.showBackButton(true);
		mTitleBar.setBackButton(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				handleBackKey();
			}
		});
	}

	private void getDataFromIntent() {
		Intent intent = getIntent();
		if (intent != null) {
			Bundle extra = intent.getExtras();

			if (extra != null) {
				mUrl = extra.getString(WebHelper.WEB_URL_KEY);
				mTitle = extra.getString(WebHelper.WEB_TITLE_KEY);
			}
		}
	}

	private void createFragment() {
		FragmentManager fm = getSupportFragmentManager();
		mWebFragment = WebFragment.newInstance();

		if (mWebFragment != null) {
			mWebFragment.setHtmlData(mUrl);
			fm.beginTransaction().add(R.id.mWebFragment, mWebFragment).commitAllowingStateLoss();
		} else {
			// 无对应Fragment，直接关闭
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			handleBackKey();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void handleBackKey() {
		if (mWebFragment != null) {
			mWebFragment.handleBackKeyEvent();
		}
	}
}
