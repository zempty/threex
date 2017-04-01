package com.zhu2chu.all.test;

import com.jfinal.plugin.activerecord.Record;

public class MainT {

    public static void main(String[] args) {
        Record s = new Record();
        Object g = (Object)s;

        System.out.println(g);
    }

}
