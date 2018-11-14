package com.translatmaster.utils;

import com.translatmaster.data.AppConstData;
import com.translatmaster.view.main.entity.Celebrities;

import java.util.List;

public class DataUtil {
    /**
     * 找出谁是主讲人
     *
     * @param celebrities
     * @return
     */
    public static String getCelebrity(List<Celebrities> celebrities) {
        String name = "";
        if (celebrities != null && !celebrities.isEmpty()) {
            for(Celebrities yooMan : celebrities) {
                if (yooMan != null && yooMan.getType() == AppConstData.CELEBRITY_TYPE_ZHUJIANG) {
                    name = yooMan.getName();
                    break;
                }
            }
        }

        return name;
    }
}
