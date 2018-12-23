package com.jbsx.view.myinfo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jbsx.R;
import com.jbsx.app.BaseFragmentActivity;
import com.jbsx.customview.TitleBar;
import com.jbsx.utils.FileUtils;
import com.jbsx.utils.RecyclerViewHelper;
import com.jbsx.view.myinfo.adapter.MyInfoAdapter;
import com.jbsx.view.myinfo.adapter.MyInfoBigImageAdapter;
import com.jbsx.view.myinfo.data.MyInfoConst;
import com.jbsx.view.myinfo.entity.MyInfoBigItem;
import com.jbsx.view.myinfo.entity.MyInfoItem;
import com.jbsx.view.myinfo.fragment.MyMessageFragment;
import com.jbsx.view.setting.AboutActivity;
import com.jbsx.view.setting.data.AboutData;
import com.jbsx.view.setting.data.SettingConst;
import com.jbsx.view.web.WebHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的消息页面
 *
 * 这个页面偷懒了, fragment覆盖在列表项上面, 用的控制fragment容器的显示和隐藏, 没有跳页面
 */
public class MyMessageActivity extends BaseFragmentActivity {
    private TitleBar mTopBarLayout;
    private MyInfoAdapter mAdapter;
    private RecyclerView mRvContent;
    private LinearLayoutManager linearLayoutManager;
    private View mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_message_activity);

        findViews();
        initMessageList();
        initTitleBar();
        registerEvents();
        initData();
    }

    private void findViews() {
        mTopBarLayout = findViewById(R.id.layout_title_bar_container);
        mRvContent = findViewById(R.id.rv_message_items);
        mFragmentContainer = findViewById(R.id.layout_common_fragment_container);
    }

    private void initTitleBar() {
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setCenterTitle("我的消息");
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBackEvent();
            }
        });
    }

    private void initMessageList() {
        mAdapter = new MyInfoAdapter(this, R.layout.myinfo_fragment_item);
        linearLayoutManager = new LinearLayoutManager(mContext);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setHasFixedSize(true);
        mRvContent.setLayoutManager(linearLayoutManager);
        mRvContent.addItemDecoration(RecyclerViewHelper.getDivider(mContext, true));
    }

    private void initData() {
        // 制造数据
        List<MyInfoItem> items = new ArrayList<MyInfoItem>();

        MyInfoItem systemMessage = new MyInfoItem(MyInfoConst.MESSAGE_TYPE_SYSTEM, "系统消息");
        systemMessage.setRightDeliver(true);

        MyInfoItem responseMessage = new MyInfoItem(MyInfoConst.MESSAGE_TYPE_RESPONSE, "回复消息");
        responseMessage.setRightDeliver(true);

        MyInfoItem thumbMessage = new MyInfoItem(MyInfoConst.MESSAGE_TYPE_THUMB, "被赞消息");
        thumbMessage.setRightDeliver(true);

        items.add(systemMessage);
        items.add(responseMessage);
        items.add(thumbMessage);

        mAdapter.setList(items);
    }

    private void registerEvents() {
        mAdapter.setOnMyItemClickListener(new MyInfoAdapter.OnMyItemClickListener() {
            @Override
            public void onClick(int position) {
                MyInfoItem item = mAdapter.getDatas().get(position);
                if (item == null) {
                    return;
                }
                gotoView(item.getId());
            }
        });
    }

    private void gotoView(final int messageType) {
        Fragment fragment = null;
        switch (messageType) {
            case MyInfoConst.MESSAGE_TYPE_SYSTEM:
                // @// TODO: 2018/12/23 系统消息单独处理 
//                fragment = MyMessageFragment.newInstance(messageType);
                break;
            case MyInfoConst.MESSAGE_TYPE_RESPONSE:
            case MyInfoConst.MESSAGE_TYPE_THUMB:
                fragment = MyMessageFragment.newInstance(messageType);
                break;
            default:
                break;
        }

        if (fragment != null) {
            replaceFragment(fragment);
        } else {
            showFragmentView(false);
        }
    }


    @Override
    public void onBackPressed() {
        handleBackEvent();
    }

    /**
     * 处理返回事件
     */
    private void handleBackEvent() {
        if (isFragmentViewVisible()) {
            // 当前正在显示fragment，则关闭
            showFragmentView(false);
        } else {
            // 无fragment在显示，关闭activity
            finish();
        }
    }

    public void replaceFragment(Fragment fragment) {
        if (fragment != null && this != null) {
            showFragmentView(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layout_common_fragment_container, fragment).commitAllowingStateLoss();
        }
    }

    private void showFragmentView(boolean show) {
        mFragmentContainer.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    /**
     * 是否可见
     *
     * @return
     */
    private boolean isFragmentViewVisible() {
        return mFragmentContainer.getVisibility() == View.VISIBLE;
    }
}
