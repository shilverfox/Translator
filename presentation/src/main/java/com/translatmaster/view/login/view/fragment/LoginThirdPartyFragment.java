//package com.translatmaster.view.login.view.fragment;
//
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import com.translatmaster.R;
//import com.translatmaster.app.BaseFragment;
//import com.translatmaster.app.MainApplicationLike;
//import com.translatmaster.utils.FragmentUtil;
//import com.translatmaster.utils.ProgressBarHelper;
//import com.translatmaster.utils.ShowTools;
//import com.translatmaster.utils.SubmitTools;
//import com.translatmaster.view.login.data.AccountVerifyEvent;
//import com.translatmaster.view.login.data.LoginConstData;
//import com.translatmaster.view.login.data.LoginData;
//import com.translatmaster.weixin.WechatEvent;
//import com.translatmaster.view.login.thirdapi.IWechatLoginListener;
//import com.translatmaster.view.login.thirdapi.LoginThirdResultEvent;
//import com.translatmaster.view.login.thirdapi.ThridPartyLoginHelper;
//import com.translatmaster.view.login.util.LoginUtils;
//import com.translatmaster.view.login.view.contact.LoginThirdPartyContract;
//import com.translatmaster.view.login.view.presenter.LoginThirdPartyPresenter;
//
///**
// * 第三方登录区
// *
// * Created by lijian15 on 2016/11/9.
// */
//
//public class LoginThirdPartyFragment extends BaseFragment
//        implements LoginThirdPartyContract.View, IWechatLoginListener {
//
//    private LoginThirdPartyContract.Presenter mPresenter;
//
//    private View mRootView;
//    private View mLayoutIconView;
//    private ImageView mBtnWxLogin;
//
//    /** 微信返回code */
//    private String mWxinResponseCode;
//
//    public LoginThirdPartyFragment() {
//        // Required empty public constructor
//    }
//
//    public static LoginThirdPartyFragment newInstance() {
//        return new LoginThirdPartyFragment();
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        mRootView = inflater.inflate(R.layout.login_third_party_fragment, null, false);
//
//        findViews(mRootView);
//        initViews();
//        registEvents();
//        createPresenter();
//
//        return mRootView;
//    }
//
//    private void findViews(View view) {
//        if (view == null) {
//            return;
//        }
//
//        mBtnWxLogin = (ImageView)view.findViewById(R.id.btn_wx_login);
//        mLayoutIconView = view.findViewById(R.id.layout_login_third_icon);
//    }
//
//    private void registEvents() {
//        mBtnWxLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleWechatLogin();
//            }
//        });
//    }
//
//    private void initViews() {
//    }
//
//    @Override
//    public void createPresenter() {
//        mPresenter = new LoginThirdPartyPresenter(this);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    /**
//     * 微信登录
//     */
//    private void handleWechatLogin() {
//        // 防止频繁点击
//        if (!SubmitTools.canSubmit()) {
//            return;
//        }
//
//        if (!checkIsSupportShare()) {
//            ShowTools.toast("需要安装微信哦");
//        } else {
//            addProgress();
//            ThridPartyLoginHelper.requestWeixinLogin(mContext);
//        }
//    }
//
//    /**
//     * 检查是否有安装微信
//     *
//     * @return
//     */
//    public boolean checkIsSupportShare() {
//        return MainApplicationLike.getInstance().getWXApi().isWXAppInstalled();
//    }
//
//    @Override
//    public void whereToGo(LoginData loginData) {
//        LoginUtils.whereToGo(eventBus, mContext, loginData);
//    }
//
//    @Override
//    public boolean isFragmentAlive() {
//        return FragmentUtil.checkLifeCycle(getActivity(), LoginThirdPartyFragment.this);
//    }
//
//    @Override
//    public void removeProgress() {
//        ProgressBarHelper.removeProgressBar(mLayoutIconView);
//    }
//
//    @Override
//    public void addProgress() {
//        ProgressBarHelper.addProgressBar(mLayoutIconView);
//    }
//
//    /**
//     * 第三方登录Event bus
//     *
//     * @param event
//     */
//    // @Subscribe
//    @Override
//    public void onEventMainThread(LoginThirdResultEvent event) {
//        if (event != null) {
//            mWxinResponseCode = event.getCode();
//
//            // 调用sdk登录
//            if (mPresenter != null) {
//                mPresenter.loginByWechatWithCode(mWxinResponseCode);
//            }
//        }
//
//        if (TextUtils.isEmpty(mWxinResponseCode)) {
//            removeProgress();
//        }
//    }
//
//    /**
//     * 账号验证消息
//     * 3.3增加
//     * 3.6修改
//     *
//     * @param event
//     */
//    // @Subscribe
//    @Override
//    public void onEventMainThread(AccountVerifyEvent event) {
//        int src = -1;
//        String token = null;
//
//        if (event != null) {
//            src = event.getRequestSrc();
//            token = event.getToken();
//        }
//
//        if (src != -1 && (!TextUtils.isEmpty(token)) && mPresenter != null) {
//            if (LoginConstData.TYPE_HTML_REQUEST_WECHAT_ACCOUNT_VERIFY == src
//                    || LoginConstData.TYPE_HTML_REQUEST_JD_BIND_WECHAT == src) {
//                // 微信登录账号风控返回，微信账号绑定京东账号完毕，用UID信息再次登录
//                addProgress();
//                mPresenter.bindAccountByToken(token);
//            }
//        } else {
//            // 不需要单独处理的事件，比如用户关闭风控webview
//            removeProgress();
//        }
//    }
//
//    /**
//     * 微信未登录时的操作
//     *
//     * @param event
//     */
//    @Override
//    public void onEventMainThread(WechatEvent event) {
//        removeProgress();
//    }
//}
