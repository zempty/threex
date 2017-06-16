package com.zhu2chu.all.bus.h2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.handler.Handler;
import com.jfinal.kit.HandlerKit;
import com.jfinal.log.Log;

/**
 * 2017年5月1日 18:02:51 此方法主要用于判断是否有权限访问h2控制台。真正响应页面的还是WebServlet。
 * 
 * @author ThreeX
 * @link http://www.zhu2chu.com
 *
 */
public class H2ServerHandler extends Handler {

    private static final Log log = Log.getLog(H2ServerHandler.class);

    private boolean isPermitted = true;

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        String uri = request.getRequestURI();

        // 如果uri以/console开头，统一为进入数据库后台的控制台。
        if (uri.startsWith("/console")) {
            if (isPermitted) {
                isHandled[0] = false; // jfinal未处理，留给容器处理。
                return;
            } else {
                HandlerKit.renderError404(request, response, isHandled);
                return;
            }
        }

        next.handle(target, request, response, isHandled);
    }

}
