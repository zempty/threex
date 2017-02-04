package wang.threex.test.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/websocket")
public class ThreeXWebSocket {
    private static int onlineCount = 0;
    
    private static CopyOnWriteArraySet<ThreeXWebSocket> webSocketSet = new CopyOnWriteArraySet<ThreeXWebSocket>();
    
    private Session session;
    
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println("有新连接加入！当前在线人数为：" + onlineCount);
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
        
        for (ThreeXWebSocket item : webSocketSet) {
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
        ThreeXWebSocket.onlineCount++;
    }
    
    public static synchronized void subOnlineCount() {
        ThreeXWebSocket.onlineCount--;
    }
}
