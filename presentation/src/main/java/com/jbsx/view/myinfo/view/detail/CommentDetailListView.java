package com.jbsx.view.myinfo.view.detail;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.R;
import com.jbsx.app.BaseEvent;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.utils.ErroBarHelper;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.MyCommentData;
import com.jbsx.view.myinfo.data.UpdateListEvent;
import com.jbsx.view.myinfo.data.UserComments;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 评论详情中的评论列表
 */
public class CommentDetailListView extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private CommentDetailListAdapter mAdapter;
    private MyCommentData mResultData;
    private UserComments mRequestData;

    /** 显示评论数 */
    private TextView mTvCommentCount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mRequestData = bundle.getParcelable(ARGUMENT);
        }
    }

    public static CommentDetailListView newInstance(UserComments argument) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT, argument);

        CommentDetailListView contentFragment = new CommentDetailListView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    public void setMainCommentData(UserComments commentData) {
        mRequestData = commentData;
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    /**
     * 评论总数
     *
     * @return
     */
    public int getTotalCommentCount() {
        int count = 0;
        if (mResultData != null && mResultData.getPayload() != null) {
            count = mResultData.getPayload().getTotalElements();
        }

        return count;
    }

    public void setCommentCountView(TextView view) {
        mTvCommentCount = view;
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new CommentDetailListAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        BaseRequestEntity entity = HttpRequestPool.getCommentDetailListEntity(
                LoginHelper.getInstance().getUserToken(), mRequestData.getId(),
                mRequestData.getSpecialAlbumId(), mRequestData.getSpecialSingleId(),
                mRequestData.getUserId(), pageIndex);
        return entity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        mResultData = gson.fromJson(result, MyCommentData.class);

        if (mResultData != null && mResultData.getPayload() != null) {
            List<UserComments> list = mResultData.getPayload().getUserComments();

            // 设置评论数量
            if (mTvCommentCount != null) {
                mTvCommentCount.setText("评论（" + getTotalCommentCount() + "条）");
            }

            return list;
        }

        return new ArrayList<UserComments>();
    }

    @Override
    public void handleErrorBarOfEmptyData() {
        ErroBarHelper.addErroBar(mListView, "还没有评论呢，添加一条吧！");
    }

    @Override
    public int getPageSize() {
        return ConstData.DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean needLoadByPage() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateMessageEvent(UpdateListEvent event) {
        clearAndFresh();
    };
}
