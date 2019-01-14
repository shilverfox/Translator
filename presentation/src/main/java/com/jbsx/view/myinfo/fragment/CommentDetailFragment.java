package com.jbsx.view.myinfo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.UiTools;
import com.jbsx.view.main.entity.SpecialSingles;
import com.jbsx.view.main.view.RepertoryListHolder;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.util.CommentUiTools;
import com.jbsx.view.myinfo.view.comment.MyCommentListView;
import com.jbsx.view.myinfo.view.comment.MyCommentViewHolder;
import com.jbsx.view.myinfo.view.detail.CommentDetailListHolder;
import com.jbsx.view.myinfo.view.detail.CommentDetailListView;
import com.jbsx.view.search.view.SearchResultHolder;

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
        initUserInfo();
        initVideoInfo();
    }

    private void initUserInfo() {
        View userView = mRootView.findViewById(R.id.layout_comment_user);
        CommentDetailListHolder viewHolder = new CommentDetailListHolder(mContext, userView);
        viewHolder.findViews(userView);
        viewHolder.drawViews(mRequestData, 0);
        viewHolder.hideCommentAndNb();

        // 单独处理本页面评论和赞
        TextView tvComment = mRootView.findViewById(R.id.tv_detail_comment_count);
        TextView tvNbCount = mRootView.findViewById(R.id.tv_detail_nb_count);
        tvComment.setText(mRequestData.getCommentReply() + "");
        tvNbCount.setText(mRequestData.getCommentLove() + "");
    }

    /**
     * 当前数据中是否包含Single信息
     *
     * @return
     */
    private boolean hasSingleInfo() {
        return mRequestData.getSingle() != null && !TextUtils.isEmpty(mRequestData.getSingle().getId());
    }

    private void initVideoInfo() {
        View videoView = mRootView.findViewById(R.id.layout_comment_video);

        // 是否有视频数据
        if (hasSingleInfo()) {
            RepertoryListHolder viewHolder = new RepertoryListHolder(mContext, videoView);

            // 设置视频图片大小
            ImageView videoPic = videoView.findViewById(R.id.iv_video_item_image);
            CommentUiTools.setSmallVideoView(videoPic);

            // 不显示主讲
            View hideView = videoView.findViewById(R.id.iv_video_item_host);
            hideView.setVisibility(View.GONE);

            TextView name = videoView.findViewById(R.id.iv_video_item_description);
            name.setTextColor(ContextCompat.getColor(mContext, R.color.app_word_red));

            // 造holder需要的数据
            SpecialSingles specialSingles = new SpecialSingles();
            specialSingles.setSingle(mRequestData.getSingle());

            viewHolder.findViews(videoView);
            viewHolder.drawViews(specialSingles, 0);
        } else {
            // 没有视频信息，不显示视频
            videoView.setVisibility(View.GONE);
        }
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
