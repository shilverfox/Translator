package com.jbsx.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 */
public class DateFormatUtil {
    public static final String TODAY = "今天";
    public static final String YESTERDAY = "昨天";
    public static final String BEFORE_YESTERDAY = "更早";

    /**
     * 获得今天日期
     *
     * @return
     */
    public static String getNowDateToStr() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowDate = sdf.format(dt);

        return nowDate;
    }

    /**
     * 将日期信息转换成今天、昨天，更早
     *
     * @param time
     * @return
     */
    public static String getDateDetail(String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        Date date = null;
        try {
            date = format.parse( time);
        } catch (ParseException e ) {
            e.printStackTrace();
        }

        Calendar current = Calendar. getInstance();

        // 今天
        Calendar today = Calendar. getInstance();
        today.set(Calendar. YEAR, current.get(Calendar.YEAR));
        today.set(Calendar. MONTH, current.get(Calendar.MONTH));
        today.set(Calendar. DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        // Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar. HOUR_OF_DAY, 0);
        today.set(Calendar. MINUTE, 0);
        today.set(Calendar. SECOND, 0);

        // 昨天
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime( date);

        int timeDiff = -1;
        if (current .after(today )) {
            timeDiff = 0;
        } else if (current .before(today ) && current.after(yesterday )) {
            timeDiff = -1;
        } else {
            timeDiff = -8;
        }

        return showDateDetail(timeDiff);
    }

    /**
     * 将日期差显示为日期
     *
     * @param xcts
     * @return
     */
    private static String showDateDetail(int xcts) {
        switch (xcts) {
            case 0:
                return TODAY;
            case -1:
                return YESTERDAY;
            default:
                return BEFORE_YESTERDAY;
        }
    }

    /**
     * 返回 yyyy-MM-dd
     *
     * @param date 参数为String类型yyyy-MM-dd HH:mm:ss
     * @return 返回 yyyy-MM-ddParseException
     * @throws
     */
    public static String exchangeStringDate(String date) throws ParseException {
        if (date != null && date.length() > 10) {
            String result = date.substring(0, 10);
            return result;
        } else {
            return null;
        }

    }

    /**
     * 返回HH:mm:ss
     *
     * @param date 参数为String类型
     * @return 返回HH:mm:ss
     * @throws ParseException
     */
    public static String exchangeStringTime(String date) throws ParseException {
        if (date != null && date.length() > 10) {
            String result = date.substring(10, date.length());
            return result;
        } else {
            return null;
        }
    }

}
