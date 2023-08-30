package com.fly;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = Application.class)
public class WorkQueueTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Test
    public void sendMessage(){
        List<String> messages = List.of("消息一", "消息二", "消息三","消息四", "消息五", "消息六");
        for (String message : messages) {
            rabbitTemplate.send("work",new Message(message.getBytes()));
        }
    }
}
