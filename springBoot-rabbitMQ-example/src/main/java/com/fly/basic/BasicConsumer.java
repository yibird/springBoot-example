package com.fly.basic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReceiveAndReplyCallback;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Description Consumer
 * @Author zchengfeng
 * @Date 2023/3/22 17:42
 */
@Component
public class BasicConsumer {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        executor.scheduleAtFixedRate(() -> {
            /**
             * 接收消息
             */
            Message message = rabbitTemplate.receive("myQueue");
            System.out.println("consumer接收来自队列myQueue的消息:"+new String(message.getBody()));
        }, 0, 3000, TimeUnit.MILLISECONDS);
    }
}
