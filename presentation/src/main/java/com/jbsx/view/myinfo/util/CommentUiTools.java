package com.jbsx.view.myinfo.util;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jbsx.utils.UiTools;

public class CommentUiTools {

    /**
     * 设置评论详情、我的评论中视频信息中的图片大小
     * 因为这里视频用的是公共的holder，需要手动调整指定大小
     *
     * @param imageView
     */
    public static void setSmallVideoView(ImageView imageView) {
        if (imageView != null) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.width = UiTools.dip2px(74);
            params.height = UiTools.dip2px(51);
        }
    }
}
