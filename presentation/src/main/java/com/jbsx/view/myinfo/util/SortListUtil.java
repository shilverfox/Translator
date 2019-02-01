package com.jbsx.view.myinfo.util;

import android.text.TextUtils;

import com.jbsx.utils.DateFormatUtil;
import com.jbsx.view.main.entity.UserSingle;

import java.util.List;

/**
 * 分组工具，我的观看历史里区分昨天今天和将来
 */
public class SortListUtil {
    /**
     * 将传入的数据进行分组，默认数据为按时间排好序的
     *
     * @param source
     * @param newData
     */
    public static List<UserSingle> makeWrappedList(List<UserSingle> source, List<UserSingle> newData){
        if (newData != null && source != null) {
            // 遍历每个一个元素
            for (UserSingle userSingle : newData) {
                /*
                  获得当前的日期描述，今天昨天更早，并替换掉原有的时间内容
                  将来如果需要更细粒度的显示时间，这里可以新更加字段去处理分组信息
                */
                String dateDescription = DateFormatUtil.getDateDetail(userSingle.getCreatedAt());
                userSingle.setCreatedAt(dateDescription);
            }
        }

        return newData;
    }

    /**
     * 是否已经存在同名分组
     *
     * @param source
     * @param description
     * @return
     */
    private static boolean hasTheSameDescription(List<UserSingle> source, String description) {
        if (source == null || source.isEmpty() || TextUtils.isEmpty(description)) {
            return false;
        }

        for (UserSingle userSingle : source) {
            if (description.equals(userSingle.getCreatedAt())) {
                return true;
            }
        }

        // 没找到
        return false;
    }

    /**
     * 分组名称是否有效
     *
     * @param groupName
     * @return
     */
    public static boolean groupIsValid(String groupName) {
        return DateFormatUtil.TODAY.equals(groupName)
                || DateFormatUtil.YESTERDAY.equals(groupName)
                || DateFormatUtil.BEFORE_YESTERDAY.equals(groupName);
    }
}
