package com.fly.config;


import com.fly.ws.SpringWebSocketHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

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
