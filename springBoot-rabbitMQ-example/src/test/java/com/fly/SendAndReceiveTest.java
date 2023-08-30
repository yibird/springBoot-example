package com.fly;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

/**
 * @Description 发送消息并消费消息
 * @Author zchengfeng
 * @Date 2023/3/22 18:19
 */
@SpringBootTest(classes = Application.class)
public class SendAndReceiveTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     */
    @Test
    public void sendMessageTest() {

        rabbitTemplate.execute(new ChannelCallback<Channel>() {
            @Override
            public Channel doInRabbit(Channel channel) throws Exception {
                AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                        .deliveryMode(2) // 持久化消息
                        .build();
                channel.basicPublish("", "myQueue", properties, "hello!".getBytes());
                return channel;
            }
        });

        // 发送消息,send()提供路由key和消息实体两个参数
        rabbitTemplate.send("test", new Message("hello!".getBytes()));
    }

    /**
     * 接收消息
     */
    @Test
    public void receiveMessageTest() throws UnsupportedEncodingException {
        // 接收消息,参数为队列名称
        Message message = rabbitTemplate.receive("test");
        String messageBody = new String(message.getBody(), "UTF-8");
        System.out.println("message body:" + messageBody);
        System.out.println("message properties:" + message.getMessageProperties());
    }
}
