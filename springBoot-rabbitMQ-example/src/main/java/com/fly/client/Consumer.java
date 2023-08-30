package com.fly.client;

import com.fly.utils.RabbitMQUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费者
 */
public class Consumer {
    // 消费的队列名称
    private final static String QUEUE_NAME = "myQueue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 通过连接工厂获取一个信道
        Channel channel = RabbitMQUtil.getChannel();
        /**
         * basicConsume()用于消费消息,basicConsume有多个重载方法,basicConsume(String queue, Consumer callback)参数说明如下:
         * - queue:消费的队列名称。
         * - autoAck:表示是否自动确认消息。如果设置为 true，表示一旦消息被消费者接收，它将自动确认（acknowledge），这样 RabbitMQ
         * 将从队列中移除消息。如果设置为 false，则需要在消费者处理消息后手动调用 basicAck 方法进行消息确认
         * - callback:应用回调对象的接口,用于通过订阅从队列接收通知和消息。callback是一个接口,DefaultConsumer是Consumer接口的实现者,
         *
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
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
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
        // 关闭Channel和连接
        channel.close();
        RabbitMQUtil.closeConn();
    }
}
