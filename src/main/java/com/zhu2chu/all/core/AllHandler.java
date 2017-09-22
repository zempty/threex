package com.zhu2chu.all.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;
import com.jfinal.kit.PropKit;

public class AllHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        String contextPath = request.getContextPath();
        // 设置ContextPath到request里面,模板引擎要用
        request.setAttribute("CP0", contextPath);
        request.setAttribute("websocketPath", PropKit.get("websocketPath", "ws://localhost:8022"));

        String uri = request.getRequestURI();

        if (uri.contains("blogtemplate")) {
            return;
        }
        if (uri.endsWith(".html")) {
            HandlerKit.renderError404(request, response, isHandled);
            return;
        } else if (uri.contains("console")) {
            isHandled[0] = false;
            return;
        }

        next.handle(target, request, response, isHandled);
    }

}
