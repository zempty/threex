/**
 * 2017年4月2日 22:37:27
 * 内容出至：http://www.xdemo.org/jsoup-html-parse/
 */

package com.zhu2chu.all.taxonomy.jsoup.parse;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

public class DocumentParser {

    /**
     * 从字符串解析出一个文档对象
     */
    public static Document parseHtml(String htmlStr) {
        Document doc = Jsoup.parse(htmlStr);
        return doc;
    }

    /**
     * 这是一个不安全的方法
     * 将String转换成html片段，注意防止跨站脚本攻击
     * 
     * @param bodyStr
     * @return
     */
    public static Element parseHtmlFragment(String bodyStr) {
        Document doc = Jsoup.parseBodyFragment(bodyStr);
        Element body = doc.body();
        return body;
    }

    /**
     * 这是一个安全的方法
     * 将String转换成html片段，注意防止跨站脚本攻击
     * 
     * @param htmlStr
     * @return
     */
    public static Element parseHtmlFragmentSafe(String htmlStr) {
        Whitelist wl = new Whitelist();
        //比较松散的过滤，包括
        //"a", "b", "blockquote", "br", "caption", "cite", "code", "col",
        //"colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
        //"i", "img", "li", "ol", "p", "pre", "q", "small", "strike", "strong",
        //"sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
        //"ul"
        Whitelist.relaxed();
        //没有任何标签，只有文本
        Whitelist.none();
        //常规的过滤器
        //"a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
        //"i", "li", "ol", "p", "pre", "q", "small", "strike", "strong", "sub",
        //"sup", "u", "ul"
        Whitelist.basic();
        //常规的过滤器，多了一个img标签
        Whitelist.basicWithImages();
        //文本类型的标签
        //"b", "em", "i", "strong", "u"
        Whitelist.simpleText();
        //另外还可以自定义过滤规则,例如
        wl.addTags("a");
        //执行过滤
        Jsoup.clean(htmlStr, wl);

        Document doc = Jsoup.parseBodyFragment(htmlStr);
        Element body = doc.body();
        return body;
    }

    /**
     * 从url加载一份文档
     * 
     * @param url
     * @return
     */
    public static Document parseDocumentFromUrl(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
            //获取标题
            String title = doc.title();

            /**
             * data(key,value)是该URL要求的参数
             * userAgent制定用户使用的代理类型
             * cookie带上cookie，如cookie("JSESSIONID","FDE234242342342423432432")
             * 连接超时时间
             * post或者get方法
             */
            doc = Jsoup.connect(url)
                       .data("query", "java")
                       .userAgent("Mozilla")
                       .cookie("auth", "token")
                       .timeout(3000)
                       .post();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     * 从指定的文件加载一份文档
     */
    public static Document parseDocumentFromFile(File file) {
        if (!file.exists()) {
            System.out.println("指定的文件不存在！");
            return null;
        }
        if (file.isDirectory()) {
            System.out.println("指定的文件是一个目录！");
            return null;
        }

        Document doc = null;
        try {
            doc = Jsoup.parse(file, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }

}
