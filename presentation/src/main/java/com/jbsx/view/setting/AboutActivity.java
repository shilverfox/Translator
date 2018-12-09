package com.jbsx.view.setting;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jbsx.R;
import com.jbsx.app.BaseActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.FileUtils;
import com.jbsx.utils.RecyclerViewHelper;
import com.jbsx.utils.StatisticsReportUtil;
import com.jbsx.view.myinfo.adapter.MyInfoAdapter;
import com.jbsx.view.myinfo.entity.MyInfoItem;
import com.jbsx.view.setting.data.SettingConst;
import com.jbsx.view.web.WebHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于我们
 */
public class AboutActivity extends BaseActivity {
	private TextView mTxtVersion;
	private TitleBar mTitleBar;

	private MyInfoAdapter mAdapter;
	private RecyclerView mRvContent;
	private LinearLayoutManager linearLayoutManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);

		initData();
		initViews();
		initEvent();
	}
	
	private void initViews() {
		mTxtVersion = findViewById(R.id.txt_myinfo_about_version);
		mTitleBar = findViewById(R.id.layout_title_bar_container);
		mRvContent = (RecyclerView) findViewById(R.id.rv_about_body);

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
		mRvContent.setAdapter(mAdapter);
		mRvContent.setHasFixedSize(true);
		mRvContent.setLayoutManager(linearLayoutManager);
		mRvContent.addItemDecoration(RecyclerViewHelper.getDivider(mContext, true));

		mAdapter.setOnMyItemClickListener(new MyInfoAdapter.OnMyItemClickListener() {
			@Override
			public void onClick(int position) {
				MyInfoItem item = mAdapter.getDatas().get(position);
				gotoView(item);
			}
		});

		mTitleBar.setBackButton(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initData() {
		mAdapter = new MyInfoAdapter(this, R.layout.myinfo_fragment_item);
		linearLayoutManager = new LinearLayoutManager(mContext);

		// 制造数据
		List<MyInfoItem> items = new ArrayList<MyInfoItem>();
		items.add(new MyInfoItem(1, "制作团队", true));
		items.add(new MyInfoItem(2, "让\"绝版\"活在当下——傅谨", true));
		items.add(new MyInfoItem(3, "与三老的最后一面——代跋（柴俊为）", true));

		mAdapter.setList(items);
	}

	private void gotoView(MyInfoItem item) {
		if (item != null) {
			String url = "";
			switch (item.getId()) {
				case 1:
					url = "http://www.baidu.com";
					break;
				case 2:
					url = "http://www.baidu.com";
					break;
				case 3:
					url = "http://www.baidu.com";
					break;
			}

			String title = TextUtils.isEmpty(item.getTitle()) ? "关于我们" : item.getTitle();
			if (!TextUtils.isEmpty(url)) {
				WebHelper.openWeb(mContext, url, title);
			}
		}

	}
}
