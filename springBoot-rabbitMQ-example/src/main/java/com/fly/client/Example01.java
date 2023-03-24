package com.fly.client;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description Example01
 * @Author zchengfeng
 * @Date 2023/3/22 14:57
 */
public class Example01 {
    private final static String USERNAME = "test";
    private final static String PASSWORD = "test";
    private final static String VIRTUAL_HOST = "VHOST";
    private final static String HOST = "192.168.159.128";
    private final static int PORT = 5672;
    private final static String QUEUE_NAME = "myQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);
        factory.setHost(HOST);
        factory.setPort(PORT);
        // 获取连接对象
        Connection conn = factory.newConnection();
        // 创建一个channel(通道)
        Channel channel = conn.createChannel();

        /**
         * 声明一个队列,如果该队列不存在,则创建它。queueDeclare参数如下:
         * queue:队列名称。
         * durable:是否声明一个持久化队列,为true时该队列将在服务器重新启动后存活。
         * exclusive:是否声明一个排他队列,排他队列表示仅限于此连接。
         * autoDelete:是否声明一个自动删除队列,则为true表示服务器将不再使用时该队列时将自动删除。
         * arguments:队列的其他参数(构造参数)。
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "这是一条消息";
        /**
         * basicPublish()用于消息,发布到不存在的exchange(交换机)将导致通道级协议异常,从而关闭通道。basicPublish()参数如下:
         * exchange:将消息发布到的交换机。
         * routingKey:路由键。routingKey用于表示向哪些队列发送消息。
         * props:消息路由的属性。
         * body:消息体。
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));

        /**
         * basicConsume()用于消费消息,basicConsume有多个重载方法,basicConsume(String queue, Consumer callback)参数说明如下:
         * queue:消费的队列名称。
         * callback:应用回调对象的接口,用于通过订阅从队列接收通知和消息。callback是一个接口,DefaultConsumer是Consumer接口的实现者,
         * DefaultConsumer能满足大部分消费场景。Consumer接口提供如下方法:
         *  - handleConsumeOk(String consumerTag):调用任何Channel.basicConsume()注册消费者时调用。
         *  - handleCancelOk(String consumerTag):当调用Channel.basicCancel()取消消费者时调用。
         *  - handleCancel(String consumerTag):当使用者因调用Channel.basicCancel()以外的
         *  原因被取消时调用。例如队列已被删除。
         *  - handleShutdownSignal(String consumerTag, ShutdownSignalException sig):当通道或基础连接已关闭时调用。
         *  - handleRecoverOk(String consumerTag):当收到basic.recover-ok(恢复成功)作为对basic.recover的回复时调用。
         *  在此调用之前收到的所有未确认的消息都将被重新传递,之后收到的所有消息不会。
         *  handleDelivery(String consumerTag,Envelope envelope,AMQP.BasicProperties properties,byte[] body):
         * 当收到消费者的basic.deliver(投递)时调用。
         */
        channel.basicConsume(QUEUE_NAME, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                String exchange = envelope.getExchange();
                long deliveryTag = envelope.getDeliveryTag();
                String routingKey = envelope.getRoutingKey();
                boolean redeliver = envelope.isRedeliver();
                System.out.println("consumerTag(消费标签):" + consumerTag);
                System.out.println("exchange(交换机):" + exchange);
                System.out.println("deliveryTag(投递标签):" + deliveryTag);
                System.out.println("routingKey(路由键):" + routingKey);
                System.out.println("redeliver(消息是否已投递):" + redeliver);
                System.out.println("properties(消息属性):" + properties);
                System.out.println("message(消息):" + message);
            }
        });
        // 关闭通道
        channel.close();
        // 关闭连接
        conn.close();
    }
}
