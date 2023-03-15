package com.fly.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Component;

/**
 * SocketIo WebSocket处理器
 */
@Component
public class SocketIoWebSocketHandle {

    /**
     * ws连接成功执行此事件
     *
     * @param client SocketIO客户端对象
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("ws connect...");
    }

    /**
     * ws连接关闭时执行此事件
     *
     * @param client SocketIO客户端对象
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("ws close...");
    }

    /**
     * 当客户端发送消息到指定监听事件(例如onMessage)时执行此方法。SocketIO发送消息时
     * 需要指定事件名,服务端接收消息时需要指定接收消息的事件名。
     *
     * @param client  SocketIO客户端对象
     * @param request SocketIO客户端消息确认请求对象,通过isAckRequested()可以判断请求是否被确认
     * @param data    消息体
     */
    @OnEvent(value = "onMessage")
    public void onMessage(SocketIOClient client, AckRequest request, Object data) {
        System.out.println("接收消息:"+data);
        // 检查是否发出了确认请求
        request.isAckRequested();
        // 向指定事件发送消息
        client.sendEvent("chatMsg", "服务端发送消息...");
    }
}
