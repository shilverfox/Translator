package com.translatmaster.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.translatmaster.R;
import com.translatmaster.utils.ColorTools;

/**
 * 通用导航栏控件
 *
 * 左侧默认支持2个icons （back, sperator, close）
 * 右侧icons容器自己添加 (addRightMultiIcon，添加完毕调用commit注册事件监听)
 * 右侧一个文字按钮 (mTvRightButton)
 * 一个图片按钮(mIvRightSingleIcon)
 * 居中标题(mTvCenterTitle)
 * 居左标题(mTvLeftTitle)
 * 居左输入框(mEtLeftInput)
 *
 * 参考代码：
 * 1. 布局：   <include layout="@layout/widget_title_bar" />
 * 2. 声明：   private PdjTitleBar mTopBarLayout;
 * 3. 初始化： mTopBarLayout = (PdjTitleBar) view.findViewById(R.id.layout_title_bar_container);
 * 4. 调用setXX设置控件内容及事件，调用showXX显示对应部分
 *
 * 默认所有控件元素都隐藏，可以单独控制每个组件元素的显示和隐藏，也可以通过set方法直接传入事件和resId使其自动显示出来。
 *
 * @author Li Jian 2018/9/6
 */
public class TitleBar extends RelativeLayout {

    /** 左侧按键区 */
    private View mLayoutLeftContainer;
    private ImageView mIvBack;
    private View mViewSeparator;
    private ImageView mIvClose;

    /** 居中标题 */
    private TextView mTvCenterTitle;

    /** 居左标题 */
    private TextView mTvLeftTitle;

    /** 居左输入框 */
    private EditText mEtLeftInput;

    /** 右侧按键区 */
    private View mLayoutRightContainer;
    private TextView mTvRightButton;
    private ImageView mIvRightSingleIcon;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        findView();
        hideAllView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    private void hideAllView() {
        // 初始隐藏部分view
        setViewGone(mIvBack, true);
        setViewGone(mViewSeparator, true);
        setViewGone(mIvClose, true);
        setViewGone(mTvLeftTitle, true);
        setViewGone(mTvCenterTitle, true);
        setViewGone(mEtLeftInput, true);
        setViewGone(mTvRightButton, true);
        setViewGone(mIvRightSingleIcon, true);
    }

    private void findView() {
        // 左侧
        mLayoutLeftContainer = findViewById(R.id.layout_title_bar_left_container);
        mIvBack = (ImageView)findViewById(R.id.iv_title_bar_back);
        mViewSeparator = findViewById(R.id.view_title_bar_separator);
        mIvClose = (ImageView)findViewById(R.id.iv_title_bar_close);

        // 中间
        mTvCenterTitle = (TextView)findViewById(R.id.tv_title_bar_middle_title);

        // 中间靠左
        mTvLeftTitle = (TextView)findViewById(R.id.tv_title_bar_left_title);
        mEtLeftInput = (EditText) findViewById(R.id.et_title_bar_left_input);

        // 右侧
        mLayoutRightContainer = findViewById(R.id.layout_title_bar_right_container);
        mTvRightButton = (TextView)findViewById(R.id.layout_title_bar_right_button);
        mIvRightSingleIcon = (ImageView)findViewById(R.id.iv_title_bar_right_single_icon);
    }

    /**
     * 显示/隐藏左边整个按键区域
     *
     * @param show
     */
    public void showLeftContainer(boolean show) {
        setViewGone(mLayoutLeftContainer, !show);
    }

    /**
     * 显示/隐藏左边返回按键
     *
     * @param show
     */
    public void showBackButton(boolean show) {
        setViewGone(mIvBack, !show);
    }

    public ImageView getBackButton() {
        return mIvBack;
    }

    /**
     * 显示/隐藏左边按键分割线
     *
     * @param show
     */
    public void showLeftSeparator(boolean show) {
        setViewGone(mViewSeparator, !show);
    }

    /**
     * 显示/隐藏左边关闭按键
     *
     * @param show
     */
    public void showCloseButton(boolean show) {
        setViewGone(mIvClose, !show);
    }

