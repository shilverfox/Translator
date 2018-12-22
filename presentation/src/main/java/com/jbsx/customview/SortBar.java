package com.jbsx.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.jbsx.R;
import com.jbsx.utils.UiTools;

import java.util.List;

/**
 * Created by Jies on 2018/7/31.
 */

public class SortBar extends HorizontalScrollView implements View.OnClickListener {
    private int COLOR_TEXT_NORMAL;//未选中时的文案颜色
    private int COLOR_TEXT_HIGHLIGHTCOLOR;//选中时的文安颜色
    public static final int ORDER_ACS = 0;//正序
    public static final int ORDER_DESC = 1;//倒序
    private int ITEM_COUNT = -1;//展示的Item數量
    private float TEXT_SIZE;//Item中文案的大小
    LinearLayout rootView;
    notifyOutsideListener notifyOutsideListener;//通知外部的接口
    List<String> typeList;//排序的类型
    Drawable[] sortResource;//排序标识图片的数组 0为默认 1为正序 2为倒序
    String currentTag = "默认";//当前所处的排序标识 ，默认是第一个

    public SortBar(Context context) {
        this(context, null);
    }

    public SortBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性数组
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SortView);
        ITEM_COUNT = array.getInteger(R.styleable.SortView_sortview_count, -1);
        COLOR_TEXT_HIGHLIGHTCOLOR = array.getResourceId(R.styleable.SortView_sortview_highlightcolor, R.color.text_orange);
        COLOR_TEXT_NORMAL = array.getResourceId(R.styleable.SortView_sortview_normalcolor, R.color.text_gray);
        TEXT_SIZE = array.getDimension(R.styleable.SortView_sortview_textsize, 16);
        initView();
    }


    //初始化先添加一個LinearLayout來承載接下来的View
    private void initView() {
        rootView = new LinearLayout(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(lp);
        rootView.setOrientation(LinearLayout.HORIZONTAL);
        addView(rootView);
    }

    //通過调用这个方法来 添加视图。
    public void setType(List<String> type, Drawable[] drawables) {
        this.typeList = type;
        this.sortResource = drawables;


        //没设置默认值的时候，设置为传入的List的size
        if (ITEM_COUNT == -1 && typeList.size() > 0) {
            ITEM_COUNT = typeList.size();
        }

        for (String sortType : typeList) {
            rootView.addView(generalRelativeLayout(sortType));
        }
        //添加完布局后 直接调用点击第一个类型来触发外部逻辑
        onClick(rootView.getChildAt(0));
    }

    /**
     * 是否为倒叙
     *
     * @param desOrAsc
     * @return
     */
    public boolean isDescend(int desOrAsc) {
        return desOrAsc == ORDER_DESC;
    }


    public RelativeLayout generalRelativeLayout(String text) {
        //生成一个Relativelayout来承载一个TextView跟一个ImageView
        // 再通过设置Relativelayout的点击事件，来改变每个TextView跟ImageView的状态跟颜色并且调用通知外部的监听
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        lp.width = getScreenWidth() / ITEM_COUNT;
        relativeLayout.setLayoutParams(lp);

        //生成一个TextView 并处理TextView在Relativelayout中的位置
        TextView tv = new TextView(getContext());
        RelativeLayout.LayoutParams tvlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        tvlp.addRule(RelativeLayout.CENTER_IN_PARENT);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(getResources().getColor(COLOR_TEXT_NORMAL));
        tv.setText(text);
        tv.setId(R.id.withText);
        tv.setTextSize(UiTools.px2sp(TEXT_SIZE));
        tv.setLayoutParams(tvlp);
        relativeLayout.addView(tv);

        //生成一個ImageView 並且放置在TextView的右边
        //当前的TextView如果是默认，则没有ImageView
        if (!text.equals("默认")) {
            ImageView iv = new ImageView(getContext());
            iv.setPadding(UiTools.dip2px(4), 0, 0, 0);
            RelativeLayout.LayoutParams ivlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            ivlp.addRule(RelativeLayout.RIGHT_OF, R.id.withText);
            ivlp.addRule(RelativeLayout.CENTER_VERTICAL);
            iv.setLayoutParams(ivlp);
            iv.setImageDrawable(sortResource[0]);
            relativeLayout.addView(iv);
        }

        //設置一個Relativelayout的Tag 用来提供给点击监听时识别是哪个Relativelayout
        relativeLayout.setTag(text);
        relativeLayout.setOnClickListener(this);
        return relativeLayout;
    }


    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels - getPaddingLeft() - getPaddingRight();
    }

    @Override
    public void onClick(View v) {
        //遍历布局中的子View 就是遍历所有的Relativelayout
        for (int i = 0; i < rootView.getChildCount(); i++) {
            View itemRelativelayout = rootView.getChildAt(i);
            if (itemRelativelayout instanceof RelativeLayout) {
                //遍历每一个Relativelayout中的子View
                for (int j = 0; j < ((RelativeLayout) itemRelativelayout).getChildCount(); j++) {
                    View childview = ((RelativeLayout) itemRelativelayout).getChildAt(j);
                    //当是TextView的时候 判断是不是点击的那个Relativelayout下面的TextView 来设置颜色
                    if (childview instanceof TextView) {
                        if (v.getTag().equals(itemRelativelayout.getTag())) {
                            ((TextView) childview).setTextColor(getResources().getColor(COLOR_TEXT_HIGHLIGHTCOLOR));
                        } else {
                            ((TextView) childview).setTextColor(getResources().getColor(COLOR_TEXT_NORMAL));
                        }
                    } else if (childview instanceof ImageView) {
                        //當是ImageView的時候，判断是不是点击的那个Relativelayout
                        if (v.getTag().equals(itemRelativelayout.getTag())) {
                            //如果是点击的relativelayout 通过imageview的图片资源来判断状态的变化，是从默认变为正序，还是从正序变为倒序。还是从倒序变为正序
                            if (itemRelativelayout.getTag().equals(currentTag)) {
                                if (((ImageView) childview).getDrawable().equals(sortResource[1])) {
                                    ((ImageView) childview).setImageDrawable(sortResource[2]);
                                    if (notifyOutsideListener != null) {
                                        notifyOutsideListener.notifySort((String) v.getTag(), ORDER_ACS);
                                    }
                                } else {
                                    ((ImageView) childview).setImageDrawable(sortResource[1]);
                                    if (notifyOutsideListener != null) {
                                        notifyOutsideListener.notifySort((String) v.getTag(), ORDER_DESC);
                                    }
                                }
                            } else {
                                ((ImageView) childview).setImageDrawable(sortResource[1]);
                                if (notifyOutsideListener != null) {
                                    notifyOutsideListener.notifySort((String) v.getTag(), ORDER_DESC);
                                }
                            }

                        } else {
                            ((ImageView) childview).setImageDrawable(sortResource[0]);
                        }
                    }
                }
            }
        }

        currentTag = (String) v.getTag();
        if (v.getTag().equals("默认")) {
            if (notifyOutsideListener != null) {
                notifyOutsideListener.notifySort((String) v.getTag(), ORDER_DESC);
            }
        }
    }


    public SortBar.notifyOutsideListener getNotifyOutsideListener() {
        return notifyOutsideListener;
    }

    public void setNotifyOutsideListener(SortBar.notifyOutsideListener notifyOutsideListener) {
        this.notifyOutsideListener = notifyOutsideListener;
    }

    //通知外部点击哪个选项的接口
    public interface notifyOutsideListener {
        //第一个参数为那种类型的排序，第二个是正序还是倒序
        void notifySort(String tag, int desOrasc);
    }
}
