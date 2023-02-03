package com.fly.ws.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * WebSocket文本消息绑定处理器
 */
public class WebSocketTextInBoundHandle extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public WebSocketTextInBoundHandle() {
        super();
    }

    /**
     * 等同于ws的onOpen
     *
     * @param ctx channel处理器上下文对象
     * @throws Exception channel激活时产生的异常
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("ws connect...");
    }

    /**
     * channel读取消息时执行该回调,等同于ws的onMessage事件
     *
     * @param ctx channel处理器上下文对象
     * @param msg 读取消息
     * @throws Exception channel读取消息产生的异常
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("message:" + msg.text());
        // 向客户端写入并刷新消息
        ctx.channel().writeAndFlush("{'status:'200,'content':'hello!'}");
    }

    /**
     * channel读取完成时执行该方法
     *
     * @param ctx channel处理器上下文对象
     * @throws Exception channel读取消息时出现的异常
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
        System.out.println("ws close...");
    }
}
