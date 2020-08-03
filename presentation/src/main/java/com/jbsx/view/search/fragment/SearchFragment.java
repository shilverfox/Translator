package com.jbsx.view.search.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.data.net.repository.TaskManager;
import com.app.domain.net.interactor.SearchUserCase;
import com.jbsx.R;
import com.jbsx.app.BaseFragment;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.customview.LiuFlowLayout;
import com.jbsx.customview.dialog.JDDJDialogFactory;
import com.jbsx.utils.Router;
import com.jbsx.view.search.SearchResultActivity;
import com.jbsx.view.search.contact.SearchContact;
import com.jbsx.view.search.presenter.SearchPresenter;
import com.jbsx.view.search.util.SearchHelper;

import java.util.List;

/**
 * 搜索中间页，输入搜索词及显示搜索历史、热词
 */
public class SearchFragment extends BaseFragment implements SearchContact.View {
    private View mRootView;
    private SearchContact.Presenter mPresenter;
    private SearchHelper mSearchHelper;

    private TextView mTvClear;

    private LiuFlowLayout mLayoutHotWords;
    private LiuFlowLayout mLayoutSearchHistory;

    public SearchFragment() {
        // Required empty public constructor
        mSearchHelper = new SearchHelper();
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void createPresenter() {
        SearchUserCase userCase = new SearchUserCase(TaskManager.getTaskManager(),
                MainApplicationLike.getUiThread());
        mPresenter = new SearchPresenter(this, userCase);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mRootView = inflater.inflate(R.layout.search_fragment, null, false);

        createPresenter();
        initViews();
        initEvents();

        return mRootView;
    }

    private void initViews() {
        mLayoutSearchHistory = mRootView.findViewById(R.id.layout_search_history);
        mLayoutHotWords = mRootView.findViewById(R.id.layout_hot_words);
        mTvClear = mRootView.findViewById(R.id.tv_search_clear);
    }

    private void initEvents() {
        mTvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JDDJDialogFactory.createDialog(mContext).setCancelable(true)
                        .setMsg("确定清空全部搜索历史吗？")
                        .setFirstOnClickListener("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // Nothing to do
                            }
                        }).setSecondOnClickListener("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mSearchHelper.removeSearchHistory();
                                initHistoryView();
                            }
                        }).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        initHotWordsView();
        initHistoryView();
    }

    /**
     * 热门搜索
     * 暂不支持
     */
    private void initHotWordsView() {
        mLayoutHotWords.removeAllViews();

        String[] test = {"梅兰芳", "田连元", "梅宝久", "吴小如","叶长海","谭鑫培","杨美玉"};
        for(int i = 0; i < test.length; i++) {
            mLayoutHotWords.addView(makeHistoryItem(test[i]));
        }

        mLayoutHotWords.setVisibility(View.GONE);
    }

    /**
     * 显示搜索历史
     */
    private void initHistoryView() {
        mLayoutSearchHistory.removeAllViews();

        List<String> history = mSearchHelper.getSearchHistory();
        if (history != null) {
            for(String historyItem : history) {
                if (!TextUtils.isEmpty(historyItem)) {
                    mLayoutSearchHistory.addView(makeHistoryItem(historyItem));
                }
            }
        }
    }

    /**
     * 搜索项
     *
     * @param itemName
     * @return
     */
    private View makeHistoryItem(final String itemName) {
        LayoutInflater inflater = LayoutInflater.from(MainApplicationLike.getAppContext());
        View wordRootView = inflater.inflate(R.layout.search_word_item_view, null);
        TextView wordView = wordRootView.findViewById(R.id.tv_search_word);
        wordView.setText(itemName);

        wordRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSearch(itemName);
            }
        });

        return wordRootView;
    }

    private void handleSearch(String searchKey) {
        mSearchHelper.doSearch(searchKey);
        Router.getInstance().open(SearchResultActivity.class, getActivity());
    }
}
