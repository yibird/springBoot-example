package com.fly;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * TTL队列测试类
 */
@SpringBootTest(classes = Application.class)
public class TTLQueueTest {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void send(){
        // 消息属性
        MessageProperties properties = new MessageProperties();
        // 设置消息过期时间
        properties.setExpiration("1800000");
        Message message = new Message("hello!".getBytes(),properties);
        rabbitTemplate.send("","ttl_queue",message);
    }
}
