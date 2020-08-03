package com.jbsx.view.myinfo.view.comment;

import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.myinfo.data.MyCommentData;
import com.jbsx.view.myinfo.data.MyMessageData;
import com.jbsx.view.myinfo.data.UserComments;
import com.jbsx.view.myinfo.view.message.MyInfoMessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的评论列表
 */
public class MyCommentListView extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private String mMessageType;
    private MyCommentAdapter mAdapter;
    private MyCommentData mMessageData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mMessageType = bundle.getString(ARGUMENT);
        }
    }

    public static MyCommentListView newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        MyCommentListView contentFragment = new MyCommentListView();
        contentFragment.setArguments(bundle);

        return contentFragment;
    }

    /**
     * 清空并重新加载数据
     */
    public void clearAndFresh() {
        loadAllData(true);
    }

    @Override
    public CommonListFragmentAdapter getAdapter(Context context) {
        mAdapter = new MyCommentAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        BaseRequestEntity entity = HttpRequestPool.getMyCommentEntity(
                LoginHelper.getInstance().getUserToken(), mMessageType, pageIndex);
        return entity;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public List parseList(String result) {
        Gson gson = new Gson();
        mMessageData = gson.fromJson(result, MyCommentData.class);

        if (mMessageData != null && mMessageData.getPayload() != null) {
            List<UserComments> list = mMessageData.getPayload().getUserComments();
            return list;
        }

        return new ArrayList<UserComments>();
    }

    @Override
    public int getPageSize() {
        return ConstData.DEFAULT_PAGE_SIZE;
    }

    @Override
    public boolean needLoadByPage() {
        return true;
    }
}
