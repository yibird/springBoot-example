package com.fly;

import com.fly.config.DeadQueueConfig;
import com.fly.config.DelayedQueueConfig;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    /**
//     * 投递延迟消息
//     *
//     * @param rabbitTemplate
//     * @return
//     */
//    @Bean
//    public ApplicationRunner runner(RabbitTemplate rabbitTemplate) {
//        return args -> {
//            // 发送消息
//            rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
//                    DelayedQueueConfig.DELAYED_ROUTING_KEY,
//                    "hello!".getBytes(),
//                    // 消息后置处理器
//                    new MessagePostProcessor() {
//                        @Override
//                        public Message postProcessMessage(Message message) throws AmqpException {
//                            // 设置消息的延迟时间
//                            message.getMessageProperties().setDelay(10000);
//                            return message;
//                        }
//                    }
//            );
//        };
//    }


}