package com.jbsx.view.myinfo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.BaseRequestCallback;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.app.domain.net.model.BaseDomainData;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.CommentInputDialog;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.utils.MessageTools;
import com.jbsx.utils.Router;
import com.jbsx.utils.ShowTools;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.entity.TabEntity;
import com.jbsx.view.myinfo.activity.CommentDetailActivity;
import com.jbsx.view.myinfo.data.CommentEvent;
import com.jbsx.view.myinfo.data.MyCommentData;
import com.jbsx.view.myinfo.data.MyInfoConst;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.comment.MyCommentListView;
import com.jbsx.view.myinfo.view.message.MyInfoMessageListView;
import com.jbsx.view.search.entity.SearchEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Comment;

import java.util.ArrayList;

public class MyCommentFragment extends BaseFragment {
    private View mRootView;
    private MyInfoUserCase mMyInfoUserCase;
    private MyCommentListView mMyCommentListView;

    public MyCommentFragment() {
        // Required empty public constructor
    }

    public static MyCommentFragment newInstance() {
        return new MyCommentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.my_comment_fragment, null, false);
        mMyInfoUserCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        initViews();
        initEvents();
        mMyCommentListView = MyCommentListView.newInstance(ConstData.COMMENT_TYPE_TO_VIDEO);
        replaceFragment(mMyCommentListView);

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceivedCommentEvent(CommentEvent event) {
        if (event != null && event.getUserComments() != null) {
            switch (event.getType()) {
                case MyInfoConst.EVENT_BUS_DELETE_COMMENT:
                    handleDeleteComment(event.getUserComments());
                    break;
                case MyInfoConst.EVENT_BUS_ADD_COMMENT:
                    handleAddComment(event);
                    break;
                case MyInfoConst.EVENT_BUS_VIEW_DETAIL:
                    goToCommentDetailActivity(event.getUserComments());
                    break;
            }
        }
    }

    /**
     * 看评论详情
     *
     * @param userComments
     */
    private void goToCommentDetailActivity(UserComments userComments) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Router.COMMENT_DETAIL_KEY, userComments);
        Router.getInstance().open(CommentDetailActivity.class, getActivity(), bundle);
    }

    /**
     * 增加评论
     *
     * @param event
     */
    private void handleAddComment(final CommentEvent event) {
        CommentInputDialog.showInputComment(getActivity(), "", new CommentInputDialog.CommentDialogListener() {
            @Override
            public void onClickPublish(Dialog dialog, EditText input, TextView btn) {

            }

            @Override
            public void onShow(int[] inputViewCoordinatesOnScreen) {
                mMyCommentListView.smoothScrollToPosition(event.getPosition());
            }

            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     * 删除一条评论
     */
    private void handleDeleteComment(UserComments userComments) {
        mMyInfoUserCase.deleteComment(LoginHelper.getInstance().getUserToken(), userComments.getId(),
                userComments.getUserId(), new BaseRequestCallback() {
                    @Override
                    public void onRequestFailed(BaseDomainData data) {
                        MessageTools.showErrorMessage(data);
                    }

                    @Override
                    public void onRequestSuccessful(String data) {
                        // 刷新
                        mMyCommentListView.clearAndFresh();
                    }

                    @Override
                    public void onNetError() {
                        ShowTools.toast("删除失败，请重试");
                    }
                });
    }
}
