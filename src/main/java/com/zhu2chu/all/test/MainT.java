package com.zhu2chu.all.test;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class MainT {

    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("https://www.baidu.com").get();
            Element e = doc.body();
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
