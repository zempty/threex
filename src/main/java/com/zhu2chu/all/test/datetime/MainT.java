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
        System.out.println(ins);
        LocalDate ld = new LocalDate();
        System.out.println(ld);
        LocalTime lt = new LocalTime();
        System.out.println(lt);
        DateTime dt = new DateTime();
        System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss.SSS"));
        LocalDateTime ldt = new LocalDateTime();
        System.out.println(ldt.toString("yyyy-MM-dd HH:mm:ss.SSS"));
        String mon = dt.monthOfYear().getAsShortText(Locale.CHINA);
        DateTime rounded = dt.dayOfMonth().roundFloorCopy();
        System.out.println(rounded);
    }

}
