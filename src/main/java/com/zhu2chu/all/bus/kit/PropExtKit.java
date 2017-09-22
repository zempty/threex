package com.zhu2chu.all.bus.kit;

/**
 * 2017年9月22日 15:20:10
 * 对PropKit的补充
 * 
 * @author ThreeX
 *
 */
public class PropExtKit {

    /**
     * JDK的Properties只对null的情况设置默认值(属性文件中没有配置为null)
     * 若是配置了啥都不填则是空字符串，有时会误事
     * 
     * @param originValue
     * @param defaultValue
     * @return
     */
    public static String get(String originValue, String defaultValue) {
        return originValue==null || "".equals(originValue) ? defaultValue : originValue;
    }

}
