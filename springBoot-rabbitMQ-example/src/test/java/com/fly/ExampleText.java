package com.fly;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description Test
 * @Author zchengfeng
 * @Date 2023/3/22 18:19
 */
@SpringBootTest(classes = Application.class)
public class ExampleText {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Test
    public void test(){
        Message queue = rabbitTemplate.sendAndReceive("myQueue", new Message("哈哈哈".getBytes()));
    }
}
