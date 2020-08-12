package com.jbsx.view.main.util;

import com.jbsx.data.AppConstData;

public class PageUtils {
    public static int parseTabType(String tabType) {
        int type = AppConstData.TYPE_NAVI_MAIN;
        try {
            type = Integer.parseInt(tabType);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 根据当前资源的sourceType分配跳转到视频还是音频
     *
     * @param resType
     * @param hasChild
     * @return
     */
    public static Integer getPageType(int resType, boolean hasChild) {
        switch (resType) {
            case AppConstData.TYPE_RESOURCE_VIDEO:
                return hasChild ? AppConstData.PAGE_TYPE_VIDEO_1 : AppConstData.PAGE_TYPE_VIDEO_FEED;

            case AppConstData.TYPE_RESOURCE_ALBUM:
            default:
                return hasChild ? AppConstData.PAGE_TYPE_ALBUM_1 : AppConstData.PAGE_TYPE_ALBUM_2;
        }
    }
}
