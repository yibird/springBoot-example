//package com.fly.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fly.redis.message.RedisMessageListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.SimpleAsyncTaskExecutor;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.serializer.*;
//import org.springframework.util.backoff.BackOff;
//import org.springframework.util.backoff.FixedBackOff;
//
///**
// * Redis配置类
// */
//@Configuration
//public class RedisConfiguration01 {
//
//    /**
//     * RedisTemplate是SpringBoot Redis整合包提供的用于操作Redis的模板类,提供了一列类操作
//     * Redis的API,默认采用JdkSerialization进行序列化,set后的数据实际上存储的是
//     * 二进制字节码,可读性非常差,通过自定义RedisTemplate
//     *
//     * @param factory
//     * @return
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        // Jackson2Json序列化
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =
//                new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        /**
//         * 设置key采用String序列化方式,
//         * Redis存取默认使用JdkSerializationRedisSerializer序列化,
//         * 这种序列化会key的前缀添加奇怪的字符,例如\xac\xed\x00\x05t\x00user_id,
//         * 使用StringRedisSerializer序列化可以去掉这种字符
//         */
//        template.setKeySerializer(stringRedisSerializer);
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        // hash的key也采用String的序列化方式
//        template.setHashKeySerializer(stringRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
//
//        /*
//         * 开启Redis事务,默认是关闭的。也可以手动开启事务,
//         * 通过template.multi()开启事务,template.exec()关闭事务
//         */
//        template.setEnableTransactionSupport(false);
//        return template;
//    }
//
////    /**
////     * 设置消息侦听器适配器,MessageListenerAdapter实现了MessageListener接口,
////     * 通过反射将消息处理委托给目标侦听器方法,并具有灵活的消息类型转换。
////     * 允许侦听器方法对消息内容类型进行操作，完全独立于Redis API。
////     *
////     * @param receiver
////     * @return
////     */
////    @Bean
////    public MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
////        /**
////         * 构建MessageListenerAdapter实例,指定委托对象和监听消息调用的
////         * 方法(默认值为"handleMessage")。当发布者发布消息时,MessageListenerAdapter
////         * 通过反射机制(底层通过MethodInvoker调用方法),调用委托对象的指定方法处理消息。
////         */
////        return new MessageListenerAdapter(receiver, "receiverMessage");
////    }
//
////    /**
////     * 向IOC容器注入Redis消息监听容器。RedisMessageListenerContainer用于
////     * 是Redis消息侦听器提供异步行为的容器。用于处理监听、转换和消息调度的底层细节。
////     *
////     * @return RedisMessageListenerContainer
////     */
////    @Bean
////    public RedisMessageListenerContainer container(RedisConnectionFactory factory,
////                                                   MessageListenerAdapter adapter) {
////        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
////        container.setConnectionFactory(factory);
////        // 添加事件监听器,用于处理指定频道的topic
////        container.addMessageListener(adapter, lifeChannelTopic());
////        container.addMessageListener(adapter, lifeChannelTopic());
////        return container;
////    }
//
//    /**
//     * 注入Channel主题,ChannelTopic和PatternTopic都实现了Topic接口,
//     * ChannelTopic表示频道主题,用于映射到Redis频道(单个频道),
//     * PatternTopic表示模式主题,用于匹配多个channel。
//     *
//     * @return Topic接口的实现
//     */
//    @Bean
//    public ChannelTopic lifeChannelTopic() {
//        return new ChannelTopic("redis.life");
//    }
//
//    @Bean
//    public ChannelTopic newChannelTopic() {
//        return new ChannelTopic("redis.new");
//    }
//
//
//    /**
//     * 向IOC容器注入Redis消息监听容器。RedisMessageListenerContainer用于
//     * 是Redis消息侦听器提供异步行为的容器。用于处理监听、转换和消息调度的底层细节。
//     *
//     * @return RedisMessageListenerContainer
//     */
//    @Bean
//    public RedisMessageListenerContainer container(RedisConnectionFactory factory,
//                                                   RedisMessageListener listener) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        // 设置redis连接工厂
//        container.setConnectionFactory(factory);
//        // 添加消息监听器,并指定Channel主题
//        container.addMessageListener(listener, new ChannelTopic("redis.life"));
//        container.addMessageListener(listener, new ChannelTopic("redis.new"));
//
//        // 以下配置可选
//
//        /**
//         * 设置任务执行器,默认使用SimpleAsyncTaskExecutor作为任务执行器。
//         * 与底层Redis(每个订阅一个连接)不同,消息监听器容器只使用一个连接,
//         * 该连接对所有注册的监听器进行"多路复用",消息调度通过任务执行器完成。建议配置
//         * 任务执行器(当使用阻塞的Redis连接器以及订阅执行器时),而不是使用
//         * 默认的SimpleAsyncTaskExecutor来复用线程池。
//         */
//        container.setTaskExecutor(new SimpleAsyncTaskExecutor("tread-"));
//
//        /**
//         * FixedBackOff类实现了BackOff接口,构造函数接收两个参数:
//         * - interval:两次尝试之间的间隔时间(毫秒)。
//         * - maxAttempts:最大尝试次数。
//         */
//        BackOff backOff = new FixedBackOff(FixedBackOff.DEFAULT_INTERVAL,
//                FixedBackOff.UNLIMITED_ATTEMPTS);
//        /**
//         * 指定回退恢复尝试的间隔。如果直到达到最大尝试次数可能出现错误,可以由ErrorHandler处理侦听器错误。
//         * RedisMessageListenerContainer默认重试间隔时间为5000ms,也可以通过
//         * setRecoveryInterval(long recoveryInterval)设置重试间隔时间;
//         * 默认最大重试次数为Long.MAX_VALUE。
//         */
//        container.setRecoveryBackoff(backOff);
//        container.setRecoveryInterval(FixedBackOff.DEFAULT_INTERVAL);
//
//        /**
//         * 设置在处理消息时引发任何未捕获异常时调用的ErrorHandler。默认情况下没有ErrorHandler
//         */
//        container.setErrorHandler((Throwable t) -> {
//            System.out.println("errorHandle error message:" + t.getMessage());
//        });
//
//        /**
//         * 设置主题序列化器,默认使用RedisSerializer.string()
//         */
//        container.setTopicSerializer(RedisSerializer.string());
//        /**
//         * 指定等待订阅注册的最长时间，以毫秒为单位。默认值为2000ms。
//         * 超时适用于等待订阅注册。请注意,订阅可以异步创建,过期的超时不会取消超时。
//         */
//        container.setMaxSubscriptionRegistrationWaitingTime(2000);
//        return container;
//    }
//
//}