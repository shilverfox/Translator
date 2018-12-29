package com.jbsx.view.myinfo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.data.ITransKey;
import com.jbsx.data.RequestTokenData;
import com.jbsx.utils.FileUtils;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.RecyclerViewHelper;
import com.jbsx.utils.Router;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.login.ModifyPasswordActivity;
import com.jbsx.view.login.ResetPasswordActivity;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.adapter.MyInfoAdapter;
import com.jbsx.view.myinfo.data.MyInfoConst;
import com.jbsx.view.myinfo.data.ReloadUserInfo;
import com.jbsx.view.myinfo.entity.MyInfoItem;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

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
    private MyInfoUserCase mUserCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_activity);

        initUserCase();
        findViews();
        initTitleBar();
        initAdapter();
        initMyInfoList();
        initUserInfoView();
        registerEvents();
    }

    private void initUserCase() {
        mUserCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
        mIvHead = findViewById(R.id.iv_my_info_activity_head);
        mRvMyInfoList = findViewById(R.id.rv_my_info_activity_list);
        mViewHead = findViewById(R.id.layout_change_head_icon);
    }

    private void initUserInfoView() {
        String iconUrl = LoginHelper.getInstance().getUserHead();
        ImageLoader.displayImage(iconUrl, mIvHead, R.drawable.default_head, true);
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
            handleGotoModifyInfo(LoginHelper.getInstance().getUserNickName());
        } else if (id == 1) {
            Router.getInstance().open(ModifyPasswordActivity.class, MyInfoActivity.this);
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
            File file = new File(data.getStringExtra(PhotoPicker.KEY_CAMEAR_PATH));
            Uri uri = Uri.fromFile(file);

            uploadIcon(file);
            ImageLoader.displayImage(uri, R.drawable.default_head, mIvHead, true);
        }
    }

    private void uploadIcon(final File file) {
        if (file != null && file.exists()) {
            showUploadHeadLoadingBar();

            mUserCase.requestUploadUserHead(LoginHelper.getInstance().getUserToken(),
                    LoginHelper.getInstance().getUserId(), "icon", file, new BaseRequestCallback() {
                        @Override
                        public void onRequestFailed(BaseDomainData data) {
                            MessageTools.showErrorMessage(data);
                            removeUploadHeadLoadingBar();
                        }

                        @Override
                        public void onRequestSuccessful(String data) {
                            handleRequestTokenSuccess(data, FileUtils.getBytesFromFile(file));
                        }

                        @Override
                        public void onNetError() {
                            removeUploadHeadLoadingBar();
                        }
                    });
        }
    }

    /**
     * 1.获取要上传到7牛的token
     *
     * @param responseData
     * @param uploadData 要上传的数据
     */
    private void handleRequestTokenSuccess(String responseData, byte[] uploadData) {
        RequestTokenData requestTokenData = ParseUtil.parseData(responseData, RequestTokenData.class);
        if (requestTokenData != null) {
            String token = requestTokenData.getPayload().getToken();
            String key = requestTokenData.getPayload().getKey();

            handleUploadIconToServer(key, token, uploadData);
        }
    }



    /**
     * 2.用7牛sdk上传到7牛
     */
    private void handleUploadIconToServer(String key, String token, byte[] uploadData) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(uploadData, key, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (response != null) {
                            JSONObject imageInfo = null;

                            try {
                                imageInfo = response.getJSONObject("imageInfo");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (imageInfo != null) {
                                try {
                                    notifyJbServerImageInfo(response.getString("key"),
                                            response.getString("name"),
                                            response.getString("size"),
                                            imageInfo.getString("format"),
                                            imageInfo, response.getString("bucket"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            // 上传7牛失败
                            removeUploadHeadLoadingBar();
                        }

                    }
                }, null);
    }

    /**
     * 3.把上传到7牛的结果告诉jb的服务端
     */
    private void notifyJbServerImageInfo(final String id, String name, String size, String format,
                                         JSONObject fileObject, String bucket) {
        mUserCase.requestUpload7NiuResult(LoginHelper.getInstance().getUserToken(), id, name, size,
                format, fileObject, bucket, new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        handleNotifyJbFailed(data);
                        removeUploadHeadLoadingBar();
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        handleNotifyJbSuccess(id);
                    }

                    @Override
                    public void onNetError() {
                        removeUploadHeadLoadingBar();
                    }
                });
    }

    private void handleNotifyJbSuccess(String imageId) {
        handleModifyUserInfo(imageId);
    }

    private void handleNotifyJbFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }

    /**
     * 4 更新用户头像信息
     *
     * @param imageId
     */
    private void handleModifyUserInfo(String imageId) {
        mUserCase.requestModifyUserInfo(LoginHelper.getInstance().getUserToken(),
                LoginHelper.getInstance().getUserId(), null, imageId,
                new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        MessageTools.showErrorMessage(data);
                        removeUploadHeadLoadingBar();
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        handleModifyUserInfoSuccess(data);
                    }

                    @Override
                    public void onNetError() {
                        removeUploadHeadLoadingBar();
                    }
                });
    }

    private void handleModifyUserInfoSuccess(String data) {
        ShowTools.toast("头像已更新");
        removeUploadHeadLoadingBar();

        EventBus.getDefault().post(new ReloadUserInfo());
    }

    private void showUploadHeadLoadingBar() {
        ProgressBarHelper.addProgressBar(mIvHead);
    }

    private void removeUploadHeadLoadingBar() {
        ProgressBarHelper.removeProgressBar(mIvHead);
    }
}
