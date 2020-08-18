package com.jbsx.view.main.util;

import android.content.Context;

import com.jbsx.R;
import com.jbsx.app.MainApplicationLike;
import com.jbsx.utils.StatisticsReportUtil;
import com.jbsx.utils.UiTools;

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
}
