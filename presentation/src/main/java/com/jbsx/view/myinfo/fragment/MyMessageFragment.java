package com.jbsx.view.myinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.domain.net.data.ConstData;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.data.ITransKey;
import com.jbsx.view.main.entity.TabEntity;
import com.jbsx.view.myinfo.view.message.MyInfoMessageListView;

import java.util.ArrayList;

/**
 * 我的消息列表
 */

public class MyMessageFragment extends BaseFragment {
    private View mRootView;
    private CommonTabLayout mTabLayout;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private String[] mTitles = {"回复消息", "赞消息"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private int mMessageType;

    public MyMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mMessageType = bundle.getInt(ITransKey.KEY);
        }
    }

    public static MyMessageFragment newInstance(int messageType) {
        Bundle bundle = new Bundle();
        bundle.putInt(ITransKey.KEY, messageType);

        MyMessageFragment contentFragment = new MyMessageFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.my_message_fragment, null, false);

        initViews();
        initEvents();
        initMainTab();

        return mRootView;
    }

    private void initViews() {
        mTabLayout = mRootView.findViewById(R.id.tab_my_message);
    }

    private void initEvents() {
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                ((MyInfoMessageListView)mFragmentList.get(position)).clearAndFresh();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void initMainTab() {
        mFragmentList.add(MyInfoMessageListView.newInstance(ConstData.MESSAGE_TYPE_COMMENT));
        mFragmentList.add(MyInfoMessageListView.newInstance(ConstData.MESSAGE_TYPE_NB));

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
        }

        // 不支持滑动切换
        mTabLayout.setTabData(mTabEntities, getActivity(), R.id.layout_my_message, mFragmentList);
        mTabLayout.setCurrentTab(mMessageType - 2);
    }
}
