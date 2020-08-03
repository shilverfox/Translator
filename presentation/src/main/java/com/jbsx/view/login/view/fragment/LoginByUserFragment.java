package com.jbsx.view.login.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.JDProgressBar;
import com.jbsx.data.ITransKey;
import com.jbsx.utils.FragmentUtil;
import com.jbsx.utils.Router;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.RegisterActivity;
import com.jbsx.view.login.ResetPasswordActivity;
import com.jbsx.view.login.customview.LoginUserAgreementView;
import com.jbsx.view.login.data.LoginData;
import com.jbsx.view.login.loginsdk.model.AppFailResult;
import com.jbsx.view.login.loginsdk.model.AppReplyCode;
import com.jbsx.view.login.util.LoginUtils;
import com.jbsx.view.login.view.contact.LoginByUserContract;
import com.jbsx.view.login.view.presenter.LoginByUserPresenter;

public class LoginByUserFragment extends BaseFragment
        implements LoginByUserContract.View {

    private final static String TAG = "LoginByJdFragment";

    private LoginByUserContract.Presenter mPresenter;

    private Button mBtnLogin;
    private EditText mEditPassword;
    private EditText mEditUserName;

    /**
     * 忘记密码，目前使用Url链接，允许界面不一致
     */
    private TextView mTxtForgetPwd;

    /**
     * 图片验证码的布局，如没有图片则就隐藏
     */
    private LinearLayout mLayoutImgCode;

    /**
     * 刷新图片验证码的progressBar
     */
    private ProgressBar mPrgBarFreshImgCode;

    /**
     * 隐藏或显示密码按钮
     */
    private ImageView mBtnPasswordSwitcher;

    /**
     * 删除密码按钮
     */
    private ImageView mBtnDeletePassword;

    /**
     * 新用户注册按钮
     */
    private TextView mBtnCreateAccount;

    /**
     * 密码输入框下面的白线，当需要输入图片验证码时，此线要显示
     */
    private ImageView mImgDividor;

    private View mRootView;

    private JDProgressBar mJDProgressBar;

    /**
     * 密码显示状态
     */
    private boolean mIsPasswordHidden = false;

    /**
     * 用户协议
     */
    private LoginUserAgreementView mViewUserAgment;

    public LoginByUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
        }
    }

    public static LoginByUserFragment newInstance() {
        Bundle bundle = new Bundle();

        LoginByUserFragment contentFragment = new LoginByUserFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.login_by_jd_fragment, null, false);

        createPresenter();
        findViews(mRootView);
        initViews();
        registEvents();

        return mRootView;
    }

    @Override
    public void createPresenter() {
        LoginViewUserCase userCase = new LoginViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new LoginByUserPresenter(this, userCase);
    }

    @Override
    public void enableLoginButton() {
        mBtnLogin.setEnabled(true);
    }


    public void login() {
        final String userName = mEditUserName.getText().toString();
        String password = mEditPassword.getText().toString();

        if (!checkName(mEditUserName)) {
            return;
        }

        if (!checkPassword(mEditPassword)) {
            return;
        }

        // 防止用户多次快速点击造成接口被多次访问
        if (!mBtnLogin.isEnabled()) {
            return;
        }

        mBtnLogin.setEnabled(false);
        addProgress();

        // 隐藏输入法
        LoginUtils.hideSoftInputMethod(mContext, mRootView);

        // 请求登录接口
        if (mPresenter != null) {
            mPresenter.login(userName, password);
        }
    }

    private boolean checkName(EditText ettJdName) {
        final String name = ettJdName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ShowTools.toast("请输入账号!");
            return false;
        }
        return true;
    }

    private boolean checkPassword(EditText etpassword) {
        final String password = etpassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            ShowTools.toast("请输入密码!");
            return false;
        }
        if (password != null && password.length() < 6) {
            ShowTools.toast("密码长度不够!");
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    /**
     * 删除密码按键可见性及可操作性
     *
     * @param enable
     */
    @Override
    public void handleDelPsdUI(boolean enable) {
        mBtnDeletePassword.setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
        mBtnDeletePassword.setEnabled(enable);
    }

    @Override
    public void whereToGo(LoginData loginData) {
        ShowTools.toast("登录成功");
        LoginUtils.whereToGo(mContext, loginData);
    }

    @Override
    public void showLoginFailedMessage() {
        ShowTools.toast("登录失败，请稍后再试");
    }

    private void registEvents() {

        mViewUserAgment.setOnCheckStatusChanged(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setButtonState();
            }
        });

        mBtnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 忘记密码
        mTxtForgetPwd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Router.getInstance().open(ResetPasswordActivity.class, getActivity());
            }
        });

        mEditPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 先判断所有的输入框是否都输入了内容
                setButtonState();

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

        mEditPassword.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    handleDelPsdUI(false);
                } else if (hasFocus && mEditPassword.getText().length() > 0) {
                    handleDelPsdUI(true);
                }
            }
        });

        mBtnDeletePassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditPassword.setText("");
                handleDelPsdUI(false);
                LoginUtils.setNormalButtonStatus(mContext, mBtnLogin, false);
            }
        });

        mBtnPasswordSwitcher.setOnClickListener(new OnClickListener() {

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

        mEditUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setButtonState();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mBtnCreateAccount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Router.getInstance().open(RegisterActivity.class, getActivity());
            }
        });
    }

    @Override
    public void initViews() {
        mEditPassword.setText("");
        mEditUserName.setText("");

        setButtonState();
    }

    private void findViews(View view) {
        if (view == null) {
            return;
        }

        mBtnLogin = (Button) view.findViewById(R.id.login_jd_login);
        mEditPassword = (EditText) view.findViewById(R.id.login_jd_password);
        mEditUserName = (EditText) view.findViewById(R.id.login_jd_num);
        mTxtForgetPwd = (TextView) view.findViewById(R.id.login_forget_password);
        mLayoutImgCode = (LinearLayout) view.findViewById(R.id.picture_layout);
        mPrgBarFreshImgCode = (ProgressBar) view.findViewById(R.id.regist_progressBar);
        mBtnPasswordSwitcher = (ImageView) view.findViewById(R.id.hide_password);
        mBtnDeletePassword = (ImageView) view.findViewById(R.id.delete_password);
        mBtnCreateAccount = (TextView) view.findViewById(R.id.login_register);
        mImgDividor = (ImageView) view.findViewById(R.id.view_line);
        mViewUserAgment = (LoginUserAgreementView) view.findViewById(R.id.viewUserAgreement);
        mJDProgressBar = (JDProgressBar)view.findViewById(R.id.login_jd_progress);
    }

    /**
     * 清空密码
     */
    public void clearPasswordInput() {
        mEditPassword.setText("");
    }

    @Override
    public void removeProgress() {
        mJDProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void addProgress() {
        mJDProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isFragmentAlive() {
        return FragmentUtil.checkLifeCycle(getActivity(), LoginByUserFragment.this);
    }

    /**
     * 根据返回不同的code判断登录状态
     */
    public void matchingCode(AppFailResult failResult) {
        if (failResult == null) {
            return;
        }

        if (failResult.getReplyCode() == AppReplyCode.reply0x7) {
            // 账号不存在
            ShowTools.toast("您输入的账号不存在，请核对后重试。");
            handleInputWindow(mEditUserName, false);
        } else {
            LoginUtils.showErrorMessage(failResult.getMessage());
        }
    }

    /**
     * 处理输入框焦点及输入法
     *
     * @param view
     */
    private void handleInputWindow(final View view, final boolean clearContent) {
        if (view != null) {
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);

            // 防止界面重绘导致删除焦点后密码输入框获取不到焦点
            MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
                @Override
                public void run() {
                    view.requestFocus();

                    // 清空输入框内容
                    if (clearContent && view instanceof EditText) {
                        ((EditText)view).setText("");
                    }
                }
            }, 100);

            // 输入法
            LoginUtils.toggleSoftInputMethod(mContext, mRootView);
        }
    }

    /**
     * 登录按钮状态判断
     */
    @Override
    public void setButtonState() {
        boolean userAccountCase = mEditUserName.getText().length() > 0;
        boolean pswCase = mEditPassword.getText().length() > 0;
        boolean userAgmentCase = mViewUserAgment.getUserChecked();

        LoginUtils.setNormalButtonStatus(mContext, mBtnLogin,
                (userAccountCase & pswCase & userAgmentCase));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}