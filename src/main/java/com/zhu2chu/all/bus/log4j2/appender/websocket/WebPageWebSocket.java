package com.zhu2chu.all.bus.log4j2.appender.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 2017年10月11日 09:22:25
 * ━━━━━━神兽出没━━━━━━  
 *　　　┏┓　　　┏┓  
 *　　┏┛┻━━━┛┻┓  
 *　    ┃　　　　　　　┃ 
 *　    ┃　　　━　　　┃  
 *　    ┃　┳┛　┗┳    ┃  
 *　　┃　　　　　　　┃  
 *　　┃　　　┻　　　┃  
 *　　┃　　　　　　　┃  
 *　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting  
 *　　　　┃　　　┃    神兽保佑,代码无bug  
 *　　　　┃　　　┃  
 *　　　　┃　　　┗━━━┓  
 *　　　　┃　　　　　　　┣┓  
 *　　　　┃　　　　　　　┏┛  
 *　　　　┗┓┓┏━┳┓┏┛  
 *　　　　　┃┫┫　┃┫┫  
 *　　　　　┗┻┛　┗┻┛  
 *  
 * ━━━━━━感觉萌萌哒━━━━━━  
 */
@ServerEndpoint("/websocket/log/webpage")
public class WebPageWebSocket {

    private static final Logger log = LoggerFactory.getLogger(WebPageWebSocket.class);

    static CopyOnWriteArraySet<WebPageWebSocket> allSocket = new CopyOnWriteArraySet<WebPageWebSocket>();
    private Session session;
    private static int onlineCount = 0;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        allSocket.add(this);
        addOnlineCount();
        if (log.isInfoEnabled()) {
            log.info("有新连接加入！当前查看日志人数为：" + onlineCount);
        }
    }

    @OnClose
    public void onClose() {
        allSocket.remove(this);
        subOnlineCount();
        if (log.isInfoEnabled()) {
            log.info("有一连接掉线！当前查看日志人数为：" + onlineCount);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        if (log.isInfoEnabled()) {
            log.info("来自客户端的消息：" + message);
        }

        for (WebPageWebSocket item : allSocket) {
            item.sendMessage(message);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        if (log.isInfoEnabled()) {
            log.info("websocket连接发生错误");
        }
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebPageWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebPageWebSocket.onlineCount--;
    }

}
