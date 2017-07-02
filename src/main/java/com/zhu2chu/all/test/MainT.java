package com.zhu2chu.all.test;

import com.jfinal.plugin.activerecord.Record;
import com.zhu2chu.all.bus.kit.FileKit;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MainT {

    public static void main(String[] args) {
        Record s = new Record();
        Object g = (Object) s;

        System.out.println(g);

        try {
            Document doc = Jsoup.connect("https://www.baidu.com").get();
            Element e = doc.body();
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        float a = 20014999;
        System.out.println(a);
    }

}
