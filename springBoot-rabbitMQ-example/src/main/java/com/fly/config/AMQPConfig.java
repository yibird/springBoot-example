//package com.fly.config;
//
//import com.rabbitmq.client.ConnectionFactory;
//import org.springframework.amqp.rabbit.connection.PooledChannelConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ThreadChannelConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.retry.backoff.ExponentialBackOffPolicy;
//import org.springframework.retry.support.RetryTemplate;
//
///**
// * @Description AMQPConfig
// * @Author zchengfeng
// * @Date 2023/3/22 17:39
// */
//@Configuration
//public class AMQPConfig {
//
//    @Bean
//    public ThreadChannelConnectionFactory connectionFactory(){
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setUsername("test");
//        factory.setPassword("test");
//        factory.setVirtualHost("VHOST");
//        factory.setHost("192.168.159.128");
//        factory.setPort(5672);
//        ThreadChannelConnectionFactory pcf = new ThreadChannelConnectionFactory(factory);
//        return pcf;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(){
//        RabbitTemplate template = new RabbitTemplate(connectionFactory());
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
//        return template;
//    }
//}
