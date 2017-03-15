package com.zhu2chu.test;

import java.math.BigDecimal;

public class MainT {

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("23.689687265498354642165");
        a = a.setScale(2, BigDecimal.ROUND_DOWN);
        System.out.println(a);
    }

}
