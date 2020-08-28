package com.jbsx.view.main.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.StatisticsReportUtil;
import com.jbsx.utils.UiTools;

import java.util.List;

public class MainViewUtil {
    /**
     * 计算feed item的图片尺寸
     *
     * @param gridColumn
     * @param padding
     * @return
     */
    public static  int calculateFeedImageHeight(int gridColumn, int padding) {
        return calculateFeedImageHeight(gridColumn, padding, 1f);
    }

    /**
     * 计算feed item的图片尺寸
     *
     * 高度 = multi*宽度
     *
     * @param gridColumn
     * @param padding
     * @return
     */
    public static int calculateFeedImageHeight(int gridColumn, int padding, float multi) {
        float itemPadding = getDimen(R.dimen.video_feed_item_padding);
        float width = (StatisticsReportUtil.getScreenWidth() - 2*gridColumn*itemPadding - 2*padding) / gridColumn;
        return (int)(width * multi);
    }

    public static float getDimen(int dimenId) {
        return MainApplicationLike.getAppContext().getResources().getDimension(dimenId);
    }

    /**
     * 本地资源页面用到的添加顶部按钮
     *
     * @param container
     * @param classifyList
     * @param listener
     */
    public static void getLocalHeaderView(ViewGroup container, List<String> classifyList,
                               final RadioGroup.OnCheckedChangeListener listener) {
        boolean hasData = (classifyList != null && classifyList.size() > 0);
        container.setVisibility(hasData ? View.VISIBLE : View.GONE);

        Context context = container.getContext();
        if (hasData) {
            RadioGroup group = new RadioGroup(context);
            group.setOrientation(RadioGroup.HORIZONTAL);
            for (int i = 0; i < classifyList.size(); i++) {
                RadioButton button = new RadioButton(context);
                button.setTextColor(0xffffffff);
                button.setText(classifyList.get(i));
                button.setButtonDrawable(null);
                button.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                        MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_size));
                button.setPadding((int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_padding_left),
                        (int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_padding_top),
                        (int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_padding_right),
                        (int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_padding_bottom));
                button.setBackgroundResource(R.drawable.radio_button_local_top_button);
                button.setGravity(Gravity.CENTER);

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = (int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_container_margin_right);
                params.topMargin = (int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_container_margin_top);
                params.bottomMargin = (int)MainViewUtil.getDimen(R.dimen.local_image_radio_head_text_container_margin_bottom);
                params.gravity = Gravity.CENTER;
                group.addView(button, params);
            }
            // 默认选中第一个，且在事件注册之前
            group.check(group.getChildAt(0).getId());

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (listener != null) {
                        listener.onCheckedChanged(radioGroup, i);
                    }
                }
            });

            container.removeAllViews();
            container.addView(group);
        }
    }

    /**
     * 获得view在布局xml中设定的高度
     *
     * @param view
     * @return
     */
    public static int getMeasureHeight(View view) {
        int[] info = measureView(view);
        return info[1];
    }

    public static int[] measureView(final View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }
}
