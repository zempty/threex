package com.zhu2chu.all.taxonomy.joda;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MainT {

    public static void main(String[] args) {
        Instant ins = new Instant();
        System.out.println("Instant:" + ins);

        LocalDate ld = new LocalDate();
        System.out.println("LocalDate:" + ld);

        LocalTime lt = new LocalTime();
        System.out.println("LocalTime:" + lt);

        DateTime dt = new DateTime();
        System.out.println("DateTime:" + dt.toString("yyyy-MM-dd HH:mm:ss.SSS"));

        LocalDateTime ldt = new LocalDateTime();
        System.out.println("LocalDateTime" + ldt.toString("yyyy-MM-dd HH:mm:ss.SSS"));

        String mon = dt.monthOfYear().getAsShortText(Locale.CHINA);
        DateTime rounded = dt.dayOfMonth().roundFloorCopy();
        System.out.println(rounded);

        // 获取当前年份
        String year = dt.year().getAsString();
        // 获取当前月份
        String month = dt.monthOfYear().getAsString();
        // 获取当前日
        String day = dt.dayOfMonth().getAsString();
        System.out.println(year + month + day);

        // 初始化时间
        DateTime dateTime1 = new DateTime(2017, 04, 01, 03, 34, 23);
        // 下面就是按照一点的格式输出时间
        String str2 = dateTime1.toString("MM/dd/yyyy hh:mm:ss.SSSa");
        String str3 = dateTime1.toString("dd-MM-yyyy HH:mm:ss");
        String str4 = dateTime1.toString("EEEE dd MMMM, yyyy HH:mm:ssa");
        String str5 = dateTime1.toString("MM/dd/yyyy HH:mm ZZZZ");
        String str6 = dateTime1.toString("MM/dd/yyyy HH:mm Z");

        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
        // 字符串按指定格式转化成时间
        DateTime dateTime2 = DateTime.parse("2017-04-02 04:03:23", format);
        // 格式化输出
        String string_u = dateTime2.toString("yyyy/MM/dd HH:mm:ss EE");
        System.out.println(string_u);

        // 格式化带Locale
        String string_c = dateTime2.toString("yyyy年MM月dd日 HH:mm:ss EE", Locale.CHINESE);
        System.out.println(string_c);

        DateTime dt1 = new DateTime();// 默认代表的是当前时间

        // 根据指定格式，将时间字符串转换成DateTime对象，这里的格式和上面的输出格式是一样的
        DateTime dt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime("2017-02-28 03:03:58");

        // 计算两个日期间隔的天数
        LocalDate start = new LocalDate(2017, 04, 04);
        LocalDate end = new LocalDate(2017, 05, 01);
        int days = Days.daysBetween(start, end).getDays();

        // 计算两个日期间隔的小时数，分钟数，秒数
        // 增加日期
        DateTime dateTime3 = DateTime.parse("2017-03-31");
        dateTime3 = dateTime3.plusDays(30);
        System.out.println("plusDays:" + dateTime3);
        dateTime3 = dateTime3.plusHours(3);
        System.out.println("plusHours:" + dateTime3);
        dateTime3 = dateTime3.plusMinutes(3);
        System.out.println("plusMinutes:" + dateTime3);
        dateTime3 = dateTime3.plusMonths(2);
        System.out.println("plusMonths:" + dateTime3);
        dateTime3 = dateTime3.plusSeconds(4);
        System.out.println("plusSeconds:" + dateTime3);
        dateTime3 = dateTime3.plusWeeks(5);
        System.out.println("plusWeeks:" + dateTime3);
        dateTime3 = dateTime3.plusYears(3);
        System.out.println("plusYears:" + dateTime3);

        // Joda-time各种操作
        dateTime2 = dateTime3.plusDays(1) // 增加天
                .plusYears(1) // 增加年
                .plusMonths(1) // 增加月
                .plusWeeks(1) // 增加星期
                .minusMillis(1) // 减分钟
                .minusHours(1) // 减小时
                .minusSeconds(1); // 减秒数

        // 判断是否闰月
        DateTime dt4 = new DateTime();
        org.joda.time.DateTime.Property month4 = dt4.monthOfYear();
        System.out.println("是否闰月：" + month4.isLeap());

        // 取得3秒前的时间
        DateTime dt5 = dt4.secondOfMinute().addToCopy(-3);
        dt4.getSecondOfMinute();// 取得整分钟后，过的秒数
        dt4.getSecondOfDay();// 取得整天后，过的秒数
        dt4.secondOfMinute();// 取得分钟对象，例如做闰年判断等使用

        // DateTime与java.util.Date对象,当前系统TimeMillis转换
        DateTime dt6 = new DateTime(new Date());
        System.out.println("dt6:" + dt6);
        Date date = dateTime1.toDate();
        DateTime dt7 = new DateTime(System.currentTimeMillis());
        System.out.println("dt7:" + dt7);
        dateTime1.getMillis();

        Calendar calendar = Calendar.getInstance();
        dateTime1 = new DateTime(calendar);

        DateTime l1 = new DateTime(DateTimeZone.forID("Asia/Shanghai"));
        System.out.println(l1.toString("yyyy-MM-dd HH:mm:sss"));
    }

}
