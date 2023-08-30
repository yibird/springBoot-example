//package com.fly.basic;
//
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @Description Provider
// * @Author zchengfeng
// * @Date 2023/3/22 17:42
// */
//@Component
//public class BasicProvider {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @PostConstruct
//    public void init() {
//        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
//        AtomicInteger i = new AtomicInteger();
//        executor.scheduleAtFixedRate(() -> {
//            String message = "这是一条生产者发送的消息" + i.get();
//            // 发送消息
//            rabbitTemplate.send("myQueue", new Message(message.getBytes()));
//            System.out.println("provider向队列myQueue投递消息:"+message);
//            i.getAndIncrement();
//        }, 0, 1000, TimeUnit.MILLISECONDS);
//    }
//}
