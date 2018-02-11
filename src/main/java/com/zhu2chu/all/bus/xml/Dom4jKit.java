package com.zhu2chu.all.bus.xml;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
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

}
