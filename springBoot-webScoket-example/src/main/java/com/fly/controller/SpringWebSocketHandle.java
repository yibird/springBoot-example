package com.fly.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;

/**
 * SpringBoot集成WebSocket,自定义WebSocket处理器。自定义WebSocket处理器需要实现Spring提供的
 * WebSocketHandler接口,WebSocketHandler接口支持处理文本、二进制、ping、pong四种消息。
 * 为了简化处理消息格式Spring提供了TextWebSocketHandler和BinaryWebSocketHandler分别用于
 * 处理文本消息和二进制消息。WebSocketHandler类图架构如下:
 * - WebSocketHandler:WebSocket处理器接口,内部提供了ws生命周期等方法,自定义WebSocket处理器需要
 * 实现该接口。
 * - AbstractWebSocketHandler:WebSocket处理器接口抽象类,该抽象类实现了WebSocketHandler接口,
 * 用于简化消息的处理。自定义WebSocket处理器可以重写handleTextMessage()方法来处理文本消息,
 * 重写handleBinaryMessage()方法处理二进制消息,重写handlePongMessage()方法来处理pong消息。
 * - TextWebSocketHandler:文本消息WebSocket处理器。该类实现了AbstractWebSocketHandler抽象类,
 * 仅用于处理WebSocket文本消息。
 * - BinaryWebSocketHandler:二进制消息WebSocket处理器。该类实现了AbstractWebSocketHandler抽象类,
 * 仅用于处理WebSocket二进制消息。
 *
 * 自定义WebSocket处理器需要实现WebSocketHandler接口,并重写如下方法:
 * - afterConnectionEstablished():ws连接成功后调用。
 * - handleMessage():处理客户端发送的消息。
 * - handleTransportError():ws连接错误时调用。
 * - afterConnectionClosed():ws连接关闭时调用。
 * - supportsPartialMessages():是否支持分片消息(默认false)。
 *
 * 使用handleMessage()处理来自客户端发送的消息时,spring将消息包装成一个WebSocketMessage对象,
 * WebSocketMessage是一个接口,它四个实现类:
 * - TextMessage:WebSocket文本消息体。
 * - BinaryMessage:WebSocket二进制消息体。
 * - PingMessage:WebSocket ping消息体。
 * - PongMessage:WebSocket pong消息体。
 * 因此接收消息时需判断WebSocketMessage属于某个具体的消息子类。
 */
@Component
public class SpringWebSocketHandle implements WebSocketHandler {

    private WebSocketSession session;

    /**
     * 在WebSocket协商成功且WebSocket连接已打开并准备好使用后调用。
     *
     * @param session WebSocketSession
     * @throws Exception 连接异常时抛出的异常对象
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;
        System.out.println("ws open");
    }

    /**
     * 当客户端发送WebSocket消息到达时调用。
     *
     * @param session WebSocket会话对象
     * @param message WebSocket消息对象
     * @throws Exception 接收客户端发送的消息时抛出的异常
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        /**
         * WebSocketMessage方法如下:
         * - getPayload():返回消息负载(从不为空)。
         * - getPayloadLength():返回消息负载的长度。
         * - isLast():判断当前消息是否是分片消息。
         */
        System.out.println("ws receive message,message:" + message.getPayload());
        // 向客户端发送文本消息
        session.sendMessage(new TextMessage("这是一条消息"));
    }

    /**
     * 处理来自基础WebSocket消息传输的错误
     *
     * @param session   WebSocket会话对象
     * @param exception 异常对象
     * @throws Exception 处理基础消息时抛出的异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("ws error,cause:" + exception.getCause());
    }

    /**
     * 在任何一方关闭WebSocket连接后或发生传输错误后调用。
     *
     * @param session     WebSocket会话对象
     * @param closeStatus 关闭状态码对象
     * @throws Exception 关闭连接时产生的异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("ws close,closeCode:" + closeStatus.getCode());
    }

    /**
     * WebSocketHandler是否处理部分消息。如果此标志设置为true,并且基础WebSocket服务器支持部分消息,
     * 则可能会拆分一个大型WebSocket消息或一个未知大小的消息,并可能通过多次调用来处理消息
     * (WebSocketSession、WebSocketMessage)
     *
     * @return 是否处理部分消息
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void send(String message) {
        try {
            session.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
