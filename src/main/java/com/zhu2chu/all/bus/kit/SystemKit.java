package com.zhu2chu.all.bus.kit;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

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

    public static void main(String[] args) {
        outputSysProperties(true);
    }

}
