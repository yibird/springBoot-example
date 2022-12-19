package com.fly;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @SpringBootTest标识当前类是一个SpringBoot测试类, classes用于指定SpringBoot启动类
 */
@SpringBootTest(classes = RedisApplication.class)
public class RedisKeyTest {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * redis key 相关操作
     */
    @Test
    public void redisKeyTest() throws InterruptedException {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        ops.set("k_01","v_01");
        // 设置key-value并设置过期时间,过期时间为1分钟
        ops.set("k_02","v_02", Duration.ofMinutes(1));
        // 设置key-value并设置过期时间,过期时间为10000,时间单位毫秒
        ops.set("k_03","v_03",10000, TimeUnit.MILLISECONDS);
        // 仅当key不存在时set key,若key存在则不做任何操作,set key成功返回true,否则返回false
        Boolean bool1 = ops.setIfAbsent("k_04","v_04");
        Boolean bool2 = ops.setIfAbsent("k_01","v_01");
        System.out.println("bool1:"+bool1); // bool1:true
        System.out.println("bool2:"+bool2); // bool2:false

        // 仅当key存在时set key,若key不存在则不做任何操作,set key成功返回true,否则返回false
        Boolean bool3 = ops.setIfPresent("k_01", "v_01_01");
        Boolean bool4 = ops.setIfPresent("k_05", "v_05");
        System.out.println("bool3:"+bool3); // bool3:true
        System.out.println("bool4:"+bool4); // bool4:false

        // 匹配以k_为前缀的key,*表示匹配所有key
        Set<String> keys = redisTemplate.keys("k_*");
        System.out.println("keys length:"+keys.size());

        Thread.sleep(1000);

        // 返回key对应value的类型
        DataType type = redisTemplate.type("k_01");
        System.out.println("type:"+type.code()); // type:string

        // 删除指定key
        Boolean delBool = redisTemplate.delete("k_01");
        System.out.println("delBool:"+delBool); // delBool:true
        // 删除多个指定key
        Long batchDelLong = redisTemplate.delete(
                Stream.of("k_02","k_03")
                        .collect(Collectors.toList()));
        System.out.println("batchDelLong:"+batchDelLong); // batchDelLong:2

        // 判断key是否存在
        System.out.println(redisTemplate.hasKey("k_04")); // true
        System.out.println(redisTemplate.hasKey("k_00004")); // false
        // 判断key是否持久化
        System.out.println(redisTemplate.persist("k_04")); // false
        // 修改key名称
        redisTemplate.rename("k_04","k_004");
        System.out.println("k_004:"+ops.get("k_004")); // k_004:v_04

        // 用于序列化指定key,并返回序列化后的值
        byte[] keyBytes = redisTemplate.dump("k_004");
        /**
         * restore(K key, byte[] value, long timeToLive, TimeUnit unit):将DUMP的结果反序列化回Redis
         * - key:序列化value存储的key。
         * - value:反序列的值。
         * - timeToLive:ttl,key的生存时间。
         * - unit:ttl时间单位。
         */
        redisTemplate.restore("k_0004",keyBytes,60000L,TimeUnit.MILLISECONDS);
        System.out.println("k_0004:"+ops.get("k_0004")); // k_0004:v_04

        // rename(K oldKey, K newKey):修改key的名称,将oldKey修改为newKey
        redisTemplate.rename("k_0004","k_04");
        System.out.println("k_04:"+ops.get("k_04")); // k_04:v_04

        // move(K key, final int dbIndex):将key移动到指定数据库,默认所有key都存储在0号数据库
        Boolean moveBool = redisTemplate.move("k_04", 1);
        System.out.println("moveBool:"+moveBool); // moveBool:true
        Thread.sleep(200);
        System.out.println("k_04:"+ops.get("k_04")); // k_04:null

        // 获取Lettuce连接工厂
        LettuceConnectionFactory connFactory = (LettuceConnectionFactory)redisTemplate.getConnectionFactory();
        // 切换数据库(切换至1号数据库)
        connFactory.setDatabase(1);
        // 设置连接工厂
        redisTemplate.setConnectionFactory(connFactory);
        System.out.println("k_04:"+redisTemplate.opsForValue().get("k_04")); // k_04:v_04

        // 从数据库中随机获取一个key
        System.out.println("randomKey:"+redisTemplate.randomKey()); // randomKey:k_004
    }
}
