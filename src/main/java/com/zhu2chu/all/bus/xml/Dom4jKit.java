package com.zhu2chu.all.bus.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 2018年2月11日 13:53:13<br>
 * dom4j操作类
 * 
 * @author ThreeX
 *
 */
public class Dom4jKit {

    public static Document readXmlFile(String xmlName) {
        return readXmlFile(new File(xmlName));
    }

    public static Document readXmlFile(File xmlFile) {
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(xmlFile);
            return doc;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取指定名字的元素列表
     * 
     * @return
     */
    public static List<Element> getElementsByName(Document doc, String ename) {
        List<Element> els = new ArrayList<Element>();
        if (doc == null || ename == null) {
            return els;
        }

        Element root = doc.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while (it.hasNext()) {
            Element a = it.next();
            String aname = a.getName();
            if (ename.equalsIgnoreCase(aname)) {
                els.add(a);
            }
        }
        return els;
    }

}
