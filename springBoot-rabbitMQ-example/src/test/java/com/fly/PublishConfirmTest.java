package com.fly;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

/**
 * 发布确认测试类
 */
@SpringBootTest(classes = Application.class)
public class PublishConfirmTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void singlePublishConfirm() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            Message message = new Message("hello!".getBytes());
            // 用于关联确认信息
            CorrelationData correlationData = new CorrelationData();
            /**
             * 发布消息。convertAndSend(String exchange, String routingKey, final Object object,
             * CorrelationData correlationData):
             * - exchange:交换机名称。
             * - routingKey:路由key。
             * - object:消息、
             * - correlationData:发布确认数据,用于关联确认信息
             */
            rabbitTemplate.convertAndSend("fanout", "routing.key", message, correlationData);
        }
        stopWatch.stop();
        System.out.println("单条发布确认模式耗时:" + stopWatch.getTotalTimeMillis() + "ms");
    }

    @Test
    public void batchPublishConfirm() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        rabbitTemplate.execute(channel -> {
            try {
                // 开启事务
                channel.txSelect();
                for (int i = 0; i < 1000; i++) {
                    // 每一批次发送10条消息
                    if (i % 100 == 0) {
                        rabbitTemplate.convertAndSend("fanout", "routing.key", new Message(("message-" + i).getBytes()), new CorrelationData());
                    }
                }
                // 提交事务
                channel.txCommit();
            } catch (ShutdownSignalException e) {
                System.out.println("Asdasdasd:" + channel);
                // 回滚事务
                channel.txRollback();
            }
            return channel;
        });
        stopWatch.stop();
        System.out.println("批量发布确认模式耗时:" + stopWatch.getTotalTimeMillis() + "ms");
    }

    @Test
    public void asyncPublishConfirm() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000; i++) {
            // 异步发送消息
            rabbitTemplate.convertAndSend("fanout", "routing.key", new Message("hello!".getBytes()));
        }
        stopWatch.stop();
        System.out.println("异步发布确认模式耗时:" + stopWatch.getTotalTimeMillis() + "ms");
    }
}
