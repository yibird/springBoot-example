package com.fly.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.*;


@Configuration
public class RedisConfiguration {

    /**
     * RedisTemplate是SpringBoot Redis整合包提供的用于操作Redis的模板类,提供了一列类操作
     * Redis的API,默认采用JdkSerialization进行序列化,set后的数据实际上存储的是
     * 二进制字节码,可读性非常差,通过自定义RedisTemplate
     * @param factory
     * @return
     */
     @Bean
     public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
         RedisTemplate<String, Object> template = new RedisTemplate<>();
         template.setConnectionFactory(factory);
         // Jackson2Json序列化   
         Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
                    new Jackson2JsonRedisSerializer(Object.class);
         ObjectMapper objectMapper = new ObjectMapper();
         objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
         jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        StringRedisSerializer stringRedisSerializer= new StringRedisSerializer();

         /**
          * 设置key采用String序列化方式,
          * Redis存取默认使用JdkSerializationRedisSerializer序列化,
          * 这种序列化会key的前缀添加奇怪的字符,例如\xac\xed\x00\x05t\x00user_id,
          * 使用StringRedisSerializer序列化可以去掉这种字符
          */
         template.setKeySerializer(stringRedisSerializer);
         template.setValueSerializer(jackson2JsonRedisSerializer);
         // hash的key也采用String的序列化方式
         template.setHashKeySerializer(stringRedisSerializer);
         // hash的value序列化方式采用jackson
         template.setHashValueSerializer(jackson2JsonRedisSerializer);
         template.afterPropertiesSet();

         /*
          * 开启Redis事务,默认是关闭的。也可以手动开启事务,
          * 通过template.multi()开启事务,template.exec()关闭事务
          */
         // template.setEnableTransactionSupport(true);
         return template;
     }
}