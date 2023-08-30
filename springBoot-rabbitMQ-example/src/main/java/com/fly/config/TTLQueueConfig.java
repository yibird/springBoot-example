package com.fly.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TTLQueueConfig {
    @Bean
    public Queue ttlQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 设置队列中的消息过期时间为30分钟,如果在指定过期时间未消费,RabbitMQ会自动删除过期消息
        arguments.put("x-expires",10000);
        return QueueBuilder
                // 声明一个持久化队列
                .durable("ttl_queue")
                // 设置声明队列的参数
                .withArguments(arguments).build();
    }
}
