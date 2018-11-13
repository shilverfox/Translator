package com.translatmaster.view.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.app.BaseActivity;
import com.translatmaster.customview.TitleBar;
import com.translatmaster.utils.StatisticsReportUtil;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity {
	private TextView mTxtVersion;
	private ImageButton mBtnGood;
	private ImageButton mBtnBad;
	private TitleBar title;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		
		initViews();
		initEvent();
	}
	
	private void initViews() {
		mTxtVersion = (TextView) findViewById(R.id.txt_myinfo_about_version);
		mBtnGood = (ImageButton) findViewById(R.id.btn_myinfo_about_good);
		mBtnBad = (ImageButton) findViewById(R.id.btn_myinfo_about_bad);
		title = (TitleBar) findViewById(R.id.layout_title_bar_container);

		// 设置Top bar 标题
		title.showBackButton(true);
		title.setCenterTitle("关于我们");

		// 版本号
		mTxtVersion.setText(StatisticsReportUtil.getSimpleVersionName());
		mTxtVersion.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				mTxtVersion.setText(StatisticsReportUtil.getSimpleVersionName());
			}
		});
	}

	private void initEvent(){
		mBtnGood.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onGoodBtnClick();
			}
		});
		mBtnBad.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBadBtnClick();
			}
		});
		title.setBackButton(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void onGoodBtnClick() {
		// 点赞 3.9修改为求好评的入口 By Li Jian
//		CsdjUtil.gotoAppMarket(mContext);
	}
	
	private void onBadBtnClick() {
		// 吐槽
//		Router.getInstance().open(MyInfoFeedBackActivity.class, this,
//				new Bundle());
	}
}
