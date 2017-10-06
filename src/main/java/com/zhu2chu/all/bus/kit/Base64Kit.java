package com.zhu2chu.all.bus.kit;

import com.xiaoleilu.hutool.lang.Base64;

/**
 * 一些小技巧2017年10月6日 23:33:37
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class Base64Kit {

    private static String[][] escapes = { { "+", "_P" }, { "-", "_M" }, { "/", "_S" }, { ".", "_D" }, { "=", "_E" } };

    /**
     * 对应base64.js的trickDecode方法。
     * 前端若用trick编码，则后端用此方法解码
     * 
     * @param base64Str
     * @return
     */
    public static String trickDecode(String base64Str) {
        for (String[] pair : escapes) {
            base64Str = base64Str.replace(pair[1], pair[0]);
        }
        return Base64.decodeStr(base64Str);
    }

    /**
     * 对应base64.js的trick方法
     * 后端若此方法编码，则前端用trickDecode解码
     * 
     * @param str
     * @return
     */
    public static String trick(String str) {
        str = Base64.encode(str);
        for (String[] pair : escapes) {
            str = str.replace(pair[0], pair[1]);
        }
        return str;
    }

}
