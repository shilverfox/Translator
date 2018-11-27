package com.jbsx.view.myinfo.view.message;

import android.content.Context;
import android.os.Bundle;

import com.app.domain.net.data.ConstData;
import com.app.domain.net.data.HttpRequestPool;
import com.app.domain.net.model.BaseRequestEntity;
import com.google.gson.Gson;
import com.jbsx.customview.listFragment.CommonListFragment;
import com.jbsx.customview.listFragment.CommonListFragmentAdapter;
import com.jbsx.view.login.util.LoginHelper;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.ViewHistoryData;
import com.jbsx.view.myinfo.data.MyMessageData;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的消息列表
 */
public class MyInfoMessageListView extends CommonListFragment {
    public static final String ARGUMENT = "argument";

    private String mMessageType;
    private MyInfoMessageAdapter mAdapter;
    private MyMessageData mMessageData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mMessageType = bundle.getString(ARGUMENT);
        }
    }

    public static MyInfoMessageListView newInstance(String argument) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT, argument);

        MyInfoMessageListView contentFragment = new MyInfoMessageListView();
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
        mAdapter = new MyInfoMessageAdapter(mContext);
        return mAdapter;
    }

    @Override
    public BaseRequestEntity getRequestEntity(int pageIndex) {
        BaseRequestEntity entity = HttpRequestPool.getMyMessageEntity(
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
        mMessageData = gson.fromJson(result, MyMessageData.class);

        if (mMessageData != null && mMessageData.getPayload() != null) {
            List<MyMessageData.MessageList> list = mMessageData.getPayload().getMessageList();
            addMessageType(list);
            return list;
        }

        return new ArrayList<MyMessageData.MessageList>();
    }

    /**
     * 是回复的消息还是点赞的消息
     *
     * @param list
     */
    public void addMessageType(List<MyMessageData.MessageList> list) {
        if (list != null) {
            for(MyMessageData.MessageList message : list) {
                message.setMessageType(mMessageType);
            }
        }
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
