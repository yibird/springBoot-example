package com.fly.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信队列配置
 */
@Configuration
public class DeadQueueConfig {
    // 普通队列
    public static final String NORMAL_QUEUE = "normal_queue";
    // 普通交换机
    public static final String DEAD_QUEUE = "dead_queue";
    // 死信队列
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    // 死信交换机
    public static final String DEAD_EXCHANGE = "dead_exchange";
    // 普通交换机和普通队列的路由key
    public static final String NORMAL_ROUTING_KEY = "normal-routing-key";
    // 死信交换机和死信队列的路由key
    public static final String DEAD_ROUTING_KEY = "dead-routing-key";

    /**
     * 声明普通队列。当普通队列中出现消息过期、消费失败或队列已满等情况导致消息无法被正常消费
     * 时,消息会被投递到死信队列中,以便后续通过定时任务或者人工干预进行手动消费。
     *
     * @return Queue
     */
    @Bean
    public Queue normalQueue() {
        Map<String, Object> arguments = new HashMap<>();
        /**
         * 设置队列投递的死信交换机。队列中因为消息过期、消费失败或队列已满等情况导致
         * 消息无法被正常消费,此时消息会被投递到 x-dead-letter-exchange参数设置的
         * 死信交换机中,以便后续通过定时任务或者人工干预进行手动消费
         */
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信的路由key
        arguments.put("x-dead-letter-routing-key", DEAD_ROUTING_KEY);
        // 设置队列最大长度
//        arguments.put("x-max-length",5);
        return QueueBuilder.durable(NORMAL_QUEUE)
                // 设置消息参数
                .withArguments(arguments).build();
    }


    /**
     * 声明死信队列
     *
     * @return Queue
     */
    @Bean
    public Queue deadQueue() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    /**
     * 声明普通交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(NORMAL_EXCHANGE, true, false);
    }

    /**
     * 声明死信交换机
     *
     * @return DirectExchange
     */

    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE, true, false);
    }


    /**
     * 声明普通队列和普通交换机的绑定关系。向交换机发送消息时,交换机会根据路由key将消息
     * 转发至有绑定关系的队列中
     *
     * @return Binding
     */
    @Bean
    public Binding normalBinding() {
        return BindingBuilder.bind(normalQueue()).to(normalExchange())
                // 设置路由key
                .with(NORMAL_ROUTING_KEY);
    }

    /**
     * 声明死信队列和死信交换机的绑定关系
     *
     * @return Binding
     */
    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange())
                // 设置路由key
                .with(DEAD_ROUTING_KEY);
    }
}
