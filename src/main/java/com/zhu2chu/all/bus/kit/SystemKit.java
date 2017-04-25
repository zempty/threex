package com.zhu2chu.all.bus.kit;

import java.util.Enumeration;
import java.util.Properties;

public class SystemKit {

    /**
     * 输出System中的所有键值对
     */
    public static void outputSysProperties() {
        Properties p = System.getProperties();

        for (Enumeration<?> e=p.propertyNames(); e.hasMoreElements();) {
            String key = (String) e.nextElement();
            System.out.println(key + ":" + p.getProperty(key));
        }
    }

    /**
     * jdk自带方法输出
     * @param bool
     */
    public static void outputSysProperties(boolean bool) {
        System.getProperties().list(System.out);
    }

    /**
     * 获取user.home的路径
     * @return
     */
    public static String getSysUserHome() {
        return System.getProperty("user.home");
    }

    public static void main(String[] args) {
        outputSysProperties(true);
    }

}
