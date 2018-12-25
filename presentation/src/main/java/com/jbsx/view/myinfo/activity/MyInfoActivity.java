package com.jbsx.view.myinfo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.data.ITransKey;
import com.jbsx.utils.RecyclerViewHelper;
import com.jbsx.utils.Router;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.login.ResetPasswordActivity;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.adapter.MyInfoAdapter;
import com.jbsx.view.myinfo.data.MyInfoConst;
import com.jbsx.view.myinfo.entity.MyInfoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * 个人信息页面
 */
public class MyInfoActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;
    private ImageView mIvHead;
    private RecyclerView mRvMyInfoList;
    private MyInfoAdapter mAdapter;
    private View mViewHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_activity);

        findViews();
        initTitleBar();
        initAdapter();
        initMyInfoList();
        registerEvents();
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
        mIvHead = findViewById(R.id.iv_my_info_activity_head);
        mRvMyInfoList = findViewById(R.id.rv_my_info_activity_list);
        mViewHead = findViewById(R.id.layout_change_head_icon);
    }

    private void initAdapter() {
        mAdapter = new MyInfoAdapter(mContext, R.layout.myinfo_fragment_item);

        // 制造数据
        List<MyInfoItem> items = new ArrayList<MyInfoItem>();
        for (int i = 0; i < 2; i++) {
            MyInfoItem infoItem = new MyInfoItem(i, MyInfoConst.MY_INFO_NAMES[i]);
//            infoItem.setTo(MyInfoConst.MY_INFO_NAVIGATIONS[i]);
            infoItem.setRightDeliver(true);
            infoItem.setNeedLogin(true);
            items.add(infoItem);
        }

        mAdapter.setList(items);
    }

    private void initMyInfoList() {
        mRvMyInfoList.setAdapter(mAdapter);
        mRvMyInfoList.setHasFixedSize(true);
        mRvMyInfoList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvMyInfoList.addItemDecoration(RecyclerViewHelper.getDivider(mContext, true));
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setCenterTitle("账户信息");
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });
    }

    private void registerEvents() {
        mViewHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhotoPicker.builder()
                        //设置图片选择数量
                        .setPhotoCount(1)
                        //取消选择时点击图片浏览
                        .setPreviewEnabled(false)
                        //开启裁剪
                        .setCrop(true)
                        //设置裁剪比例(X,Y)
                        .setCropXY(1, 1)
                        //设置裁剪界面标题栏颜色，设置裁剪界面状态栏颜色
                        .setCropColors(R.color.app_word_red, R.color.app_word_red)
                        .start(MyInfoActivity.this);
            }
        });

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
    }

    private void gotoView(int id) {
        if (id == 0) {
            // 修改昵称
            handleGotoModifyInfo(LoginHelper.getInstance().getUserId());
        } else if (id == 1) {
            Router.getInstance().open(ResetPasswordActivity.class, MyInfoActivity.this);
        }
    }

    private void handleGotoModifyInfo(String oldInfo) {
        Bundle bundle = new Bundle();
        bundle.putString(ITransKey.KEY, oldInfo);

        Router.getInstance().open(ModifyUserInfoActivity.class, MyInfoActivity.this, bundle);
    }

    private void onBackEvent() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 选择返回
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
        }

        // 拍照功能或者裁剪后返回
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.CROP_CODE) {
            Uri uri = Uri.fromFile(new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH)));
            uploadIcon(uri);
            ImageLoader.displayImage(uri, R.drawable.default_head, mIvHead, true);
        }
    }

    private void uploadIcon(Uri uri) {

    }
}
