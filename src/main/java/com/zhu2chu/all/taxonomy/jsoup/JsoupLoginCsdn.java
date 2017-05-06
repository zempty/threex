package com.zhu2chu.all.taxonomy.jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 2017年5月6日 10:56:32
 * jsoup模拟登录CSDN。
 * 参至：http://www.tuicool.com/articles/bam6jeU
 * 感觉写得很棒
 * 
 * @author ThreeX
 *
 */
public class JsoupLoginCsdn {

    public static void main(String[] args) {
        JsoupLoginCsdn jlc = new JsoupLoginCsdn();

        //由于fiddler监听不了java发起的请求。所以得先设置代理

        try {
            jlc.login("user", "pass");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(String username, String password) throws IOException {
        //拿到一个连接对象，用于干事情。不设置proxy的话，fiddler抓不到java发起的请求。
        Connection conc = Jsoup.connect("https://passport.csdn.net/account/login?ref=toolbar").proxy("127.0.0.1", 8888);
        conc.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        //在设置proxy之后，得设置这个。不然访问https时，fiddler不信任。造成访问失败
        conc.validateTLSCertificates(false);
        Response res = conc.execute();
        Document d1 = Jsoup.parse(res.body());//解析请求体为对象,这样就可以用java的方式操作了
        Elements es = d1.select("#fm1");//表单ID通过页面源码找到

        //datas请求时发送的数据
        Map<String, String> datas = new HashMap<String, String>();
        for (Element e : es.get(0).getAllElements()) {
            if (e.attr("name").equals("username")) {
                e.attr("value", username);//如果是输入用户名的Input，我们就输入用户名
            }
            if (e.attr("name").equals("password")) {
                e.attr("value", password);//如果是输入密码的Input，我们就输入用户名
            }
            if (e.attr("name").length()>0) {//排除没有名字的表单元素
                datas.put(e.attr("name"), e.val());
            }
        }

        //第二次请求。登录请求
        Connection conc2 = Jsoup.connect("https://passport.csdn.net/account/login?ref=toolbar").proxy("127.0.0.1", 8888);
        conc2.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        conc2.validateTLSCertificates(false);
        Response res2 = conc2.ignoreContentType(true).method(Method.POST).data(datas).cookies(res.cookies()).execute();
        System.out.println("登录后内容："+res2.body());

        //登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
        Map<String, String> map=res2.cookies();
        for(String s:map.keySet()){
            System.out.println(s+"      "+map.get(s));
        }
    }

}
