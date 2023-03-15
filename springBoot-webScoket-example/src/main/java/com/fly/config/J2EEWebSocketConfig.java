package com.fly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * J2EE WebSocket配置类,由于使用内嵌容器,需要将内嵌容器交由Spring管理并初始化,
 * 对于外部容器可忽略
 */
@Configuration
public class J2EEWebSocketConfig {
    /**
     * 检测ServerEndpointConfig类型的bean并向标准Java WebSocket注册运行时
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointConfig(){
        return new ServerEndpointExporter();
    }
}
