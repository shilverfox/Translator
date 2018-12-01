package com.jbsx.view.setting;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.customview.dialog.JDDJDialogFactory;
import com.jbsx.utils.FileUtils;
import com.jbsx.utils.RecyclerViewHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.myinfo.adapter.MyInfoAdapter;
import com.jbsx.view.myinfo.entity.MyInfoItem;
import com.jbsx.view.setting.data.SettingConst;

import java.util.ArrayList;
import java.util.List;

/**
 * 设置界面
 */
public class SettingsActivity extends BaseFragmentActivity {
    private RecyclerView mLvContent;
    private TitleBar title;
    private LinearLayout root_view;
    private MyInfoAdapter mAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        initData();
        initView();
        initEvent();
    }

    private void initView() {
        title = (TitleBar) findViewById(R.id.layout_title_bar_container);
        mLvContent = (RecyclerView) findViewById(R.id.lv_myinfo_body);
        root_view = (LinearLayout) findViewById(R.id.root_view);
        title.setCenterTitle("设置");
        title.showBackButton(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initEvent() {
        mLvContent.setAdapter(mAdapter);
        mLvContent.setHasFixedSize(true);
        mLvContent.setLayoutManager(linearLayoutManager);
        mLvContent.addItemDecoration(RecyclerViewHelper.getDivider(mContext));

        mAdapter.setOnMyItemClickListener(new MyInfoAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                MyInfoItem item = mAdapter.getDatas().get(position);
                if (item == null) {
                    return;
                }
                gotoView(item.getId());
            }
        });

        title.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBack();
            }
        });
    }

    private void initData() {
        mAdapter = new MyInfoAdapter(this, R.layout.myinfo_fragment_item);
        linearLayoutManager = new LinearLayoutManager(mContext);

        // 制造数据
        List<MyInfoItem> items = new ArrayList<MyInfoItem>();
        items.add(new MyInfoItem(SettingConst.SETTING_TYPE_CLEAR_CACHE, "清除本地缓存", FileUtils.totalCache(this)));
        MyInfoItem aboutItem = new MyInfoItem(SettingConst.SETTING_TYPE_ABOUT, "关于我们");
        aboutItem.setTo(AboutActivity.class);
        items.add(aboutItem);
//        items.add(new MyInfoItem(SettingConst.SETTING_TYPE_UPDATE, "检查更新"));
        mAdapter.setList(items);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void onClickBack() {
        finish();
    }

    /**
     * 页面跳转
     *
     * @param index
     */
    private void gotoView(final int index) {

        switch (index) {
            case SettingConst.SETTING_TYPE_CLEAR_CACHE:
                cleanCacheClick();
                break;
            case SettingConst.SETTING_TYPE_ABOUT:
//                Router.getInstance().open(MyInfoAboutActivity.class, (Activity) mContext);
                break;
            case SettingConst.SETTING_TYPE_UPDATE:
                handleUpdateCheck();
                break;
        }
    }

    private void handleUpdateCheck() {
//        NewUpdateServer.checkUpdateCommon(new NewUpdateServer.UpdateListener() {
//
//            @Override
//            public void onSuccess(boolean isBugly, UpgradeInfo updateInfo, VersionData versionData) {
//                LogTools.e("checkUpdate", "我的点击检测");
//                ProgressBarHelper.removeProgressBar(root_view);
//                if (isBugly) {
//                    NewUpdateServer.showBuglyDialog(MyInfoSettingActivity.this, updateInfo, true);
//                }else {
//                    NewUpdateServer.showDialog(MyInfoSettingActivity.this, versionData, true);
//                }
//            }
//
//            @Override
//            public void onFailed(String error) {
//                LogTools.e("checkUpdate", "我的检测失败");
//                ProgressBarHelper.removeProgressBar(root_view);
//                ShowTools.toastInThread(error);
//            }
//        }, true);
    }

    /**
     * 清除缓存响应事件
     */
    private void cleanCacheClick() {
        JDDJDialogFactory.createDialog(mContext)
                .setTitle("确定清除缓存吗？")
                .setSecondOnClickListener("确定", new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    FileUtils.deleteCache(mContext.getCacheDir());
                                    if (Environment.getExternalStorageState().equals(
                                            Environment.MEDIA_MOUNTED)) {
                                        FileUtils.deleteCache(mContext
                                                .getExternalCacheDir());
                                    }

                                    MainApplicationLike.getInstance().getHanlder().post(new Runnable() {
                                        @Override
                                        public void run() {
                                            mAdapter.getDatas().get(0).setHint("0K");
                                            mAdapter.notifyDataSetChanged();
                                            ShowTools.toast("清除成功!");
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }
                }).setFirstOnClickListener("取消", null).show();
    }
}
