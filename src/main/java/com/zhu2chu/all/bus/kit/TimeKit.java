package com.zhu2chu.all.bus.kit;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class TimeKit {

    /**
     * 计算两个毫秒数之间的相差。以xx时xx分xx秒的形式显示
     * 
     * @param startMillis
     * @param endMillis
     * @return
     */
    public static String calcTime(long startMillis, long endMillis) {
        if (startMillis > endMillis) {
            System.out.println("开始时间比结束时间大");
            return "开始时间比结束时间大";
        }
        if (startMillis == endMillis) {
            return "0秒";
        }

        long lt = endMillis - startMillis;

        long day = lt / (24 * 60 * 60 * 1000);
        long hour = (lt / (60 * 60 * 1000) - day * 24);
        long min = ((lt / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (lt / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

        StringBuilder msg = new StringBuilder("消耗时间：");

        if (hour > 0L) {
            msg.append(hour + "时");
        }
        if (min > 0L) {
            msg.append(min + "分");
        }
        if (s > 0L) {
            msg.append(s + "秒");
        }
        if (hour > 0L || min > 0L || s > 0L) {
            msg.append("。");
        }

        msg.append("共" + lt + "毫秒");

        return msg.toString();
    }

    /**
     * 2017年5月9日 22:51:06
     * 通过时区获取当前时间
     * 
     * @param dateTimeZone
     * @return
     */
    public static DateTime now(String dateTimeZone) {
        if (dateTimeZone==null || dateTimeZone=="") {
            dateTimeZone = "Asia/Shanghai";
        }

        return new DateTime(DateTimeZone.forID(dateTimeZone));
    }

    /**
     * 2017年5月9日 23:03:26
     * 字符串转时间对象
     * 
     * @param format
     * @param time
     * @return
     */
    public static DateTime strToDate(String formatStr, String time) {
        DateTimeFormatter format = DateTimeFormat.forPattern(formatStr);

        DateTime dateTime = DateTime.parse(time, format);
        return dateTime;
    }

    /**
     * 毫秒转字符串显示
     * 
     * @param timeMillis
     * @return
     */
    public static String millisToDateTime(String formatStr, long timeMillis) {
        return new DateTime(timeMillis).toString(formatStr);
    }

    /**
     * 毫秒转字符串显示
     * 
     * @param timeMillis
     * @return
     */
    public static String millisToDateTime(long timeMillis) {
        return millisToDateTime("yyyy-MM-dd HH:mm:ss", timeMillis);
    }

    /**
     * 是否有效的指定的日期格式
     * 
     * @param dateStr
     * @param format
     * @return
     */
    public static boolean isValidDateFormat(String dateStr, String format) {
        boolean convertSuccess = true;
        if (format==null || "".equals(format)) {
            return false;
        }
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            sdf.setLenient(false);
            sdf.parse(dateStr);
        } catch (ParseException e) {
            convertSuccess = false;
        }

        return convertSuccess;
    }

}
