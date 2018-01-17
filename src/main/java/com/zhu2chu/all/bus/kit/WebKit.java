package com.zhu2chu.all.bus.kit;

import javax.servlet.http.HttpServletRequest;

/**
 * 2018年1月17日 09:25:44<br>
 * web相关工具类
 * 
 * @author ThreeX
 *
 */
public class WebKit {

    /**
     * 判断当前请求是否为ajax请求
     * 
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
        String ajax = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(ajax)) {
            return true;
        } else {
            return false;
        }
    }

}
