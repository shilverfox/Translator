package com.translatmaster.customview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.translatmaster.utils.LogTools;

import java.util.Vector;

public class TabGroupView extends LinearLayout implements OnClickListener {
    private Context mContext;
    private boolean isInited = false;
    private MyOnClickListener onClickListener;
    private View currentView = null;
    private Vector<Fragment> pageList;
    private TextView buyCart = null;
    private StateController stateController;

    private static final int tabGroup_type_page = 0;
    public static final int tabGroup_type_activity = 1;
    private FragmentManager fragmentManager;
    private int fragemntContainerId = 0;

    public int getFragemntContainerId() {
        return fragemntContainerId;
    }

    public void setFragemntContainerId(int fragemntContainerId) {
        this.fragemntContainerId = fragemntContainerId;
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public TabGroupView(Context context) {
        this(context, null);
        this.mContext = context;
        LogTools.e("TabGroupView", "context");
    }

    public TabGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LogTools.e("TabGroupView", "context attrs");
    }

    public void setPageList(Vector<Fragment> pageList) {
        this.pageList = pageList;
    }

    public Vector<Fragment> getPageList() {
        return this.pageList;
    }

    public void setTabText(String[] text) {
        for (int i = 0; i < this.getChildCount(); i++) {
            ViewGroup viewGroup = (ViewGroup) this.getChildAt(i);
            if (viewGroup.getChildCount() < 1) {
                break;
            }
            View view = viewGroup.getChildAt(0);
            if (view != null && view instanceof TextView) {
                TextView tv = (TextView) view;
                tv.setText(text[i]);
            } else {
                if (viewGroup.getChildCount() <= 1) {
                    break;
                }
                view = viewGroup.getChildAt(1);
                if (view != null && view instanceof TextView) {
                    TextView tv = (TextView) view;
                    tv.setText(text[i]);
                }
            }
        }
    }

    public TextView getTextTab(int index) {
        if (this.getChildCount() > index) {
            ViewGroup viewGroup = (ViewGroup) this.getChildAt(index);
            if (viewGroup.getChildCount() > 0) {
                View view = viewGroup.getChildAt(0);
                if (view != null && view instanceof TextView) {
                    return (TextView) view;
                }

                if (viewGroup.getChildCount() > 1) {
                    view = viewGroup.getChildAt(1);
                    if (view != null && view instanceof TextView) {
                        return (TextView) view;
                    }
                }
            }
        }
        return null;
    }

    /*
     * 是否隐藏 tab 中的icon 图片isGone true 隐藏isGone false 不隐藏
     */
    public void setTabIconGone(boolean isGone) {
        if (!isGone) {
            return;
        }
        for (int i = 0; i < this.getChildCount(); i++) {
            if (((ViewGroup) this.getChildAt(i)).getChildAt(0) instanceof ImageView) {
                ImageView iv = (ImageView) ((ViewGroup) this.getChildAt(i)).getChildAt(0);
                iv.setVisibility(View.GONE);
            } else {
                if (((ViewGroup) this.getChildAt(i)).getChildAt(1) instanceof ImageView) {
                    ImageView iv = (ImageView) ((ViewGroup) this.getChildAt(i)).getChildAt(1);
                    iv.setVisibility(View.GONE);
                }
            }
        }
    }

    public void setSelected(int index) {

        setSelected(index, null);
    }

    public void setSelected(int index, Object obj) {
        if (pageList != null && pageList.size() > index) {
            if (!isInited) {
                init();
                isInited = true;
            }

            View v = this.getChildAt(index);
            if (obj != null) {
                v.setTag(obj);
            }

            onClick(v);
        }
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!isInited) {
            init();
            isInited = true;
        }
    }

    // BuyCartDrawable buyCartDrawable;
    public void init() {
        for (int i = 0; i < this.getChildCount(); i++) {
            this.getChildAt(i).setOnClickListener(this);
            this.getChildAt(i).setId(i);
        }

    }

    public StateController getStateController() {
        if (stateController == null) {
            stateController = new StateController();
        }

        return stateController;
    }

    private void setTextNum(int num) {
    }

    public void setOnMyClickListener(MyOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public int getSelectIndex() {
        if (currentView != null)
            return currentView.getId();
        return 0;
    }

    public Fragment getCurrentPage() {
        if (pageList != null && pageList.size() > 1)
            return pageList.get(getSelectIndex());
        return null;
    }

    public Fragment getPage(int index) {
        if (pageList != null && pageList.size() > 1 && index < pageList.size())
            return pageList.get(index);
        return null;
    }

    @Override
    public void onClick(View v) {
        if (currentView != null) {
            // 非初始化操作，切换tab
            if (currentView.equals(v)) {
                // 点击当前已选中的view，不做响应
                return;
            } else {
                // 可以切换, 隐藏当前的Fragment
                hideShowingTabView();

                // 显示要切换的Fragment
                showClickedTabView(v);
            }
        } else {
            // 执行初始化添加tab操作
            showClickedTabView(v);
        }

        // 保存当前view
        currentView = v;

        // 回调
        if (onClickListener != null) {
            onClickListener.onClick(v);
        }
    }

    /**
     * 隐藏当前正在显示的Tab标签，用于做切换
     */
    private void hideShowingTabView() {
        try {
            if (pageList != null && currentView != null) {
                if (pageList.get(currentView.getId()).isAdded()) {
                    fragmentManager.beginTransaction().hide(pageList.get(currentView.getId())).commit();
                }

                currentView.setSelected(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示被点击的Tab及内容
     *
     * @param view
     */
    private void showClickedTabView(View view) {
        try {
            if (view != null) {
                view.setSelected(true);
                Fragment selectFragment = pageList.get(view.getId());
                if (selectFragment != null && !selectFragment.isDetached() && mContext != null && !((Activity) mContext).isFinishing()) {
                    if (!selectFragment.isAdded()) {
                        fragmentManager.beginTransaction().add(fragemntContainerId, selectFragment).commitAllowingStateLoss();
                    } else {
                        fragmentManager.beginTransaction().show(selectFragment).commitAllowingStateLoss();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class StateController {
        private int num;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
            setTextNum(num);
        }

        public void addNum() {
            setNum(num + 1);
        }
    }

    public interface MyOnClickListener {
        public boolean onClick(View v);
    }
}
