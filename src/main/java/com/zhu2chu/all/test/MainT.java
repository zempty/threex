package com.zhu2chu.all.test;

<<<<<<< HEAD
import com.jfinal.plugin.activerecord.Record;
=======
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
>>>>>>> remotes/origin/master

public class MainT {

    public static void main(String[] args) {
<<<<<<< HEAD
        Record s = new Record();
        Object g = (Object)s;

        System.out.println(g);
=======
        try {
            Document doc = Jsoup.connect("https://www.baidu.com").get();
            Element e = doc.body();
            System.out.println(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
>>>>>>> remotes/origin/master
    }

}
