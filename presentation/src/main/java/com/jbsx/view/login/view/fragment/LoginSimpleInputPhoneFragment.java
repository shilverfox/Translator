//package com.translatmaster.view.login.view.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.text.TextWatcher;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.app.data.net.repository.TaskManager;
//import com.app.domain.net.interactor.LoginViewUserCase;
//import com.translatmaster.R;
//import com.translatmaster.app.BaseFragment;
//import com.translatmaster.app.MainApplicationLike;
//import com.translatmaster.utils.ShowTools;
//import com.translatmaster.utils.SubmitTools;
//import com.translatmaster.view.login.LoginActivity;
//import com.translatmaster.view.login.customview.LoginUserAgreementView;
//import com.translatmaster.view.login.util.LoginHelper;
//import com.translatmaster.view.login.util.LoginUtils;
//import com.translatmaster.view.login.view.contact.LoginSimpleInputPhoneContract;
//import com.translatmaster.view.login.view.presenter.LoginByUserPresenter;
//import com.translatmaster.view.login.view.presenter.LoginSimpleInputPhonePresenter;
//
//
///**
// * 手机号码 + 短信验证码登录
// */
//
//public class LoginSimpleInputPhoneFragment extends BaseFragment
//        implements LoginSimpleInputPhoneContract.View {
//
//    private View mRootView;
//    private EditText mEdtMobile;
//    private Button mBtnSubmit;
//    private Button mBtnGetSms;
//    private ImageView mBtnDelContent;
//
//    /** 用户协议 */
//    private LoginUserAgreementView mViewUserAgment;
//
//    private LoginSimpleInputPhoneContract.Presenter mPresenter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mRootView = inflater.inflate(R.layout.login_simple_input_phone_fragment, null, false);
//
//        createPresenter();
//        findViews();
//        registEvents();
//        preEvent();
//        getDataFromIntent();
//
//        return mRootView;
//    }
//
//    public void createPresenter() {
//        LoginViewUserCase userCase = new LoginViewUserCase(TaskManager.getTaskManager(),
//                MainApplicationLike.getUiThread());
//        mPresenter = new LoginSimpleInputPhonePresenter(this, userCase);
//    }
//
//    private void getDataFromIntent() {
//        Bundle bundle = getArguments();
//        if(bundle != null) {
//            String phone = bundle.getString("PushCodePhone");
//            if (!TextUtils.isEmpty(phone)) {
//                mEdtMobile.setText(phone);
//                handldGetSms();
//            }
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//    }
//
//    /**
//     * 初始执行的加载逻辑
//     */
//    private void preEvent() {
//        // 如果调用isShowMobileLogin获取是否有快捷登录入口，则此处不必需要前置方法
////        handleBeforeMobileLogin();
//        handleSubmitButtonUI();
//    }
//
//    private void findViews() {
//        mEdtMobile = (EditText) mRootView.findViewById(R.id.edt_login_ps_mobile);
//        mBtnSubmit = (Button) mRootView.findViewById(R.id.edt_login_ps_submit);
//        mBtnDelContent = (ImageView) mRootView.findViewById(R.id.btn_delete_password);
//        mBtnGetSms = (Button) mRootView.findViewById(R.id.edt_login_ps_get_sms);
//        mViewUserAgment = (LoginUserAgreementView) mRootView.findViewById(R.id.viewUserAgreement);
//    }
//
//    private void registEvents() {
//        mBtnGetSms.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                handldGetSms();
//            }
//        });
//
//        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                handldGetSms();
//            }
//        });
//
//        mEdtMobile.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                handleSubmitButtonUI();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // Nothing to do
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Nothing to do
//            }
//        });
//
//        mBtnDelContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mEdtMobile.setText("");
//            }
//        });
//
//        mViewUserAgment.setOnCheckStatusChanged(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleSubmitButtonUI();
//            }
//        });
//    }
//
//    /**
//     * 控制提交按钮点击状态
//     */
//    private void handleSubmitButtonUI() {
//        boolean inputCase = mEdtMobile.getText().length() > 0;
//        boolean checkCase = mViewUserAgment.getUserChecked();
//
//        LoginUtils.setNormalButtonStatus(mContext, mBtnSubmit, (inputCase & checkCase));
//        mBtnDelContent.setVisibility(inputCase ? View.VISIBLE : View.INVISIBLE);
//    }
//
//    /**
//     * 防止频繁点击
//     */
//    private void lockSubmitBtn() {
//        LoginUtils.setNormalButtonStatus(mContext, mBtnSubmit, false);
//    }
//
//    private void unLockSubmitBtn() {
//        LoginUtils.setNormalButtonStatus(mContext, mBtnSubmit, true);
//    }
//
//    /**
//     * 获得当前输入框里的手机号码
//     *
//     * @return
//     */
//    private String getMobileOfEditText() {
//        return mEdtMobile.getText().toString();
//    }
//
//    /**
//     * 获取短信验证码
//     */
//    private void handldGetSms() {
//        if (SubmitTools.canSubmit()) {
//            final String mobile = getMobileOfEditText();
//            if (mPresenter != null) {
//                mPresenter.handleGetSms(mobile);
//            }
//        }
//    }
//
//    /**
//     * 跳转到输入验证码页面
//     */
//    private void gotoInputSmsView() {
//    }
//
//    @Override
//    public void onGetSmsSuccessful() {
//        ShowTools.toast("验证码已发送");
//        gotoInputSmsView();
//        unLockSubmitBtn();
//    }
//
//    @Override
//    public void onGetSmsFailed(String errorMessage) {
//        LoginUtils.showErrorMessage(errorMessage);
//        unLockSubmitBtn();
//    }
//
//    @Override
//    public Context getViewContext() {
//        return mContext;
//    }
//}
