package com.zhu2chu.all.test.datetime;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class MainT {

    public static void main(String[] args) {
        Instant ins = new Instant();
        System.out.println("Instant:" + ins);

        LocalDate ld = new LocalDate();
        System.out.println("LocalDate" + ld);

        LocalTime lt = new LocalTime();
        System.out.println("LocalTime" + lt);

        DateTime dt = new DateTime();
        System.out.println("DateTime" + dt.toString("yyyy-MM-dd HH:mm:ss.SSS"));

        LocalDateTime ldt = new LocalDateTime();
        System.out.println("LocalDateTime" + ldt.toString("yyyy-MM-dd HH:mm:ss.SSS"));

        String mon = dt.monthOfYear().getAsShortText(Locale.CHINA);
        DateTime rounded = dt.dayOfMonth().roundFloorCopy();
        System.out.println(rounded);
    }

}
