package com.translatmaster.view.login.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.translatmaster.R;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.app.MainApplicationLike;
import com.translatmaster.utils.LogTools;
import com.translatmaster.utils.ShowTools;
import com.translatmaster.view.login.customview.LoginUserAgreementView;
import com.translatmaster.view.login.util.LoginUtils;
import com.translatmaster.view.login.view.contact.LoginSimpleInputSmsContract;
import com.translatmaster.view.login.view.presenter.LoginSimpleInputSmsPresenter;

/**
 * 手机号码 + 短信验证码登录
 *
 * Created by lijian15 on 2016/8/17.
 */

public class LoginSimpleInputSmsFragment extends BaseFragment
        implements LoginSimpleInputSmsContract.View {

    private final static String TAG = "LoginSimpleInputSmsFragment";

    /** 重新获取验证码的锁定时间，单位（秒） */
    private final static int FREEZE_TIME = 60;

    /** 倒计时刷新时间间隔，单位（毫秒） */
    private final static long UPDATE_RATE = 1000;

    private final static String BTN_GET_SMS_LABEL = "获取验证码";

    private final static String BTN_GET_SMS_AGAIN = "重新获取";

    private View mRootView;
    private TextView mTxtMobile;
    private EditText mEdtSms;
    private EditText mEditPassword;
    private TextView mTxtGetSms;
    private Button mBtnSubmit;
    private ImageView mBtnDeletePassword;
    private ImageView mBtnPasswordSwitcher;
    private LoginUserAgreementView mViewUserAgment;

    /**
     * 密码显示状态
     */
    private boolean mIsPasswordHidden = false;

    /** 发送短信的手机号 */
    public static String mMobile;

    private boolean mKeepRunning;

    /** 倒计时计时器 */
    private int mCurTimer = FREEZE_TIME;

    public LoginSimpleInputSmsContract.Presenter mPresenter;

    public static LoginSimpleInputSmsFragment getInstance(String mobile) {
        mMobile = mobile;

        return new LoginSimpleInputSmsFragment();
    }

    public LoginSimpleInputSmsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.login_simple_input_sms_fragment, null, false);

        createPresenter();
        findViews();
        registEvents();
        preEvent();

        return mRootView;
    }

    public void createPresenter() {
        LoginViewUserCase userCase = new LoginViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new LoginSimpleInputSmsPresenter(this, userCase);
    }

    /**
     * 初始执行的加载逻辑
     */
    private void preEvent() {
        mTxtMobile.setText(mMobile);
        mBtnSubmit.setText(getSubmitButtonName());
        mViewUserAgment.setVisibility(needShowUserAgreement() ? View.VISIBLE : View.INVISIBLE);
        handleSubmitButtonUI();
    }

    /**
     * 设置倒计时按钮label
     *
     * @param time
     */
    private void setViewTime(final int time) {
        final String startString = "(";
        final String endString = "s)";

        Activity activity = getActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time > 0) {
                        mTxtGetSms.setText(startString + time + endString + BTN_GET_SMS_AGAIN);
                        LoginUtils.setNormalTextStatus(mContext, mTxtGetSms, false);
                    } else {
                        mTxtGetSms.setText(BTN_GET_SMS_LABEL);
                    }
                }
            });
        }
    }

    /**
     * 开始倒计时
     */
    private void startCountDown() {
        mKeepRunning = true;
        mCurTimer = FREEZE_TIME;

        new Thread() {
            public void run() {
                try {
                    while (--mCurTimer >= 0 && mKeepRunning) {
                        setViewTime(mCurTimer);

                        // 间隔
                        Thread.sleep(UPDATE_RATE);
                    }
                } catch (InterruptedException e) {
                    LogTools.e(TAG, e.toString());
                }

                // 停止计时
                stopCountDown();
            }
        }.start();
    }

    /**
     * 倒计时结束
     */
    private void stopCountDown() {
        Activity activity = getActivity();

        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LoginUtils.setNormalTextStatus(mContext, mTxtGetSms, true);
                }
            });
        }

        mKeepRunning = false;
    }

    private void findViews() {
        mEdtSms = (EditText) mRootView.findViewById(R.id.edt_login_ps_sms);
        mTxtGetSms = (TextView) mRootView.findViewById(R.id.edt_login_ps_get_sms);
        mBtnSubmit = (Button) mRootView.findViewById(R.id.edt_login_ps_submit);
        mTxtMobile = (TextView) mRootView.findViewById(R.id.edt_login_ps_mobile);
        mEditPassword = (EditText) mRootView.findViewById(R.id.login_jd_password);
        mBtnDeletePassword = (ImageView) mRootView.findViewById(R.id.delete_password);
        mBtnPasswordSwitcher = (ImageView) mRootView.findViewById(R.id.hide_password);
        mViewUserAgment = (LoginUserAgreementView) mRootView.findViewById(R.id.viewUserAgreement);
    }

    /**
     * 删除密码按键可见性及可操作性
     *
     * @param enable
     */
    public void handleDelPsdUI(boolean enable) {
        mBtnDeletePassword.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
        mBtnDeletePassword.setEnabled(enable);
    }

    /**
     * 控制提交按钮点击状态
     */
    private void handleSubmitButtonUI() {
        boolean userAccountCase = mobileIsValid(mTxtMobile.getText());
        boolean pswCase = mEditPassword.getText().length() > 0;
        boolean smsCase = mEdtSms.getText().length() > 0;
        boolean userAgmentCase = mViewUserAgment.getUserChecked();

        LoginUtils.setNormalButtonStatus(mContext, mBtnSubmit,
                (userAccountCase & pswCase & smsCase && userAgmentCase));
    }

    private boolean mobileIsValid(CharSequence phone) {
        return !TextUtils.isEmpty(phone) && phone.length() >= 11;
    }

    private void registEvents() {
        mBtnDeletePassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditPassword.setText("");
                handleDelPsdUI(false);
                LoginUtils.setNormalButtonStatus(mContext, mBtnSubmit, false);
            }
        });

        mBtnPasswordSwitcher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mIsPasswordHidden) {
                    mEditPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mBtnPasswordSwitcher.setImageResource(R.drawable.login_show_password_bg);
                } else {
                    mEditPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mBtnPasswordSwitcher.setImageResource(R.drawable.login_hide_password_bg);
                }

                if (mEditPassword.getText().length() > 0) {
                    // 如果有输入密码，明文或密文改变后光标位置不变
                    mEditPassword.setSelection(mEditPassword.getText().length());
                }

                mIsPasswordHidden = !mIsPasswordHidden;
            }
        });

        mTxtGetSms.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleGetSms();
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                handleSubmit();
            }
        });

        mTxtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleSubmitButtonUI();

                boolean valid = mobileIsValid(s);
                LoginUtils.setNormalTextStatus(mContext, mTxtGetSms, valid);

                if (valid) {
                    mMobile = s.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEdtSms.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleSubmitButtonUI();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing to do
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing to do
            }
        });

        mEditPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                handleSubmitButtonUI();

                // 防止在密码输入框没有焦点时触发此事件
                if (!mEditPassword.hasFocus()) {
                    return;
                }

                // 删除密码按钮显示逻辑
                handleDelPsdUI(mEditPassword.getText().length() != 0);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mViewUserAgment.setOnCheckStatusChanged(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubmitButtonUI();
            }
        });
    }

    /**
     * 获取短信验证码
     */
    private void handleGetSms() {
        mEdtSms.requestFocus();
        startCountDown();
        if (mPresenter != null) {
            mPresenter.handleGetSms(mMobile);
        }
    }

    /**
     * 提交手机号、验证码进行登录
     */
    private void handleSubmit() {
        String sms = mEdtSms.getText().toString();
        String password = mEditPassword.getText().toString();
        if (mPresenter != null) {
            mPresenter.handleRegisterByPhone(mMobile, password, sms);
        }
    }

    @Override
    public void onDestroy() {
        stopCountDown();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onGetSmsSuccessful() {
        ShowTools.toast("发送验证码成功，请注意查收");
    }

    @Override
    public void onGetSmsFailed(String errorMessage) {
        LoginUtils.showErrorMessage(errorMessage);
    }

    @Override
    public void onRegisterByPhoneSuccessful() {
        // 停止倒计时
        stopCountDown();
        ShowTools.toast(getSuccessToast());
    }

    @Override
    public void onRegisterByPhoneError(int code, String errorMessage) {
        LoginUtils.showErrorMessage(errorMessage);
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public String getSuccessToast() {
        return "恭喜您，注册成功！";
    }

    @Override
    public String getSubmitButtonName() {
        return "立即注册";
    }

    @Override
    public boolean needShowUserAgreement() {
        return true;
    }
}
