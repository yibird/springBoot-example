//package com.fly.work;
//
//
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//@Component
//public class Work01 {
//    /**
//     * 监听队列
//     * @param msg 表示接收到的消息体
//     * @param message 消息实体,它包含了接收到的消息的详细信息，如消息体、属性、元数据等
//     * @param channel 表示 AMQP 通道,可以通过它进行手动消息确认、拒绝等操作
//     */
//    @RabbitListener(queues = {"work"})
//    public void receiveMessage(Object msg, Message message, Channel channel) throws IOException {
//        channel.basicQos(10);
//        System.out.println("work01 message body:"+new String(message.getBody(),"UTF-8"));
//    }
//}
