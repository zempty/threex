package com.zhu2chu.all.bus.kit;

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

}
