package com.fly.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SocketIoWebSocket配置类
 */
@Configuration
public class SocketIoWebSocketConfig {

    /**
     * 向Spring容器注入SocketIOServer实例
     *
     * @return SocketIOServer实例
     */
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        // 指定ws host
        config.setHostname("localhost");
        // 指定ws端口
        config.setPort(8080);
        // 绑定ws URL
        config.setContext("/socketio-ws");
        // 创建SocketIOServer对象
        SocketIOServer server = new SocketIOServer(config);
        server.start();
        return server;
    }

    /**
     * 注入Spring注解扫描器
     *
     * @return Spring注解扫描器实例
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIOServer());
    }
}
