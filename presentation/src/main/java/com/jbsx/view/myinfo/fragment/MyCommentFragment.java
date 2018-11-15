package com.jbsx.view.myinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.domain.net.data.ConstData;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.view.main.entity.TabEntity;
import com.jbsx.view.myinfo.view.comment.MyCommentListView;
import com.jbsx.view.myinfo.view.message.MyInfoMessageListView;

import java.util.ArrayList;

public class MyCommentFragment extends BaseFragment {
    private View mRootView;

    public MyCommentFragment() {
        // Required empty public constructor
    }

    public static MyCommentFragment newInstance() {
        return new MyCommentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.my_comment_fragment, null, false);

        initViews();
        initEvents();
        replaceFragment(MyCommentListView.newInstance(ConstData.COMMENT_TYPE_TO_VIDEO));

        return mRootView;
    }

    private void initViews() {

    }

    private void initEvents() {

    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_my_comment, fragment).commitAllowingStateLoss();
        }
    }
}
