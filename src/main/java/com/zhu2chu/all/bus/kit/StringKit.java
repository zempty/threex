package com.zhu2chu.all.bus.kit;

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

}
