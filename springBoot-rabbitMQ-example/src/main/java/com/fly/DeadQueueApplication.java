package com.fly;

import com.fly.config.DeadQueueConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DeadQueueApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeadQueueApplication.class);
    }

    /**
     * 模拟死信队列 消息过期的场景,向普通队列发送10条消息,由于没有消费者消费,
     * 因此10s后消息会被投递到死信队列中。
     *
     * @param rabbitTemplate
     * @return
     */
    @Bean
    public ApplicationRunner runner(RabbitTemplate rabbitTemplate) {

        return args -> {
            MessageProperties properties = new MessageProperties();
            properties.setExpiration("3000");
            // 设置消息过期时间
            for (int i = 0; i < 10; i++) {
                rabbitTemplate.send(DeadQueueConfig.NORMAL_EXCHANGE,
                        DeadQueueConfig.NORMAL_ROUTING_KEY,
                        new Message(("hello" + i + "!").getBytes(), properties)
                );
            }


            // 创建一个核心线程为8的定时线程池
            ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(8);
            // 调度固定速率的任务,延迟执行时间为3000ms,每隔1000ms毫秒调度一次
            executor.scheduleAtFixedRate(() -> {
                Message message = rabbitTemplate.receive(DeadQueueConfig.DEAD_QUEUE);
                try {
                    System.out.println("message body:" + new String(message.getBody(),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }, 3000, 1000, TimeUnit.MILLISECONDS);

        };
    }

//    /**
//     * 消息消费者
//     *
//     * @param message 消息
//     * @param channel 信道
//     */
//    @RabbitListener(queues = {DeadQueueConfig.NORMAL_QUEUE})
//    public void handler(Message message, Channel channel) throws IOException {
//        // 获取消息id
//        int messageId = Integer.parseInt(message.getMessageProperties().getMessageId());
//        // 获取消息投递标签
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//
//        // 对消息id为偶数的消息处理失败
//        if (messageId % 2 == 0) {
//            // 消息处理失败不重试,只处理当前投递的消息
//            channel.basicNack(deliveryTag, false, false);
//        } else {
//            // 消息处理成功
//            channel.basicAck(deliveryTag, false);
//        }
//    }
}
