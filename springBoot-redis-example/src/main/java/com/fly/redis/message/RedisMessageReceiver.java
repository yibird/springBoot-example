package com.fly.redis.message;

import org.springframework.stereotype.Component;

/**
 * 自定义消息接收类。MessageListenerAdapter通过MethodInvoker调用消息接收类的指定方法,
 * 构建MessageListenerAdapter时应指定消息接收类和对应的消息处理方法。
 */
@Component
public class RedisMessageReceiver {
    public void receiverMessage(String message,String channel){
        System.out.println("message:"+message);
        System.out.println("channel:"+channel);
    }
}
