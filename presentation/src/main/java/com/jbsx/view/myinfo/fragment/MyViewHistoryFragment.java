package com.jbsx.view.myinfo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.data.ConstData;
import com.app.domain.net.interactor.MyInfoUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.TitleBar;
import com.jbsx.view.main.entity.Single;
import com.jbsx.view.main.entity.UserSingle;
import com.jbsx.view.main.entity.ViewHistoryData;
import com.jbsx.view.myinfo.contact.MyViewHistoryContact;
import com.jbsx.view.myinfo.presenter.MyViewHistoryPresenter;
import com.jbsx.view.myinfo.view.video.MyInfoVideoListView;

import java.util.ArrayList;
import java.util.List;

public class MyViewHistoryFragment extends BaseFragment implements MyViewHistoryContact.View {
    private View mRootView;
    private ViewGroup mLayoutHistory;
    private TitleBar mTopBarLayout;
    private ViewGroup mLayoutSelectAll;
    private ImageView mIvCheckAll;
    private TextView mTvCheckAllLabel;
    private TextView mTvDelete;

    public MyViewHistoryContact.Presenter mPresenter;

    /** 视频 */
    private MyInfoVideoListView mRepertoryList;

    /** 选中需要删除的id */
    private List<String> mSelectIds;

    public static boolean mCanSelectItem;
    public boolean mIsSelectAll;

    public MyViewHistoryFragment() {
        // Required empty public constructor

        // 因为他是静态的，每次一个新的Fragment要重新初始化
        mCanSelectItem = false;
    }

    public static MyViewHistoryFragment newInstance() {
        return new MyViewHistoryFragment();
    }

    @Override
    public void createPresenter() {
        MyInfoUserCase userCase = new MyInfoUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new MyViewHistoryPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.my_view_history_fragment, null, false);
        createPresenter();
        initViews();
        initEvents();
        initVideoListView();
        handleSelectorUI();
        handleSelectAllStuff();

        return mRootView;
    }

    private void initViews() {
        mLayoutSelectAll = mRootView.findViewById(R.id.layout_select_all);
        mLayoutHistory = mRootView.findViewById(R.id.layout_my_history_container);
        mTopBarLayout = mRootView.findViewById(R.id.layout_title_bar_container);
        mTvCheckAllLabel = mRootView.findViewById(R.id.tv_select_all_label);
        mTvDelete = mRootView.findViewById(R.id.tv_delete_label);
        mIvCheckAll = mRootView.findViewById(R.id.iv_select_all_check);
        mTopBarLayout.showBackButton(true);
        mTopBarLayout.setCenterTitle(getTitle());
    }

    public String getTitle() {
        return "观看历史";
    }

    private void initEvents() {
        mTopBarLayout.setBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackEvent();
            }
        });

        mTopBarLayout.setRightButton("编辑", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRightClick();
            }
        });

        mIvCheckAll.setOnClickListener(mSelectListener);
        mTvCheckAllLabel.setOnClickListener(mSelectListener);

        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDeleteClick();
            }
        });
    }

    /**
     * 获得当前哪些项被选中
     *
     * @return
     */
    private String[] getSelectedIds() {
        List<String> temp = new ArrayList();
        List<UserSingle> allVideo = mRepertoryList.getAdapter().getData();

        if (allVideo != null) {
            for(UserSingle item : allVideo) {
                Single single = item.getSingle();
                if (single.isCheck()) {
                    temp.add(single.getId());
                }
            }
        }

        String[] result = new String[temp.size()];
        temp.toArray(result);

        return result;
    }

    /**
     * 删除选中id
     */
    private void handleDeleteClick() {
        mPresenter.requestDelete(getSelectedIds());
    }

    /**
     * 全选按键事件
     */
    private View.OnClickListener mSelectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handleSelectAllClick();
        }
    };

    private void handleRightClick() {
        mCanSelectItem = !mCanSelectItem;
        handleSelectorUI();
        mRepertoryList.freshAdapter();
    }

    private void handleSelectAllClick() {
        mIsSelectAll = !mIsSelectAll;
        handleSelectAllStuff();

        if (mIsSelectAll) {
            mRepertoryList.selectAll();
        } else {
            mRepertoryList.unSelectAll();
        }
    }

    /**
     * 右上角编辑按钮，全选、删除显示逻辑
     */
    private void handleSelectorUI() {
        mTopBarLayout.setRightButtonText(mCanSelectItem ? "取消" : "编辑");
        mLayoutSelectAll.setVisibility(mCanSelectItem ? View.VISIBLE : View.GONE);
    }

    /**
     * 全选， 删除UI
     */
    private void handleSelectAllStuff() {
        mIvCheckAll.setImageResource(mIsSelectAll ? R.drawable.red_rect_check : R.drawable.rect_uncheck);
        mTvCheckAllLabel.setText(mIsSelectAll ? "取消全选" : "全选");
    }

    private void onBackEvent() {
        if (mContext != null) {
            ((Activity)mContext).finish();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initVideoListView() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mRepertoryList = MyInfoVideoListView.newInstance("");
        mRepertoryList.setBtnSelection(mTopBarLayout.getRightButton());
        mRepertoryList.setFunctionId(getSearchFunctionId());
        transaction.add(R.id.layout_my_history_container, mRepertoryList);
        transaction.commitAllowingStateLoss();
    }

    /**
     * 列表的function Id
     *
     * @return
     */
    public String getSearchFunctionId() {
        return ConstData.FUNCTION_ID_MY_HISTORY;
    }

    @Override
    public boolean isHistory() {
        return true;
    }
}
