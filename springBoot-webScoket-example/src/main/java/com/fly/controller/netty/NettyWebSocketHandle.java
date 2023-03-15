package com.fly.controller.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * netty集成WebSocket
 */
public class NettyWebSocketHandle {
    public static void main(String[] args) {
        // 创建主事件循环组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 创建工作事件循环组
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            // 创建服务启动器
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 获取ChannelPipeline管道
                            ch.pipeline()
                                    // 在管道的最后位置添加Http请求解码和响应解码处理器
                                    .addLast(new HttpServerCodec())
                                    // 添加Http压缩处理器
                                    .addLast(new HttpContentCompressor())
                                    // 添加HTTP对象聚合完整处理器
                                    .addLast(new HttpObjectAggregator(65536))
                                    // 添加WebSocket处理器,并绑定websocketPath
                                    .addLast(new WebSocketServerProtocolHandler("/ws"))
                                    .addLast(new WebSocketTextInBoundHandle());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(8888).sync();
            // 对关闭channel进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
