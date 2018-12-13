package com.jbsx.view.myinfo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.RecyclerViewHelper;
import com.jbsx.utils.image.ImageLoader;
import com.jbsx.view.login.callback.ILoginResultListener;
import com.jbsx.view.login.callback.IOnLoginListener;
import com.jbsx.view.login.data.LoginData;
import com.jbsx.view.login.data.LoginResultEvent;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.adapter.MyInfoAdapter;
import com.jbsx.view.myinfo.contact.MyInfoContact;
import com.jbsx.view.myinfo.data.MyInfoConst;
import com.jbsx.view.myinfo.entity.MyInfoItem;
import com.jbsx.view.myinfo.presenter.MyInfoPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MyInfoFragment extends BaseFragment implements ILoginResultListener, MyInfoContact.View {
    private View mRootView;
    private View mViewUserContainer;
    private RecyclerView mRecyclerView;
    private Button mBtnLogOut;
    private TextView mTxtUserName;
    private TextView mTxtBusinessName;
    private TextView mTxtValidDate;
    private TextView mTxtLogin;
    private ImageView mIvUserHead;

    private MyInfoAdapter mAdapter;

    public MyInfoContact.Presenter mPresenter;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.my_info_fragment, null, false);
        createPresenter();
        findViews();
        initEvents();
        initAdapter();
        initViews();
        loadUserInfo();

        return mRootView;
    }

    @Override
    public void createPresenter() {
        MyInfoUserCase userCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new MyInfoPresenter(this, userCase);
    }

    private void initViews() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(RecyclerViewHelper.getDivider(mContext, true));
    }

    /**
     * 先加载一遍用户信息，有效期有可能会更新
     * 获取后更新保存的用户数据
     * 更新页面
     */
    private void loadUserInfo() {
        if (LoginHelper.getInstance().isLogin()) {
            mPresenter.requestUserInfo(LoginHelper.getInstance().getUserId());
        } else {
            refreshUI();
        }
    }

    private void handleLogBtnUI() {
        boolean isLogin = LoginHelper.getInstance().isLogin();
        mBtnLogOut.setVisibility(isLogin ? View.VISIBLE : View.INVISIBLE);
    }

    private void findViews() {
        mRecyclerView = mRootView.findViewById(R.id.rv_my_info_list);
        mBtnLogOut = mRootView.findViewById(R.id.btn_log_out);
        mTxtUserName = mRootView.findViewById(R.id.tv_my_info_user_name);
        mTxtBusinessName = mRootView.findViewById(R.id.tv_my_info_business_name);
        mTxtValidDate = mRootView.findViewById(R.id.tv_my_info_valid_date);
        mTxtLogin = mRootView.findViewById(R.id.tv_my_info_login);
        mIvUserHead = mRootView.findViewById(R.id.iv_my_info_user_head);
        mViewUserContainer = mRootView.findViewById(R.id.view_my_info_user);
    }

    private void initEvents() {
        mBtnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 退出登录
                LoginHelper.getInstance().logOut();
                refreshUI();
            }
        });

        // 未登录时可以点击登录
        mViewUserContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!LoginHelper.getInstance().isLogin()) {
                    LoginHelper.getInstance().startLogin(getActivity(), new IOnLoginListener() {
                        @Override
                        public void onSucess() {
                            // 普通登录，没啥可回调的
                        }

                        @Override
                        public void onFailed() {

                        }
                    });
                }
            }
        });
    }

    private void initAdapter() {
        mAdapter = new MyInfoAdapter(mContext, R.layout.myinfo_fragment_item);

        // 制造数据
        List<MyInfoItem> items = new ArrayList<MyInfoItem>();
        for (int i = 0; i < MyInfoConst.MY_TYPE_IDS.length; i++) {
            MyInfoItem infoItem = new MyInfoItem(MyInfoConst.MY_TYPE_IDS[i], MyInfoConst.MY_TYPE_NAMES[i]);
            infoItem.setTo(MyInfoConst.MY_TYPE_NAVIGATIONS[i]);
            infoItem.setNeedLogin(MyInfoConst.NEED_LOGIN[i]);
            items.add(infoItem);
        }

        mAdapter.setList(items);
    }

    /**
     * 接口获取用户信息成功，更新用户信息
     * 这个接口不返回token，因此不要更新token
     *
     * @param userInfo
     */
    @Override
    public void updateUserInfo(LoginData userInfo) {
        // 缓存token
        String token = new String(LoginHelper.getInstance().getUserToken());
        LoginHelper.getInstance().setLoginUser(userInfo);
        LoginHelper.getInstance().setToken(token);
        LoginHelper.getInstance().saveData();
        refreshUI();
    }

    /**
     * 处理用户信息显示
     */
    private void handleUserData() {
        LoginData userData = LoginHelper.getInstance().getLoginUser();
        if (userData != null && userData.getPayload() != null) {
            LoginData.UserInfo userInfo = userData.getPayload().getUserInfo();

            if (userInfo != null) {
                mTxtUserName.setText(userData.getPayload().getAccount());
                mTxtUserName.setVisibility(View.VISIBLE);
                mTxtBusinessName.setText(userInfo.getDepartment());
                mTxtBusinessName.setVisibility(View.VISIBLE);
                mTxtValidDate.setText(userInfo.getMemberEnd());
                mTxtValidDate.setVisibility(View.VISIBLE);
                mTxtLogin.setVisibility(View.GONE);

                // 头像
                ImageLoader.displayImage(userInfo.getImageUrl(), mIvUserHead, R.drawable.default_head, true);
            }
        } else {
            // 未登录
            mTxtUserName.setVisibility(View.GONE);
            mTxtBusinessName.setVisibility(View.GONE);
            mTxtValidDate.setVisibility(View.GONE);
            mTxtLogin.setVisibility(View.VISIBLE);
            mIvUserHead.setImageResource(R.drawable.my_icon_default);
        }
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginResultEvent event) {
        if (event != null && event.action == LoginResultEvent.LoginAction.SUCCESS) {
            refreshUI();
        }
    }

    private void refreshUI() {
        handleLogBtnUI();
        handleUserData();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshUI();
    }
}
