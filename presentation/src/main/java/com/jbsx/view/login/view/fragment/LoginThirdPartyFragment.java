package com.jbsx.view.login.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.LoginViewUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.FragmentUtil;
import com.jbsx.utils.ProgressBarHelper;
import com.jbsx.utils.ShowTools;
import com.jbsx.utils.SubmitTools;
import com.jbsx.view.login.data.LoginData;
import com.jbsx.view.login.thirdapi.ThridPartyLoginHelper;
import com.jbsx.view.login.util.LoginUtils;
import com.jbsx.view.login.view.contact.LoginThirdPartyContract;
import com.jbsx.view.login.view.presenter.LoginSimpleInputSmsPresenter;
import com.jbsx.view.login.view.presenter.LoginThirdPartyPresenter;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 第三方登录区
 *
 * Created by lijian15 on 2016/11/9.
 */

public class LoginThirdPartyFragment extends BaseFragment
        implements LoginThirdPartyContract.View {

    private LoginThirdPartyContract.Presenter mPresenter;

    private View mRootView;
    private View mLayoutIconView;
    private ImageView mBtnWxLogin;

    /** 微信返回code */
    private String mWxinResponseCode;

    public LoginThirdPartyFragment() {
        // Required empty public constructor
    }

    public static LoginThirdPartyFragment newInstance() {
        return new LoginThirdPartyFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.login_third_party_fragment, null, false);

        findViews(mRootView);
        initViews();
        registEvents();
        createPresenter();

        return mRootView;
    }

    private void findViews(View view) {
        if (view == null) {
            return;
        }

        mBtnWxLogin = (ImageView)view.findViewById(R.id.btn_wx_login);
        mLayoutIconView = view.findViewById(R.id.layout_login_third_icon);
    }

    private void registEvents() {
        mBtnWxLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleWechatLogin();
            }
        });
    }

    private void initViews() {
    }

    @Override
    public void createPresenter() {
        LoginViewUserCase userCase = new LoginViewUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new LoginThirdPartyPresenter(this, userCase);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 微信登录
     */
    private void handleWechatLogin() {
        // 防止频繁点击
        if (!SubmitTools.canSubmit()) {
            return;
        }

        if (!checkIsSupportShare()) {
            ShowTools.toast("需要安装微信哦");
        } else {
            addProgress();
            loginByShareSdk();
        }
    }

    /**
     * 检查是否有安装微信
     *
     * @return
     */
    public boolean checkIsSupportShare() {
        return MainApplicationLike.getInstance().getWXApi().isWXAppInstalled();
    }

    private void loginByShareSdk() {
        //判断指定平台是否已经完成授权
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if(plat.isAuthValid()) {
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                handleLogin(userId);
                return;
            }
        }
        plat.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
                if (action == Platform.ACTION_USER_INFOR) {
                    PlatformDb platDB = platform.getDb();
                    //通过DB获取各种数据
                    platDB.getToken();
                    platDB.getUserGender();
                    platDB.getUserIcon();
                    platDB.getUserId();
                    platDB.getUserName();

                    handleLogin(platDB.getUserId());
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                removeProgress();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                removeProgress();
            }
        });

        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        //获取用户资料
        plat.showUser(null);
    }

    private void handleLogin(String userId) {
        mPresenter.loginByWechatWithCode(userId);
    }

    @Override
    public boolean isFragmentAlive() {
        return FragmentUtil.checkLifeCycle(getActivity(), LoginThirdPartyFragment.this);
    }

    @Override
    public void removeProgress() {
        ProgressBarHelper.removeProgressBar(mLayoutIconView);
    }

    @Override
    public void addProgress() {
        ProgressBarHelper.addProgressBar(mLayoutIconView);
    }

    @Override
    public void whereToGo(LoginData loginData) {
        ShowTools.toast("登录成功");
        LoginUtils.whereToGo(mContext, loginData);
    }
}
