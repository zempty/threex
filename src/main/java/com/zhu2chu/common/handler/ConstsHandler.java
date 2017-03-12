package com.zhu2chu.common.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;

/**
 * 设置一些常量到request里面
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class ConstsHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        String contextPath = request.getContextPath();
        //设置ContextPath到request里面,模板引擎要用
        request.setAttribute("CP0", contextPath);
    }

}
