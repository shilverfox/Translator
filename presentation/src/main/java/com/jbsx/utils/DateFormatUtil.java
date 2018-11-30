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
     * 将日期信息转换成今天、明天、后天、星期
     *
     * @param date
     * @return
     */
    public static String getDateDetail(String date) {
        Calendar today = Calendar.getInstance();
        Calendar target = Calendar.getInstance();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            today.setTime(df.parse(getNowDateToStr()));
            today.set(Calendar.HOUR, 0);
            today.set(Calendar.MINUTE, 0);
            today.set(Calendar.SECOND, 0);

            target.setTime(df.parse(date));
            target.set(Calendar.HOUR, 0);
            target.set(Calendar.MINUTE, 0);
            target.set(Calendar.SECOND, 0);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        long intervalMilli = target.getTimeInMillis() - today.getTimeInMillis();
        int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));

        return showDateDetail(xcts);
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
