package com.translatmaster.view.myinfo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.app.BaseEvent;
import com.translatmaster.app.BaseFragment;
import com.translatmaster.utils.RecyclerViewHelper;
import com.translatmaster.view.login.callback.ILoginResultListener;
import com.translatmaster.view.login.data.LoginData;
import com.translatmaster.view.login.data.LoginResultEvent;
import com.translatmaster.view.login.util.LoginHelper;
import com.translatmaster.view.myinfo.adapter.MyInfoAdapter;
import com.translatmaster.view.myinfo.data.MyInfoConst;
import com.translatmaster.view.myinfo.entity.MyInfoItem;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MyInfoFragment extends BaseFragment implements ILoginResultListener {
    private View mRootView;
    private RecyclerView mRecyclerView;
    private Button mBtnLogOut;
    private TextView mTxtUserName;
    private TextView mTxtBusinessName;
    private TextView mTxtValidDate;
    private TextView mTxtLogin;

    private MyInfoAdapter mAdapter;

    public MyInfoFragment() {
        // Required empty public constructor
    }

    public static MyInfoFragment newInstance() {
        return new MyInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.my_info_fragment, null, false);
        findViews();
        initEvents();
        initAdapter();
        initViews();

        return mRootView;
    }

    private void initViews() {
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(RecyclerViewHelper.getDivider(mContext));

        refreshUI();
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
        mTxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginHelper.getInstance().startLogin(getActivity());
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
            items.add(infoItem);
        }

        mAdapter.setList(items);
    }

    /**
     * 处理用户信息显示
     */
    private void handleUserData() {
        LoginData userData = LoginHelper.getInstance().getLoginUser();
        if (userData != null) {
            LoginData.Result result = userData.getPayload();

            if (result != null) {
                mTxtUserName.setText(result.getCellPhone());
                mTxtUserName.setVisibility(View.VISIBLE);
                mTxtBusinessName.setText(result.getId());
                mTxtBusinessName.setVisibility(View.VISIBLE);
                mTxtValidDate.setText("dd");
                mTxtValidDate.setVisibility(View.VISIBLE);
                mTxtLogin.setVisibility(View.GONE);
            }
        } else {
            // 未登录
            mTxtUserName.setVisibility(View.GONE);
            mTxtBusinessName.setVisibility(View.GONE);
            mTxtValidDate.setVisibility(View.GONE);
            mTxtLogin.setVisibility(View.VISIBLE);
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
}
