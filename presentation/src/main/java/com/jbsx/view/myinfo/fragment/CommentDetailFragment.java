package com.jbsx.view.myinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.comment.MyCommentListView;
import com.jbsx.view.myinfo.view.detail.CommentDetailListView;

/**
 * 评论详情
 */
public class CommentDetailFragment extends BaseFragment {
    public static final String ARGUMENT = "argument";

    private View mRootView;
    private UserComments mRequestData;

    public CommentDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestData = bundle.getParcelable(ARGUMENT);
        }
    }

    public static CommentDetailFragment newInstance(UserComments argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        CommentDetailFragment contentFragment = new CommentDetailFragment();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.comment_detail_fragment, null, false);
        initViews();
        initEvents();
        replaceFragment(CommentDetailListView.newInstance(mRequestData));

        return mRootView;
    }

    private void initViews() {

    }

    private void initEvents() {

    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_comment_detail_list, fragment).commitAllowingStateLoss();
        }
    }
}
