package com.fly.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * 声明队列,RabbitTemplate 发送的队列如果不存在并不会创建队列,因此需要预先定义
     *
     * @return Queue
     */
    @Bean
    public Queue demoQueue() {
        /**
         * Queue提供多个构造方法,其中完整的构造方法为Queue(String name, boolean durable, boolean exclusive, boolean autoDelete):
         * - name:队列名称。
         * - durable:指定队列是否是持久化。如果设置为 true,队列会在 RabbitMQ 服务器重启后仍然存在。
         * 如果设置为 false,队列会在服务器重启后被删除(即临时队列)。默认值为 false。
         * - exclusive:指定队列是否是排他的。如果设置为 true,队列只能被声明它的连接使用,并在连接关闭时自动删除。默认值为 false。
         * - autoDelete:指定队列是否是自动删除的。如果设置为 true,当队列不再被使用时会自动被删除。默认值为 false。
         */
        return new Queue("test", true);
    }

    @Bean
    public Queue workQueue() {
        return new Queue("work");
    }

    /**
     * 声明一个扇型交换机
     *
     * @return
     */
    @Bean
    public Exchange exchange() {
        return new FanoutExchange("fanout");
    }

    /**
     * 定义交换机与队列的绑定关系。交换机fanout中路由键匹配routing.key的消息时,
     * 就会路由到test这个队列中。通过绑定,可以实现一个交换机对多个队列的消息路由。
     *
     * @return
     */
    @Bean
    public Binding binding() {
        // 绑定队列到交换机,使用 routing.key 作为路由key
        return BindingBuilder.bind(demoQueue()).to(exchange()).with("routing.key").noargs();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setChannelTransacted(true);
        /**
         * setConfirmCallback(ConfirmCallback confirmCallback)用于设置消息发布确认回调。当消息被成功确认接收时,
         * 或者发生发布确认失败时会调用该回调。ConfirmCallback内部提供了confirm()方法,其参数如下:
         * - correlationData:关联数据对象,可以用于在消息确认时识别消息。
         * - ack:布尔值,表示消息是否被成功确认。true 表示消息成功确认,false 表示消息确认失败。
         * - cause:如果消息确认失败,这个参数会包含失败的原因。如果确认成功,这个参数为 null。
         */
//        template.setConfirmCallback((correlationData, ack, cause) -> {
//            if (ack) {
//                // 消息成功确认
//                System.out.println("Message confirmed: " + correlationData);
//            } else {
//                // 消息确认失败，可能需要进行重发等处理
//                System.out.println("Message confirmation failed: " + correlationData);
//            }
//        });
        return template;
    }
}