    /**
     * 左边“返回”按键资源及点击事件
     * 自动设置按键可见，但要注意先后顺序，如果前面让按键隐藏，后面调用这个方法，则按键又被显示
     *
     * @param resId 图片资源id
     */
    public void setBackButton(int resId, final View.OnClickListener clickListener) {
        showBackButton(true);
        mIvBack.setImageResource(resId);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    /**
     * 设置返回按键事件
     * 只设置事件，不关注可见性，需要手动调用显示、隐藏方法
     *
     * @param clickListener
     */
    public void setBackButton(final View.OnClickListener clickListener) {
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    /**
     * 左边“关闭”按键资源及点击事件
     * 自动设置按键可见，但要注意先后顺序，如果前面让按键隐藏，后面调用这个方法，则按键又被显示
     *
     * @param resId 图片资源id
     */
    public void setCloseButton(int resId, final View.OnClickListener clickListener) {
        showCloseButton(true);
        mIvClose.setImageResource(resId);
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }


    /**
     * 设置返回按键事件
     * 只设置事件，不关注可见性，需要手动调用显示、隐藏方法
     *
     * @param clickListener
     */
    public void setCloseButton(final View.OnClickListener clickListener) {
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    public ImageView getColoseButton() {
        return mIvClose;
    }

    /**
     * 设置居中标题
     *
     * @param title
     */
    public void setCenterTitle(String title, int textColor) {
        if (!TextUtils.isEmpty(title)) {
            showCenterTitle(true);
            mTvCenterTitle.setText(title);
            mTvCenterTitle.setTextColor(textColor);
        }
    }

    /**
     * 设置居中标题
     * 默认颜色
     *
     * @param title
     */
    public void setCenterTitle(String title) {
        setCenterTitle(title, 0xff333333);
    }

    public TextView getCenterTitle() {
        return mTvCenterTitle;
    }

    /**
     * 居中标题是否可见
     *
     * @return
     */
    public boolean isCenterTitleVisible() {
        return mTvCenterTitle.getVisibility() == View.VISIBLE;
    }

    public void showCenterTitle(boolean show) {
        setViewGone(mTvCenterTitle, !show);
    }

    /**
     * 设置居左标题
     *
     * @param title
     * @param drawLeftId drawable left
     * @param drawRightId drawable right
     */
    public void setLeftTitle(String title, int drawLeftId, int drawRightId, int textColor) {
        if (!TextUtils.isEmpty(title)) {
            showLeftTitle(true);
            mTvLeftTitle.setText(title);
            setLeftTitleColor(textColor);
            setLeftTitleDrawable(drawLeftId, drawRightId);
        }
    }

    public TextView getLeftTitle() {
        return mTvLeftTitle;
    }

    public void setLeftTitleColor(int textColor) {
        mTvLeftTitle.setTextColor(textColor);
    }

    /**
     * 设置左侧标题drawable
     *
     * @param drawLeftId
     * @param drawRightId
     */
    public void setLeftTitleDrawable(int drawLeftId, int drawRightId) {
        mTvLeftTitle.setCompoundDrawables(getDrawable(drawLeftId), null, getDrawable(drawRightId), null);
    }

    public void setLeftTitleDrawable(Drawable drawLeft, Drawable drawRight) {
        mTvLeftTitle.setCompoundDrawables(drawLeft, null, drawRight, null);
    }

    public void setLeftTitlePadding(int left, int top, int right, int bottom) {
        mTvLeftTitle.setPadding(left, top, right, bottom);
    }

    /**
     * 设置drawable padding，单位px
     *
     * @param paddingInPx
     */
    public void setLeftTitleDrawablePadding(int paddingInPx) {
        mTvLeftTitle.setCompoundDrawablePadding(paddingInPx);
    }

    /**
     * 设置居左标题
     */
    public void setLeftTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            showLeftTitle(true);
            mTvLeftTitle.setText(title);
            Drawable[] drawables = mTvLeftTitle.getCompoundDrawables();
            if (drawables != null && drawables.length >= 4) {
                mTvLeftTitle.setCompoundDrawables(drawables[0], null, drawables[2], null);
            }
        }
    }

    public void setLeftTitleSize(int fontSize) {
        mTvLeftTitle.setTextSize(fontSize);
    }

    private void showLeftTitle(boolean show) {
        setViewGone(mTvLeftTitle, !show);
    }

    /**
     * 居左标题点击事件
     *
     * @param clickListener
     */
    public void setLeftClickListener(final View.OnClickListener clickListener) {
        mTvLeftTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    /**
     * 设置居左输入框
     *
     * @param hint
     * @param drawLeftId
     */
    public void setLeftInput(String hint, int drawLeftId, final View.OnClickListener onClickListener) {
        showLeftInput(true);

        if (!TextUtils.isEmpty(hint)) {
            mEtLeftInput.setHint(hint);
        }

        mEtLeftInput.setCompoundDrawables(getDrawable(drawLeftId), null, null, null);
        mEtLeftInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
            }
        });
    }

    public void showLeftInput(boolean show) {
        setViewInVisible(mEtLeftInput, !show);
    }

    public EditText getLeftInput() {
        return mEtLeftInput;
    }

    /**
     * 设置背景颜色，渐变色
     *
     * @param startColor
     * @param endColor
     */
    public void setBackgroundColor(String startColor, String endColor) {
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.BL_TR,
                new int[]{ColorTools.parseColor(startColor), ColorTools.parseColor(endColor)});
        setBackgroundDrawable(drawable);
    }


    public ImageView getRightSingleIcon() {
        return mIvRightSingleIcon;
    }

    /**
     * 右侧文字按钮
     *
     * @param label
     * @param clickListener
     */
    public void setRightButton(String label, final View.OnClickListener clickListener) {
        if (!TextUtils.isEmpty(label)) {
            showRightButton(true);
            mTvRightButton.setText(label);
            mTvRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onClick(v);
                    }
                }
            });
        }
    }

    /**
     * 右侧独立按键
     * 当右侧只有一个按键时用这个
     *
     * @param resId
     * @param clickListener
     */
    public void setRightSingleIcon(int resId, final View.OnClickListener clickListener) {
        showRightSingleIcon(true);
        mIvRightSingleIcon.setImageResource(resId);
        mIvRightSingleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    clickListener.onClick(v);
                }
            }
        });
    }

    public void showRightSingleIcon(boolean show) {
        setViewInVisible(mIvRightSingleIcon, !show);
    }

    public void showRightButton(boolean show) {
        setViewGone(mTvRightButton, !show);
    }

    public TextView getRightButton() {
        return mTvRightButton;
    }

    private void setViewGone(View view, boolean gone) {
        if (view != null) {
            view.setVisibility(gone ? View.GONE : View.VISIBLE);
        }
    }

    private void setViewInVisible(View view, boolean hide) {
        if (view != null) {
            view.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
        }
    }

    private Drawable getDrawable(int resId) {
        Drawable drawable = null;
        try {
            drawable = getResources().getDrawable(resId);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return drawable;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
