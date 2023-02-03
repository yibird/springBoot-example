package com.fly.redis.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis消息监听类。Redis中发布的消息的侦听器。MessageListener
 * 可以实现SubscriptionListener以接收订阅状态的通知。
 * MessageListener是一个函数式接口,仅提供了一个onMessage方法。
 */
@Component
public class RedisMessageListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 通过Redis处理接收对象的回调
     * @param message message 消息实体
     * @param pattern pattern 通道匹配的模式
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 获取消息,消息体是一个二进制数据
        byte[] body = message.getBody();
        // 使用值序列化器转换消息
        Object messageBody = redisTemplate.getValueSerializer().deserialize(body);
        // 获取监听的channel
        byte[] channelByte = message.getChannel();
        // 使用字符串序列化器转换
        Object channel = redisTemplate.getStringSerializer().deserialize(channelByte);
        System.out.println("pattern:"+new String(pattern));
        System.out.println("channel name:"+channel);
        System.out.println("message content:"+messageBody);
    }
}
