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
}
