package com.jbsx.view.myinfo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.app.domain.util.ParseUtil;
import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.MessageTools;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.setting.data.AboutData;

/**
 * 更改个人信息页面
 */
public class ModifyUserInfoActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;
    private EditText mEtUserInfo;

    private MyInfoUserCase mUserCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_user_info_activity);

        initUserCase();
        findViews();
        initTitleBar();
        registerEvents();
    }

    private void initUserCase() {
        mUserCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
        mEtUserInfo = findViewById(R.id.et_modify_user_info);
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setCenterTitle("更改名字");
    }

    private void registerEvents() {
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });

        mTopBarLayout.setRightButton("保存", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleModify();
            }
        });
    }

    private void onBackEvent() {
        finish();
    }

    private void handleModify() {
        String nickName = mEtUserInfo.getText().toString();
        mUserCase.requestModifyUserInfo(LoginHelper.getInstance().getUserToken(),
                nickName, new BaseRequestCallback() {
            @Override
            public void onRequestFailed(BaseDomainData data) {
                handleModifyInfoFailed(data);
            }

            @Override
            public void onRequestSuccessful(String data) {
                handleModifyInfoSuccessful(data);
            }

            @Override
            public void onNetError() {

            }
        });
    }

    private void handleModifyInfoSuccessful(String data) {
        AboutData aboutData = ParseUtil.parseData(data, AboutData.class);
        if (aboutData != null && aboutData.getPayload() != null) {
        }
    }

    private void handleModifyInfoFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
    }

}
