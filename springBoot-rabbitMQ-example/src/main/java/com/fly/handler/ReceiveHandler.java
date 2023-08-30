//package com.fly.handler;
//
//import com.rabbitmq.client.Channel;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class ReceiveHandler {
//    /**
//     * 监听队列
//     *
//     * @param msg     表示接收到的消息体
//     * @param message 消息实体,它包含了接收到的消息的详细信息，如消息体、属性、元数据等
//     * @param channel 表示 AMQP 通道,可以通过它进行手动消息确认、拒绝等操作
//     */
//    @RabbitListener(queues = {"test"}, ackMode = "MANUAL")
//    public void receiveMessage(Object msg, Message message, Channel channel) throws IOException {
//        System.out.println("message body:" + new String(message.getBody(), "UTF-8"));
//        // 获取消息的传送标签
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        try {
//            // 处理消息逻辑...
//
//            // 消息处理成功,手动确认消息
//            channel.basicAck(deliveryTag,false);
//            // 处理失败,拒绝消息并重试
////            channel.basicNack(deliveryTag,false,true);
//        } catch (Exception e) {
//            // 处理异常,拒绝消息并重试
//            channel.basicNack(deliveryTag,false,true);
//        }
//    }
//}
