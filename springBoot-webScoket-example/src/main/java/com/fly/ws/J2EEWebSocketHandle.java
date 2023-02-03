package com.fly.ws;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * J2EE集成WebSocket。
 * <p>
 * J2EE集成WebSocket出现 Failed to register @ServerEndpoint class 异常原因:
 * - Session包引入错误。应引入javax.websocket.Session。
 * - onOpen方法未使用@PathParam注解。onOpen中除Session、EndpointConfig类型的参数外必须使用@PathParam注解。
 * - onError方法未设置Throwable参数。例如:onError(Throwable e)。
 */
@Component
@ServerEndpoint("/j2ee-ws/{userId}")
public class J2EEWebSocketHandle {

    /**
     * 在线人数,使用volatile(保证线程可见性和可序性)+原子类(底层利用CAS保证线程原子性)保证线程安全
     */
    private static volatile AtomicInteger onlineCount = new AtomicInteger(0);

    /**
     * 当前回话
     */
    private Session session;

    /**
     * 用户唯一标识
     */
    private String userId;

    /**
     * WebSocket Map容器,用于存储每个客户端对应的J2EEWebSocket对象,以用户id为key,
     * 对应的WebSocket为value。ConcurrentHashMap可以保证set时线程是安全的。
     */
    private static ConcurrentHashMap<String, J2EEWebSocketHandle> wsMap = new ConcurrentHashMap<>();

    /**
     * 创建Session list用于保存在线用户信息。Collections.synchronizedList()用于创建一个线程安全的List
     */
    private final static List<Session> SESSIONS = Collections.synchronizedList(new ArrayList<>());

    /**
     * ws建立连接时执行的回调
     *
     * @param session WebSocket回话对象
     * @param userId  用户标识
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;
        SESSIONS.add(session);
        // 如果wsMap存在对应的webSocket实例则移除,否则在线人数+1
        if (wsMap.containsKey(userId)) {
            wsMap.remove(userId);
        } else {
            addOnlineCount();
        }
        // 无论是否存在webSocket实例,wsMap都会添加当前实例
        wsMap.put(userId, this);
        System.out.println("ws connect open...");
        System.out.println("session id:" + session.getId() + ",userId:" + userId + ",onlineCount:" + getOnlineCount());
    }

    /**
     * 接收客户端向服务器发送消息时执行的回调
     *
     * @param session WebSocket回话对象
     * @param message 客户端发送的消息
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        System.out.println("ws receive messages...");
        System.out.println("userId:" + this.userId + ",message:" + message);
    }

    /**
     * ws连接关闭时执行的回调。ws断开连接时wsMap将移除对应的webSocket实例,在线人数并-1
     *
     * @param session WebSocket回话对象
     */
    @OnClose
    public void onClose(Session session) {
        if (StringUtils.hasLength(this.userId) && wsMap.containsKey(userId)) {
            wsMap.remove(userId);
            subOnlineCount();
        }
        System.out.println("ws connect close...");
        System.out.println("onlineCount:" + getOnlineCount());
    }

    /**
     * ws连接异常时执行的回调
     *
     * @param session WebSocket回话对象
     * @param e       异常对象
     */
    @OnError
    public void onError(Session session, Throwable e) {
        System.out.println("ws connect error...");
    }

    /**
     * 向SESSIONS列表中所有状态为打开的Session对象发送消息
     *
     * @param message 发送消息
     */
    public void sendMessage(String message) {
        try {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                    System.out.println("userId" + session.getPathParameters().get("userId") + ",message:" + message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定userId发送消息
     *
     * @param message 发送消息
     * @param userId  用户id唯一标识
     */
    public void sendMessage(String message, String userId) {
        J2EEWebSocketHandle webSocket = wsMap.get(userId);
        if (webSocket != null) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
                System.out.println("userId" + session.getPathParameters().get("userId") + ",message:" + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取在线人数
     *
     * @return 返回在线人数
     */
    public static int getOnlineCount() {
        return onlineCount.get();
    }

    /**
     * 在线人数+1
     */
    public static void addOnlineCount() {
        onlineCount.incrementAndGet();
    }

    /**
     * 在线人数-1
     */
    public static void subOnlineCount() {
        onlineCount.decrementAndGet();
    }
}
