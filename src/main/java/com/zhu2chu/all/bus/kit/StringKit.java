package com.zhu2chu.all.bus.kit;

import java.util.Arrays;

/**
 * 2017年4月19日 19:58:47 字符串工具类 收集各种处理字符串的方法
 * 
 * @author ThreeX
 *
 */
public class StringKit {

    /**
     * jdk的String.valueOf方法。 改："null"变成""
     * 
     * @param obj
     * @return
     */
    public static String valueOf(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }

    /**
     * 将数组转换成逗号分隔的字符串
     * @param aay
     * @return
     */
    public static String aayToStr(Object[] aay) {
        if (aay==null || aay.length==0) {
            return "";
        }
        String aayStr = Arrays.toString(aay);
        return aayStr.substring(1,aayStr.lastIndexOf("]"));
    }

}
