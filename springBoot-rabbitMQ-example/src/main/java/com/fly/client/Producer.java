package com.fly.client;

import com.fly.utils.RabbitMQUtil;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 */
public class Producer {
    // 投递的队列名称
    private final static String QUEUE_NAME = "myQueue";
    public static void main(String[] args) throws IOException, TimeoutException {
        // 通过连接工厂获取一个信道
        Channel channel = RabbitMQUtil.getChannel();
        /**
         * 声明一个队列,如果该队列不存在,则创建它。queueDeclare参数如下:
         * - queue:队列名称。
         * - durable:是否声明一个持久化队列,为true时该队列将在服务器重新启动后存活。
         * - exclusive:是否声明一个排他队列,排他队列表示仅限于此连接。
         * - autoDelete:是否声明一个自动删除队列,则为true表示服务器将不再使用时该队列时将自动删除。
         * - arguments:队列的其他参数(构造参数)。
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "这是一条消息";
        /**
         * basicPublish()用于消息,发布到不存在的exchange(交换机)将导致通道级协议异常,从而关闭通道。basicPublish()参数如下:
         * - exchange:将消息发布到的交换机。
         * - routingKey:路由键。routingKey用于表示向哪些队列发送消息。
         * - props:消息路由的属性。
         * - body:消息体。
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        // 关闭Channel和连接
        channel.close();
        RabbitMQUtil.closeConn();
    }
}
