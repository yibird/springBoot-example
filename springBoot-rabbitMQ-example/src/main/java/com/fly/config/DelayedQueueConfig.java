package com.fly.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {
    public static final String DELAYED_EXCHANGE_NAME = "delay.exchange";
    public static final String DELAYED_QUEUE_NAME = "delay.queue";
    public static final String DELAYED_ROUTING_KEY = "delay.routingkey";

    @Bean
    public Queue queue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    /**
     * 自定义交换机
     * @return
     */
    @Bean
    public CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        // 设置消息延迟类型为direct
        args.put("x-delayed-type", "direct");
        /**
         * 声明自定义交换机,构造函数如下:
         * - name:交换机名称。
         * - type:交换机类型、x-delayed-message表示一个延迟消息
         * - durable:是否将交换机设置为持久化。
         * - autoDelete:指定交换机是否是自动删除的。如果设置为 true,当交换机不再被使用时会自动被删除。默认值为 false。
         * - arguments:交换机的其他参数。
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, args);
    }

    /**
     * 定义队列和交换机的绑定关系
     * @param queue 队列
     * @param customExchange 自定义交换机
     * @return
     */
    @Bean
    public Binding bindingNotify(Queue queue, CustomExchange customExchange) {
        return BindingBuilder.bind(queue).to(customExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
