package com.zhu2chu.all.bus.kit;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import com.jfinal.kit.PropKit;

/**
 * 2017年5月6日 10:50:54
 * System相关工具类
 * 
 * @author ThreeX
 *
 */
public class SystemKit {

    /**
     * 输出System中的所有键值对
     */
    public static void outputSysProperties() {
        Properties p = System.getProperties();

        for (Enumeration<?> e = p.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            System.out.println(key + ":" + p.getProperty(key));
        }
    }

    /**
     * jdk自带方法输出
     * 
     * @param bool
     */
    public static void outputSysProperties(boolean bool) {
        System.getProperties().list(System.out);
    }

    /**
     * 获取user.home的路径
     * 
     * @return
     */
    public static String getSysUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * 设置系统代理
     * @param kvs
     */
    public static void setSystemProxy(Properties kvs) {
        System.setProperties(kvs);
    }

    /**
     * 2017年9月21日 14:50:53
     * 调用默认浏览器打开相应的url
     * 下面这种方式。在eclipse打断点后，单步过能弹出浏览器，直接运行过去就不行，不靠谱的
     * process = Runtime.getRuntime().exec("cmd /c start http://127.0.0.1:17878");
     * 
     * @param url
     */
    public static void openBrowse(String url) {
        try {
            // 创建一个URI实例
            java.net.URI uri = java.net.URI.create(url);
            // 获取当前系统桌面扩展
            java.awt.Desktop dp = java.awt.Desktop.getDesktop();
            // 判断系统桌面是否支持要执行的功能
            if (dp.isSupported(java.awt.Desktop.Action.BROWSE)) {
                // 获取系统默认浏览器打开链接 
                dp.browse(uri) ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        outputSysProperties(true);
    }

}
