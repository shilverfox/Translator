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
import com.jbsx.view.myinfo.adapter.MyInfoBigImageAdapter;
import com.jbsx.view.myinfo.entity.MyInfoBigItem;
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

	private MyInfoBigImageAdapter mAdapter;
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

		mTitleBar.setBackButton(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	private void initData() {
		mAdapter = new MyInfoBigImageAdapter(this, R.layout.myinfo_fragment_big_item);
		linearLayoutManager = new LinearLayoutManager(mContext);

		// 制造数据
		List<MyInfoBigItem> items = new ArrayList<MyInfoBigItem>();
		for (int i = 0; i < SettingConst.ABOUT_APP.length; i++) {
			MyInfoBigItem item = new MyInfoBigItem();
			item.setTitle(SettingConst.ABOUT_APP[i][SettingConst.POSITION_ABOUT_TITLE]);
			item.setAuthor(SettingConst.ABOUT_APP[i][SettingConst.POSITION_ABOUT_AUTHOR]);
			item.setSummary(SettingConst.ABOUT_APP[i][SettingConst.POSITION_ABOUT_SUMMARY]);
//			item.setImgId(Integer.parseInt(SettingConst.ABOUT_APP[i][SettingConst.POSITION_ABOUT_IMG_ID]));
			item.setImgUrl(SettingConst.ABOUT_APP[i][SettingConst.POSITION_ABOUT_IMG_URL]);
			item.setToUrl(SettingConst.ABOUT_APP[i][SettingConst.POSITION_ABOUT_TO_URL]);

			items.add(item);
		}

		mAdapter.setList(items);
	}

	private void gotoView(MyInfoItem item) {
		if (item != null) {
			String url = "";
			switch (item.getId()) {
				case 1:
					url = "http://jbsx.china1896.com/jbsx/src/forapp/teamGp.html";
					break;
				case 2:
					url = "http://jbsx.china1896.com/jbsx/src/forapp/article1.html";
					break;
				case 3:
					url = "http://jbsx.china1896.com/jbsx/src/forapp/article2.html";
					break;
			}

			String title = TextUtils.isEmpty(item.getTitle()) ? "关于我们" : item.getTitle();
			if (!TextUtils.isEmpty(url)) {
				WebHelper.openWeb(mContext, url, title);
			}
		}

	}
}
