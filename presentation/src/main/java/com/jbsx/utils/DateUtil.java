package com.jbsx.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by lijian on 2018/11/25.
 */

public class DateUtil {
    /**
     * 时间转成毫秒
     *
     * @param date
     * @return
     */
    public static long parseMillSecond(String date) {
        long result = 0;

        if (!TextUtils.isEmpty(date)) {
            String times[] = date.split(":");

            // 保证至少有分：秒
            if (times != null && times.length >= 2) {
                int timeLength = times.length;
                boolean hasHour = (timeLength == 3);
                int i = 0;

                // 小时
                if (hasHour) {
                    result = Integer.parseInt(times[i++]) * 60 * 60 * 1000;
                }

                // 分
                result += Integer.parseInt(times[i++]) * 60 * 1000;

                // 秒
                result += Integer.parseInt(times[i++]) * 1000;
            }
        }

        return result;
    }
}
