package com.fly.config;

import com.fly.controller.SpringWebSocketHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * SpringBoot集成WebSocket配置类。首先使用@EnableWebSocket启用WebSocket支持,
 * 实现了WebSocketConfigurer接口并重写registerWebSocketHandlers方法,
 * 通过WebSocketHandlerRegistry实例根据WebSocket处理器和指定URL进行注册。
 */
@Configuration
// 启用WebSocket
@EnableWebSocket
public class SpringWebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private SpringWebSocketHandle springWebSocketHandle;


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        /**
         * addHandler()注册WebSocket处理器并绑定到指定URL,该处理器需要实现
         * WebSocketHandler接口。setAllowedOrigins("*")表示允许任意源访问。
         */
        registry.addHandler(springWebSocketHandle,"/spring-ws")
                .setAllowedOrigins("*");
    }
}
