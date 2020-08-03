//package com.translatmaster.view.login.view.fragment;
//
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.text.method.HideReturnsTransformationMethod;
//import android.text.method.PasswordTransformationMethod;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.translatmaster.R;
//import com.translatmaster.app.BaseFragment;
//import com.translatmaster.app.MainApplicationLike;
//import com.translatmaster.utils.ProgressBarHelper;
//import com.translatmaster.utils.ShowTools;
//import com.translatmaster.view.login.data.CloseLoginEvent;
//import com.translatmaster.view.login.data.LoginBindingPhoneData;
//import com.translatmaster.view.login.util.LoginHelper;
//import com.translatmaster.view.login.util.LoginUtils;
//import com.translatmaster.view.login.view.contact.LoginSetPasswordContract;
//import com.translatmaster.view.login.view.presenter.LoginSetPasswordPresenter;
//
//
///**
// * 设置密码
// */
//
//public class LoginSetPasswordFragment extends BaseFragment
//        implements LoginSetPasswordContract.View {
//
//    private View mRootView;
//    private EditText mEditPassWord;
//    private Button mBtnLogin;
//    private ImageView mImgSwitchButton;
//
//    private static String mMobile = "";
//
//    /** 密码显示状态 */
//    private boolean mIsHidden = false;
//
//    /** 登录类型 */
//    private int mLoginType;
//
//    private LoginSetPasswordContract.Presenter mPresenter;
//
//    public static LoginSetPasswordFragment getInstance(String mobile) {
//        mMobile = mobile;
//
//        return new LoginSetPasswordFragment();
//    }
//
//    public LoginSetPasswordFragment() {
//    }
//
//    public void setLoginType(int loginType) {
//        this.mLoginType = loginType;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mRootView = inflater.inflate(R.layout.login_set_password_fragment, null, false);
//
//        findViews();
//        registEvents();
//        preEvent();
//        initView();
//        createPresenter();
//
//        return mRootView;
//    }
//
//    @Override
//    public void createPresenter() {
//        mPresenter = new LoginSetPasswordPresenter(this);
//    }
//
//    /**
//     * 处理错误返回
//     *
//     * @param errorMessage
//     */
//    @Override
//    public void handleFailedReason(String errorMessage) {
//        LoginUtils.showErrorMessage(errorMessage);
//    }
//
//    @Override
//    public void requestInputFocus() {
//        MainApplicationLike.getInstance().getHanlder().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mEditPassWord.requestFocus();
//            }
//        }, 100);
//    }
//
//    @Override
//    public void hideInputMethod() {
//        LoginUtils.hideSoftInputMethod(mContext, mRootView);
//    }
//
//    @Override
//    public void hideProgressBar() {
//        ProgressBarHelper.removeProgressBar(mRootView);
//    }
//
//    @Override
//    public void showProgressBar() {
//        ProgressBarHelper.addProgressBar(mRootView);
//    }
//
//    @Override
//    public void showDefaultNetErrorInfo() {
//        ShowTools.toast("网络繁忙，请稍后再试");
//    }
//
//    @Override
//    public void showDefaultErrorInfo() {
//        ShowTools.toast("登录失败，请稍后再试");
//    }
//
//    @Override
//    public void handleAccountBindSuccess(String xpin, LoginBindingPhoneData bindData) {
//        if (bindData != null && !TextUtils.isEmpty(xpin)) {
//            // 验证成功，跳转主界面
//            String pin = bindData.getResult().getPin();
//            String mobile = bindData.getResult().getMobile();
//            String userName = LoginSdkHelper.getUserAccount();
//
//            LoginUser loginUser = LoginHelper.getInstance().initLoginData(mobile, xpin, pin, userName);
//            hideProgressBar();
//
//            // 由于进入登陆或注册的入口比较多，而且要返回当时的入口，所以不可以强制返回主界面
//            eventBus.post(loginUser);
//
//            // 关闭之前打开的注册界面
//            eventBus.post(new CloseLoginEvent());
//            if(getActivity()!=null){
//                getActivity().finish();
//            }
//        }
//    }
//
//    /**
//     * 初始执行的加载逻辑
//     */
//    private void preEvent() {
//
//    }
//
//    private void findViews() {
//        mEditPassWord = (EditText)mRootView.findViewById(R.id.login_jd_password);
//        mBtnLogin = (Button)mRootView.findViewById(R.id.login_jd_login);
//        mImgSwitchButton = (ImageView)mRootView.findViewById(R.id.login_jd_switch);
//    }
//
//    /**
//     * 密码显示状态切换
//     */
//    private void handleBtnShowPswClick() {
//        if (!mIsHidden) {
//            mImgSwitchButton.setImageResource(R.drawable.login_show_password_bg);
//            mEditPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//        } else {
//            mImgSwitchButton.setImageResource(R.drawable.login_hide_password_bg);
//            mEditPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
//        }
//
//        if (mEditPassWord.getText().length() > 0) {
//            // 如果有输入密码，明文或密文改变后光标位置不变
//            mEditPassWord.setSelection(mEditPassWord.getText().length());
//        }
//
//        mIsHidden = !mIsHidden;
//    }
//
//    private void registEvents() {
//        // 密码显示状态切换
//        mImgSwitchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleBtnShowPswClick();
//            }
//        });
//
//        // 先用京东sdk注册，然后调用服务器接口，最后都成功后进入主界面
//        mBtnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                registerPhone();
//            }
//        });
//
//        mEditPassWord.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                setButtonState();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//    }
//
//    private void initView() {
//        mEditPassWord.setText("");
//        setButtonState();
//    }
//
//    private void registerPhone() {
//        String mPassWord = mEditPassWord.getText().toString();
//
//        if (TextUtils.isEmpty(mPassWord)) {
//            ShowTools.toast("请输入密码");
//            return;
//        }
//
//        if (mPassWord.length() < 6 || mPassWord.length() > 20) {
//            ShowTools.toast("密码长度必须大于6位小于20位！");
//            return;
//        }
//
//        // 防止用户多次点击造成多次访问服务器
//        if (!mBtnLogin.isEnabled()) {
//            return;
//        }
//
//        LoginUtils.hideSoftInputMethod(mContext, mRootView);
//        setSubmitBtnEnable(true);
//
//        if (mPresenter != null) {
//            mPresenter.handleSetPwsRequest(mMobile, mPassWord);
//        }
//    }
//
//    /**
//     * 按钮可否点击
//     *
//     * @param enable
//     */
//    @Override
//    public void setSubmitBtnEnable(boolean enable) {
//        mBtnLogin.setEnabled(enable);
//    }
//
//    private void setButtonState(){
//        if (TextUtils.isEmpty(mEditPassWord.getText())) {
//            mBtnLogin.setBackgroundResource(R.drawable.regist_button_disable_bg);
//            setSubmitBtnEnable(false);
//        } else {
//            mBtnLogin.setBackgroundResource(R.drawable.longin_button_green_bg);
//            setSubmitBtnEnable(true);
//        }
//    }
//
//    @Override
//    public void onResume() {
//        // 设置保存的登录类型，有可能点击“新用户注册”而被更改了
//        LoginHelper.getInstance().setLoginType(mLoginType);
//
//        super.onResume();
//    }
//}