package com.fly;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest(classes = RedisApplication.class)
public class PublishSubscriptionTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test(){
        String messageOne = "我命由我不由天,先当孙子后成仙!";
        String messageTwo = "爆竹声中一岁除,你菜我是真的服!";
        /**
         * convertAndSend(String channel, Object message):转换message为byte[]
         * 并指定通道发送转换后的message。channel需要与Redis配置类的通道一致。
         */
        redisTemplate.convertAndSend("redis.life", messageOne);
        redisTemplate.convertAndSend("redis.new",messageTwo);
    }
}
