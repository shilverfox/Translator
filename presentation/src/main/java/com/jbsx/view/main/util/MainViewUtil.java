package com.jbsx.view.main.util;

import android.app.UiAutomation;
import android.content.Context;
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
     * @param gridColumn
     * @param padding
     * @return
     */
    public static  int calculateFeedImageHeight(int gridColumn, int padding) {
        int itemPadding = (int) MainApplicationLike.getAppContext().getResources().getDimension(R.dimen.video_feed_item_padding);
        float width = (StatisticsReportUtil.getScreenWidth() - (gridColumn - 1)* UiTools.dip2px(itemPadding) - 2*padding) / gridColumn;
        return (int)width;
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
                button.setPadding(UiTools.dip2px(20), 0, UiTools.dip2px(20), UiTools.dip2px(8));
                button.setBackgroundResource(R.drawable.radio_button_local_top_button);
                button.setGravity(Gravity.CENTER);

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = UiTools.dip2px(20);
                params.topMargin = UiTools.dip2px(10);
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
}
