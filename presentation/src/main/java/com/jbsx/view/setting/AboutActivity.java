package com.jbsx.view.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.BaseActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.StatisticsReportUtil;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity {
	private TextView mTxtVersion;
	private TitleBar mTitleBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		
		initViews();
		initEvent();
	}
	
	private void initViews() {
		mTxtVersion = findViewById(R.id.txt_myinfo_about_version);
		mTitleBar = findViewById(R.id.layout_title_bar_container);

		mTitleBar.showBackButton(true);
		mTitleBar.setCenterTitle("关于我们");

		// 版本号
		mTxtVersion.setText(StatisticsReportUtil.getSimpleVersionName());
		mTxtVersion.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				mTxtVersion.setText(StatisticsReportUtil.getSimpleVersionName() + "(" + StatisticsReportUtil.getSoftwareVersionCode() + ")");
			}
		});
	}

	private void initEvent(){
		mTitleBar.setBackButton(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
