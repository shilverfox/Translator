package com.jbsx.view.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.login.util.LoginUtils;

/**
 * 修改密码
 */
public class ModifyPasswordActivity extends BaseFragmentActivity {
    private LinearLayout mLayoutRoot;
    private TitleBar mTopBarLayout;

    private Button mBtnLogin;
    private EditText mEditOldPassword;
    private EditText mEditNewPassword;
    private EditText mEditConfirmNewPassword;

    private LoginViewUserCase mUserCase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password_activity);

        initUserCase();
        findViews();
        registEvents();
        initViews();
    }

    private void initUserCase() {
        mUserCase = new LoginViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
    }

    private void initViews() {
        mTopBarLayout.setCenterTitle("修改密码");
        mTopBarLayout.showBackButton(true);
        LoginUtils.setNormalButtonStatus(mContext, mBtnLogin, true);
    }

    private void findViews() {
        mLayoutRoot = (LinearLayout) findViewById(R.id.modify_pwd_root);
        mTopBarLayout = (TitleBar) findViewById(R.id.layout_title_bar_container);
        mBtnLogin =  findViewById(R.id.login_jd_login);
        mEditOldPassword =  findViewById(R.id.ev_modify_pwd_old);
        mEditNewPassword =  findViewById(R.id.ev_modify_pwd_new);
        mEditConfirmNewPassword =  findViewById(R.id.ev_modify_pwd_confirm_new);
    }

    private void registEvents() {
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleModifyPassword();
            }
        });
    }

    private void handleModifyPassword() {
        String newConfirmPwd = mEditConfirmNewPassword.getText().toString();
        String newPwd = mEditNewPassword.getText().toString();

        if (TextUtils.isEmpty(newConfirmPwd) || TextUtils.isEmpty(newPwd)) {
            ShowTools.toast("密码不能为空");
            return;
        }

        if (!newPwd.equals(newConfirmPwd)) {
            ShowTools.toast("两次输入的密码不同");
            return;
        }

        if (newPwd.equals(newConfirmPwd)) {
            ProgressBarHelper.addProgressBar(mLayoutRoot);
            mUserCase.modifyPassword(LoginHelper.getInstance().getUserToken(),
                    LoginHelper.getInstance().getUserId(), mEditOldPassword.getText().toString(),
                    mEditNewPassword.getText().toString(), new BaseRequestCallback() {
                        @Override
                        public void onRequestFailed(BaseDomainData data) {
                            handleModifyFailed(data);
                        }

                        @Override
                        public void onRequestSuccessful(String data) {
                            handleModifySuccess();
                        }

                        @Override
                        public void onNetError() {
                            ProgressBarHelper.removeProgressBar(mLayoutRoot);
                        }
                    });
        }
    }

    private void handleModifySuccess() {
        ShowTools.toast("密码修改成功");
        ProgressBarHelper.removeProgressBar(mLayoutRoot);
        finish();
    }

    private void handleModifyFailed(BaseDomainData data) {
        MessageTools.showErrorMessage(data);
        ProgressBarHelper.removeProgressBar(mLayoutRoot);
    }

    private void onBackEvent() {
        finish();
    }
}
