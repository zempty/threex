package com.zhu2chu.all.test.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/** 
 *  ━━━━━━神兽出没━━━━━━  
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
@ServerEndpoint("/websocket/server")
public class AllWebSocket {
    private static int onlineCount = 0;

    private static CopyOnWriteArraySet<AllWebSocket> webSocketSet = new CopyOnWriteArraySet<AllWebSocket>();

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为：" + onlineCount);
        System.out.println(this);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        System.out.println("有一连接掉线！当前在线人数为：" + onlineCount);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息：" + message);

        for (AllWebSocket item : webSocketSet) {
            if ("HeartBeat".equals(message)) {//如果是心跳包
                if (this == item) {
                    item.sendMessage("心跳回复");
                }
                continue;
            }
            item.sendMessage(message);
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
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
        AllWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        AllWebSocket.onlineCount--;
    }
}
