package com.jbsx.utils;

import java.text.ParseException;

/**
 * 日期工具
 */
public class DateFormatUtil {
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
