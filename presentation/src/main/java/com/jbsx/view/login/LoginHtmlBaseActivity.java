package com.jbsx.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.view.login.data.HtmlModal;
import com.jbsx.view.login.data.LoginConstData;
import com.jbsx.view.login.view.fragment.html.LoginHtmlBaseFragment;
import com.jbsx.view.login.view.fragment.html.LoginHtmlHelper;

/**
 * 需要用html显示的UI，比如账号风控，微信京东账号绑定，找回密码等
 * 3.6增加
 *
 * Created by lijian15 on 2016/11/15.
 */
public class LoginHtmlBaseActivity extends BaseFragmentActivity {

	private HtmlModal mHtmlModal;
	private int mRequestSrc;
	private LoginHtmlBaseFragment mLoginHtmlBaseFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_html_base_activity);

		getDataFromIntent();
		createFragment();
	}

	private void getDataFromIntent() {
		Intent intent = getIntent();

		if (intent != null) {
			Bundle extra = intent.getExtras();
			Parcelable model = null;

			if (extra != null) {
				model = extra.getParcelable(LoginConstData.INTENT_HTML_MODEL);
			}

			if (model != null) {
				mHtmlModal = (HtmlModal)model;
				mRequestSrc = mHtmlModal.getRequestSource();
			}
		}
	}

	private void createFragment() {
		FragmentManager fm = getSupportFragmentManager();
		mLoginHtmlBaseFragment = LoginHtmlHelper.getInstance().getHtmlFragment(mRequestSrc);

		if (mLoginHtmlBaseFragment != null) {
			mLoginHtmlBaseFragment.setHtmlData(mHtmlModal);
			fm.beginTransaction().add(R.id.layout_login_html_root, mLoginHtmlBaseFragment)
					.commitAllowingStateLoss();
		} else {
			// 无对应Fragment，直接关闭
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (mLoginHtmlBaseFragment != null) {
				mLoginHtmlBaseFragment.handleBackKeyEvent();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
