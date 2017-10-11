package com.zhu2chu.all.bus.log4j2.appender.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jfinal.core.Controller;
import com.zhu2chu.all.bus.router.URIMapping;
import com.zhu2chu.all.front.FrontRoutes;

/**
 * 日志监控页
 * 
 * @author ThreeX
 *
 */
@URIMapping(uri="/logmonitor",routeClass=FrontRoutes.class,viewPath="/websocket")
public class LogMonitorController extends Controller {

    private static final Logger log = LogManager.getLogger(LogMonitorController.class);

    public void index() {
        render("logmonitor.html");
    }

    /**
     * 模拟日志输出好让页面显示
     */
    public void mockLog() {
        log.info("一秒发一波无敌赛亚人");
        renderNull();
    }

}
