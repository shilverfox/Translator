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

                // 获得当前的日期描述，今天昨天更早
                String dateDescription = DateFormatUtil.getDateDetail(userSingle.getCreatedAt());

                // 判断这个描述是否存在，如果source内容为0，则说明第一次添加，此时要用newData比较
                boolean sourceNotEmpty = (source.size() > 0);
                boolean hasIt = hasTheSameDescription(sourceNotEmpty ? source : newData, dateDescription);

                // 如果不存在，则设置这个描述
                if (!hasIt) {
                    userSingle.setCreatedAt(dateDescription);
                }

                // 如果已经有更早，则剩下的全是更早了，赶紧结束
                if (DateFormatUtil.BEFORE_YESTERDAY.equals(dateDescription) && hasIt) {
                    break;
                }
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
