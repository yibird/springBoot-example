package com.fly.handler;

import com.fly.config.DelayedQueueConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 延迟消息处理器
 */
@Component
public class DelayedMessageHandler {
    @RabbitListener(queues = {DelayedQueueConfig.DELAYED_QUEUE_NAME})
    public void handler(Message message, Channel channel) throws IOException {
        System.out.println("收到延迟消息,消息体:" + new String(message.getBody(), "UTF-8"));
        // 手动应答
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
